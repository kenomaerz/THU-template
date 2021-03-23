package de.dkfz.cami.sdsk.server.api;

import ca.uhn.fhir.rest.annotation.*;
import ca.uhn.fhir.rest.api.MethodOutcome;
import ca.uhn.fhir.rest.param.TokenParam;
import ca.uhn.fhir.rest.server.IResourceProvider;
import de.dkfz.cami.sdsk.core.model.Language;
import de.dkfz.cami.sdsk.core.model.bfo.*;
import de.dkfz.cami.sdsk.core.model.managedmodel.bfo.BFOQualityClassManaged;
import de.dkfz.cami.sdsk.core.model.units.UnitLabel;
import de.dkfz.cami.sdsk.server.Utils;
import de.dkfz.cami.sdsk.server.exceptions.OperationOutcomeCustom;
import org.eclipse.rdf4j.model.IRI;
import org.hl7.fhir.r4.model.*;

import java.util.*;


/**
 * This resource provider implements operations used by patient resources.
 */
public class PatientResourceProvider implements IResourceProvider {

    OperationOutcomeCustom operationOutcomeClass = new OperationOutcomeCustom();

    public PatientResourceProvider() {
    }

    @Override
    public Class<Patient> getResourceType() {
        return Patient.class;
    }


    /**
     * Fetches all of the patients from the server.
     *
     * @return list of all patients
     */
    @Search
    public List<Patient> getAllPatients() {

        List<BFOObjectInstance> patients = Utils.instances.getPatients();
        List<Patient> fhirPatients = new ArrayList<>();
        for (BFOObjectInstance patient : patients) {
            Patient fhirPatient = new Patient();
            IdType id = new IdType();
            id.setParts(null, null, patient.getIRI().getLocalName().split("A02__Patient__")[1], null);
            fhirPatient.setId(id);
            fhirPatients.add(fhirPatient);
        }
        return fhirPatients;
    }

    /**
     * Fetches a patient-object with the given global identifier.
     *
     * @param identifier the patient's global identifier
     * @return the requested patient-object
     */
    @Search
    public Patient getPatientByIdentifier(@RequiredParam(name = Patient.SP_IDENTIFIER) TokenParam identifier) {

        BFOObjectInstance sdskPatient = Utils.instances.getPatientByID(identifier.getValue().split("A02__Patient__")[1]);
        if (sdskPatient == null) {
            operationOutcomeClass.throwResourceNotFound(OperationOutcome.IssueSeverity.FATAL, OperationOutcome.IssueType.NOTFOUND, "The patient with identifier " + identifier + " does not exist. Please check the entered identifier.");
        }
        Patient fhirPatient = new Patient();
        fhirPatient.setId(identifier.getValue().split("A02__Patient__")[1]);
        String name = sdskPatient.getLabel(Language.getStandardLanguage());
        String[] splittedNames = name.split(" ");
        fhirPatient.addName().setFamily(splittedNames[0]).addGiven(splittedNames[1]);
        fhirPatient.addIdentifier().setSystem(identifier.getSystem()).setValue(identifier.getValue());
        Enumerations.AdministrativeGender gender = setFHIRGender(sdskPatient);
        fhirPatient.setGender(gender);
        return fhirPatient;
    }

    /**
     * Read a patient with the given ID from the server.
     *
     * @param patientID the patient's ID
     * @return the requested patient
     */
    @Read()
    public Patient getPatientById(@IdParam IdType patientID) {

        BFOObjectInstance sdskPatient = Utils.instances.getPatientByID(patientID.getIdPart());
        if (sdskPatient == null) {
            operationOutcomeClass.throwResourceNotFound(OperationOutcome.IssueSeverity.FATAL, OperationOutcome.IssueType.INVALID, "The patient with id " + patientID + " does not exist. Please check the entered ID.");
        }
        Patient fhirPatient = new Patient();
        fhirPatient.addIdentifier().setSystem(sdskPatient.getIRI().getNamespace()).setValue(sdskPatient.getIRI().getLocalName());
        String name = sdskPatient.getLabel(Language.getStandardLanguage());
        String[] splittedNames = name.split(" ");
        fhirPatient.addName().setFamily(splittedNames[1]).addGiven(splittedNames[0]);
        Enumerations.AdministrativeGender gender = setFHIRGender(sdskPatient);
        fhirPatient.setGender(gender);
        return fhirPatient;
    }

