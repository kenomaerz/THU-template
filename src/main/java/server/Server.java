
package server;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.rest.client.api.IGenericClient;
import error.DuplicateServerException;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import model.AbstractObservationModel;
import model.CategorialObservationModel;
import model.NumericalObservationModel;

import org.hl7.fhir.r4.model.Bundle;
import org.hl7.fhir.r4.model.Enumerations;
import org.hl7.fhir.r4.model.Observation;
import org.hl7.fhir.r4.model.Patient;

import java.util.ArrayList;
import java.util.List;

import util.ServerConfig;

public class Server {
    
    private static int instances = 0;
    FhirContext ctx = FhirContext.forR4();
    IGenericClient client = ctx.newRestfulGenericClient(ServerConfig.SERVER_BASE_URL);


    public Server() {
        System.out.println("Constructing Server");
        instances++;
        if (instances > 1) {
            throw new DuplicateServerException("More than one instance of server created! Please reuse previous instance!");
        }
    }

    public boolean testConnection() {
        System.out.println("TestConnection");

        HttpResponse<JsonNode> response = Unirest.get(ServerConfig.SERVER_BASE_URL + "metadata").asJson();
        int status = response.getStatus();

        System.out.println(response.getStatusText());
        if (status == 200) {
            return true;
        }
        return false;
    }

    public String createPatient(String firstname, String lastname, Enumerations.AdministrativeGender gender) {
        Patient newPatient = new Patient();
        newPatient
                .addName()
                .setFamily(firstname)
                .addGiven(lastname);
        newPatient.setGender(gender);

        client.create()
                .resource(newPatient)
                .execute();

        ArrayList<String> allPatients = getPatients();
        System.out.println(allPatients);
        String patientID = allPatients.get(allPatients.size() - 1);
        return patientID;
    }

    public String createObservation(AbstractObservationModel observation, String patientID) {
        Observation fhirObservation = new Observation();
        fhirObservation.getCode().addCoding().setSystem(observation.getObservationSystem()).setCode(observation.getObservationCode());
        fhirObservation.getSubject().setReference("Patient/" + patientID);
        
        if (observation instanceof NumericalObservationModel) {
        	NumericalObservationModel numerical = (NumericalObservationModel) observation;
        	fhirObservation.getValueQuantity().setValue(numerical.getNumericalValue()).setUnit(numerical.getUnit());
        } else {
        	CategorialObservationModel categorical = (CategorialObservationModel) observation;
        	fhirObservation.getValueCodeableConcept().addCoding().setSystem(categorical.getValueSystem()).setCode(categorical.getValueCode());
        }
        
        client.create()
              .resource(fhirObservation)
              .execute();
        String observationID = getLastCreatedObservationForPatient(patientID);
        return observationID;
    }

    public ArrayList<String> getPatients() {
        org.hl7.fhir.r4.model.Bundle results = client
                .search()
                .forResource(Patient.class)
                .returnBundle(org.hl7.fhir.r4.model.Bundle.class)
                .execute();

        ArrayList<String> allPatientIDs = new ArrayList<>();
        List<Bundle.BundleEntryComponent> entries = results.getEntry();
        for (int i = 0; i < entries.size(); i++) {
            String patientID = results.getEntry().get(i).getResource().getId().split(ServerConfig.SERVER_BASE_URL +  "Patient/")[1];
            allPatientIDs.add(patientID);
        }
        return allPatientIDs;
    }

    public ArrayList<AbstractObservationModel> getObservationsOfPatient(String patientID) {
        org.hl7.fhir.r4.model.Bundle results = client
                .search()
                .forResource(Observation.class)
                .where(Observation.SUBJECT.hasId(patientID))
                .returnBundle(org.hl7.fhir.r4.model.Bundle.class)
                .execute();

        ArrayList<AbstractObservationModel> allObservations = new ArrayList<>();
        List<Bundle.BundleEntryComponent> entries = results.getEntry();


        for (int i = 0; i < entries.size(); i++) {
            String observationID = entries.get(i).getResource().getId();
            Observation observationResource = (Observation) entries.get(i).getResource();
            String observationSystem = observationResource.getCode().getCoding().get(0).getSystem();
            String observationCode = observationResource.getCode().getCoding().get(0).getCode();
            if (observationResource.hasValueQuantity()) {
                Double observationValue = observationResource.getValueQuantity().getValue().doubleValue();
                String observationUnit = observationResource.getValueQuantity().getUnit();
                NumericalObservationModel observation = new NumericalObservationModel(observationSystem, observationCode, observationValue, observationUnit);
                observation.setObservationID(observationID);
                observation.setPatientID(patientID);
                allObservations.add(observation);
            } else if (observationResource.hasValueCodeableConcept()) {
                String valueSystem = observationResource.getValueCodeableConcept().getCoding().get(0).getSystem();
                String valueCode = observationResource.getValueCodeableConcept().getCoding().get(0).getCode();
                CategorialObservationModel observation = new CategorialObservationModel(observationSystem, observationCode, valueSystem, valueCode);
                observation.setObservationID(observationID);
                observation.setPatientID(patientID);
                allObservations.add(observation);
            }
        }
        return allObservations;
    }

    public String getLastCreatedObservationForPatient(String patientID) {
        ArrayList<AbstractObservationModel> allObservations = getObservationsOfPatient(patientID);
        String observationID = allObservations.get(allObservations.size() - 1).getObservationID().split("http://surgipedia.sfb125.de/wiki/Observation/")[1];
        return observationID;
    }
}

