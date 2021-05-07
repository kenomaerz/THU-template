package server;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.rest.client.api.IGenericClient;
import error.DuplicateServerException;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import model.AbstractObservationModel;
import model.BooleanObservationModel;
import model.CategorialObservationModel;
import model.NumericalObservationModel;
import org.hl7.fhir.r4.model.Bundle;
import org.hl7.fhir.r4.model.Enumerations;
import org.hl7.fhir.r4.model.Observation;
import org.hl7.fhir.r4.model.Patient;
import util.Config;

import java.util.ArrayList;
import java.util.List;

public class Server {

    private static int instances = 0;
    FhirContext ctx = FhirContext.forR4();
    IGenericClient client = ctx.newRestfulGenericClient(Config.SERVER_BASE_URL);


    public Server() {
        System.out.println("Constructing Server");
        instances++;
        if (instances > 1) {
            throw new DuplicateServerException("More than one instance of server created! Please reuse previous instance!");
        }
    }

    public boolean testConnection() {
        System.out.println("TestConnection");

        HttpResponse<JsonNode> response = Unirest.get(Config.SERVER_BASE_URL + "metadata").asJson();
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

    public AbstractObservationModel createObservation(AbstractObservationModel observation, String patientID) {
        Observation fhirObservation = new Observation();
        fhirObservation.getCode().addCoding().setSystem(observation.getObservationSystem()).setCode(observation.getObservationCode());
        fhirObservation.getSubject().setReference("Patient/" + patientID);

        if (observation instanceof NumericalObservationModel) {
            NumericalObservationModel numerical = (NumericalObservationModel) observation;
            fhirObservation.getValueQuantity().setValue(numerical.getValue()).setUnit(numerical.getUnit());
        } else if (observation instanceof CategorialObservationModel) {
            CategorialObservationModel categorical = (CategorialObservationModel) observation;
            fhirObservation.getValueCodeableConcept().addCoding().setSystem(categorical.getValueSystem()).setCode(categorical.getValueCode());
        } else {
            BooleanObservationModel bool = (BooleanObservationModel) observation;
            System.out.println("Val sys" + bool.getValueSystem());
            System.out.println("val vode" + bool.getValueCode());
            fhirObservation.getValueCodeableConcept().addCoding().setSystem(bool.getValueSystem()).setCode(bool.getValueCode());
        }

        client.create()
                .resource(fhirObservation)
                .execute();
        return getLastCreatedObservationForPatient(patientID);
        
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
            String patientID = results.getEntry().get(i).getResource().getId().split(Config.SERVER_BASE_URL + "Patient/")[1];
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
                if (valueCode.equals("true_value_specification") || valueCode.equals("false_value_specification")) {
                    boolean value = valueCode.equals("true_value_specification");
                	BooleanObservationModel observation = new BooleanObservationModel(observationSystem, observationCode, value);
                    observation.setObservationID(observationID);
                    observation.setPatientID(patientID);
                    allObservations.add(observation);
                } else {
                    CategorialObservationModel observation = new CategorialObservationModel(observationSystem, observationCode, valueSystem, valueCode);
                    observation.setObservationID(observationID);
                    observation.setPatientID(patientID);
                    allObservations.add(observation);
                }

            }
        }
        return allObservations;
    }

    public AbstractObservationModel getLastCreatedObservationForPatient(String patientID) {
        ArrayList<AbstractObservationModel> allObservations = getObservationsOfPatient(patientID);
        return allObservations.get(allObservations.size() - 1);
    }
}