    /**
     * Creates a patient with the given FHIR parameters.
     *
     * @param patient the FHIR parameters
     */
    @Create
    public MethodOutcome createPatient(@ResourceParam Patient patient) { //todo birthdate?

        /** if (patient == null) {
         OperationOutcome operationOutcome = operationOutcomeClass.customOperationOutcome(OperationOutcome.IssueSeverity.FATAL, OperationOutcome.IssueType.INVALID, "Please give a patient resource to create the patient.");
         throw new ResourceNotFoundException("", operationOutcome); //todo (probably unnecessary)
         }*/
        BFOObjectInstance sdskPatient = Utils.instances.instantiatePatient(UUID.randomUUID().toString());
        List<HumanName> names = patient.getName();
        if (names == null || names.isEmpty()) {
            operationOutcomeClass.throwInvalidRequest(OperationOutcome.IssueSeverity.FATAL, OperationOutcome.IssueType.INVALID, "The patient's name is missing. Please enter the name of the referring patient.");
        }
        String lastname = names.get(0).getFamily();
        List<StringType> firstname = names.get(0).getGiven();
        if (lastname == null || lastname.isEmpty() || lastname.trim().isEmpty()) {
            operationOutcomeClass.throwInvalidRequest(OperationOutcome.IssueSeverity.FATAL, OperationOutcome.IssueType.INVALID, "The patient's last name is missing. Please enter the last name of the referring patient.");
        }
        if (firstname == null || firstname.isEmpty()) {
            operationOutcomeClass.throwInvalidRequest(OperationOutcome.IssueSeverity.FATAL, OperationOutcome.IssueType.INVALID, "The patient's first name is missing. Please enter the first name of the referring patient.");
        }
        String name = names.get(0).getNameAsSingleString();
        sdskPatient.addLabel(name, Language.getStandardLanguage());
        Enumerations.AdministrativeGender gender = patient.getGender();
        if (gender == null) {
            operationOutcomeClass.throwInvalidRequest(OperationOutcome.IssueSeverity.FATAL, OperationOutcome.IssueType.INCOMPLETE, "The patient's gender is missing. Please enter the gender of the referring patient.");
        } else {
            BFOQualityClass genderClass = new BFOQualityClassManaged(Utils.ontology, Utils.ontology.getFactory().createIRI("http://sfb125.de/ontology/factorontology/gender"));
            IRI value = null;
            switch (gender) {
                case MALE:
                    value = Utils.ontology.getFactory().createIRI("http://sfb125.de/ontology/factorontology/male");
                    break;
                case FEMALE:
                    value = Utils.ontology.getFactory().createIRI("http://sfb125.de/ontology/factorontology/female");
                    break;
                case OTHER:
                    value = Utils.ontology.getFactory().createIRI("http://sfb125.de/ontology/factorontology/intersex");
                    break;
                default:
                    value = null;
            }
            if (value != null) { //todo what if it is default?
                sdskPatient.getOrCreateQualityInstance(genderClass).createCategoricalMeasurementInstance(value, Utils.ontology.getFactory().createIRI("http://purl.obolibrary.org/obo/OBI_0000938"), new Date(), sdskPatient);
            }
        }
        return new MethodOutcome().setId(patient.getIdElement());
    }

   /* @Update  //todo update Method
    public MethodOutcome updatePatient(@IdParam IdType theId, @ResourceParam Patient thePatient) {
        BFOObjectInstance sdskPatient = Utils.instances.instantiatePatient(UUID.randomUUID().toString());
        List<HumanName> names = thePatient.getName();
        if (names == null || names.isEmpty()) {
            operationOutcomeClass.throwInvalidRequest(OperationOutcome.IssueSeverity.FATAL, OperationOutcome.IssueType.INVALID, "Please enter the name of the referring patient.");
        }
        String lastname = names.get(0).getFamily();
        List<StringType> firstname = names.get(0).getGiven();
        // Lastname whole missing + empty
        if (lastname == null || lastname.isEmpty() || lastname.trim().isEmpty()) {
            operationOutcomeClass.throwInvalidRequest(OperationOutcome.IssueSeverity.FATAL, OperationOutcome.IssueType.INVALID, "Please enter the last name of the referring patient.");
        }
        // Firstname whole missing + empty
        if (firstname == null || firstname.isEmpty()) {
            operationOutcomeClass.throwInvalidRequest(OperationOutcome.IssueSeverity.FATAL, OperationOutcome.IssueType.INVALID, "Please enter the first name of the referring patient.");
        }
        String name = names.get(0).getNameAsSingleString();
        sdskPatient.addLabel(name, Language.getStandardLanguage());
        Enumerations.AdministrativeGender gender = thePatient.getGender();
        // Gender whole missing + empty
        if (gender == null) {
            operationOutcomeClass.throwInvalidRequest(OperationOutcome.IssueSeverity.FATAL, OperationOutcome.IssueType.INCOMPLETE, "The gender is missing. Please enter the gender of the referring patient.");
        } else {
            BFOQualityClass genderClass = new BFOQualityClassManaged(Utils.ontology, Utils.ontology.getFactory().createIRI("http://sfb125.de/ontology/factorontology/gender"));
            IRI value = null;
            switch (gender) {
                case MALE:
                    value = Utils.ontology.getFactory().createIRI("http://sfb125.de/ontology/factorontology/male");
                    break;
                case FEMALE:
                    value = Utils.ontology.getFactory().createIRI("http://sfb125.de/ontology/factorontology/female");
                    break;
                case OTHER:
                    value = Utils.ontology.getFactory().createIRI("http://sfb125.de/ontology/factorontology/intersex");
                    break;
                default:
                    value = null;
            }
            if (value != null) {
                sdskPatient.getOrCreateQualityInstance(genderClass).createCategoricalMeasurementInstance(value, Utils.ontology.getFactory().createIRI("http://purl.obolibrary.org/obo/OBI_0000938"), new Date(), sdskPatient);
            }
        }
        return new MethodOutcome().setId(thePatient.getIdElement());

    }*/


    /**
     * Set the gender of the SDSK patient in the FHIR patient object.
     *
     * @param sdskPatient SDSK patient object
     * @return FHIR gender
     */
    public Enumerations.AdministrativeGender setFHIRGender(BFOObjectInstance sdskPatient) {
        BFOQualityClass genderClass = new BFOQualityClassManaged(Utils.ontology, Utils.ontology.getFactory().createIRI("http://sfb125.de/ontology/factorontology/gender"));
        BFOQualityInstance genderInstance = sdskPatient.getQualityInstance(genderClass);
        Object genderValue = genderInstance.getMeasurements().get(0).getMeasurementValue(UnitLabel.CATEGORICAL);

        Enumerations.AdministrativeGender gender;
        switch (genderValue.toString()) {
            case "male:":
            case "Male":
                gender = Enumerations.AdministrativeGender.MALE;
                break;
            case "female":
            case "Female":
                gender = Enumerations.AdministrativeGender.FEMALE;
                break;
            case "Intersex":
            case "intersex":
                gender = Enumerations.AdministrativeGender.OTHER;
                break;
            default:
                gender = Enumerations.AdministrativeGender.NULL;
        }
        return gender;
    }
}