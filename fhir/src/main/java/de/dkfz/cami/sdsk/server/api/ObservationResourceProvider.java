package de.dkfz.cami.sdsk.server.api;

import ca.uhn.fhir.rest.annotation.*;
import ca.uhn.fhir.rest.api.MethodOutcome;
import ca.uhn.fhir.rest.param.ReferenceParam;
import ca.uhn.fhir.rest.server.IResourceProvider;
import de.dkfz.cami.sdsk.core.model.Language;
import de.dkfz.cami.sdsk.core.model.bfo.BFOObjectInstance;
import de.dkfz.cami.sdsk.core.model.bfo.BFOQualityClass;
import de.dkfz.cami.sdsk.core.model.bfo.BFOQualityInstance;
import de.dkfz.cami.sdsk.core.model.iao.IAOMeasurementInstance;
import de.dkfz.cami.sdsk.core.model.managedmodel.bfo.BFOQualityClassManaged;
import de.dkfz.cami.sdsk.core.model.managedmodel.iao.IAOMeasurementInstanceManaged;
import de.dkfz.cami.sdsk.core.model.units.UnitLabel;
import de.dkfz.cami.sdsk.server.Utils;
import de.dkfz.cami.sdsk.server.exceptions.OperationOutcomeCustom;
import org.eclipse.rdf4j.model.IRI;
import org.hl7.fhir.r4.model.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This resource provider implements operations used by observation resources.
 */
public class ObservationResourceProvider implements IResourceProvider {

    OperationOutcomeCustom operationOutcomeClass = new OperationOutcomeCustom();
    IRIValidator iriValidator = new IRIValidator();


    public ObservationResourceProvider() {
    }

    @Override
    public Class<Observation> getResourceType() {
        return Observation.class;
    }

    /**
     * Read a observation with the given ID from the server.
     *
     * @param observationID the patient's ID
     * @return the requested patient
     */
    @Read()
    public Observation getObservationById(@IdParam IdType observationID) {

        IAOMeasurementInstance sdskObservation = new IAOMeasurementInstanceManaged(Utils.instances, Utils.instances.getFactory().createIRI("http://surgipedia.sfb125.de/wiki/Special:URIResolver/" +
                observationID.getIdPart()));

        if (sdskObservation == null) {
            operationOutcomeClass.throwResourceNotFound(OperationOutcome.IssueSeverity.FATAL, OperationOutcome.IssueType.NOTFOUND, "The observation with ID " + observationID + " does not exist. Please check the entered ID.");
        }
        BFOQualityInstance qualityInstance = sdskObservation.getMeasuredQuality();
        BFOQualityClass qualityClass = (BFOQualityClass) qualityInstance.getBFOClass();
        Observation observation = new Observation();
        observation.addIdentifier().setSystem(sdskObservation.getIRI().getNamespace()).setValue(sdskObservation.getIRI().getLocalName());
        String id = sdskObservation.getURI();
        observation.setId(id);

        String idPart = observation.getIdElement().getIdPart();
        String splittedIdPart = idPart.split("A02__Patient__")[1];
        String patientID = splittedIdPart.split("__")[0];

        observation.getSubject().setReference(patientID);
        observation.getCode().addCoding().setCode(qualityClass.getIRI().getLocalName()).setSystem(qualityClass.getIRI().getNamespace()).setDisplay(qualityClass.getLabel(Language.getStandardLanguage()));
        Date measurementDate = sdskObservation.getMeasurementDate();
        observation.setIssued(measurementDate);
        observation = setFHIRValueType(sdskObservation, observation);
        return observation;
    }

    /**
     * Fetches all observations for a patient with the given patient ID.
     *
     * @param patientID the patient's ID
     * @return bundle of the patient's observations
     */
    @Search
    public List<Observation> getObservationsForPatient(
            @OptionalParam(name = Observation.SP_SUBJECT) ReferenceParam patientID
    ) {
        BFOObjectInstance sdskPatient = Utils.instances.getPatientByID(patientID.getIdPart());
        if (sdskPatient == null) {
            operationOutcomeClass.throwResourceNotFound(OperationOutcome.IssueSeverity.FATAL, OperationOutcome.IssueType.NOTFOUND, "The patient with the ID " + sdskPatient + " does not exist.");
        }
        List<BFOQualityInstance> qualityInstances = sdskPatient.getInstantiatedQualities();
        List<Observation> observations = new ArrayList<>();
        for (BFOQualityInstance qualityInstance : qualityInstances) {
            List<IAOMeasurementInstance> measurementInstances = qualityInstance.getMeasurements();
            for (IAOMeasurementInstance measurementInstance : measurementInstances) {
                BFOQualityClass qualityClass = (BFOQualityClass) qualityInstance.getBFOClass();
                Observation observation = createFHIRObservation(measurementInstance, qualityClass, patientID);
                observations.add(observation);
            }
        }
        return observations;
    }


    /**
     * Fetches all observations with the given code for a patient with the given patient ID.
     *
     * @param patientID       the patient's ID
     * @param observationCode the code of the observation
     * @return the requested observation
     */
    @Search
    public List<Observation> getObservationsForPatientAndCode(
            @OptionalParam(name = Observation.SP_SUBJECT) ReferenceParam patientID,
            @OptionalParam(name = Observation.SP_CODE) String observationCode
    ) {
        BFOObjectInstance sdskPatient = Utils.instances.getPatientByID(patientID.getIdPart());
        if (sdskPatient == null) {
            operationOutcomeClass.throwResourceNotFound(OperationOutcome.IssueSeverity.FATAL, OperationOutcome.IssueType.NOTFOUND, "The patient with ID " + sdskPatient + " does not exist.");
        }
        BFOQualityClass qualityClass = new BFOQualityClassManaged(Utils.ontology, Utils.ontology.getFactory().createIRI(observationCode));
        BFOQualityInstance qualityInstance = sdskPatient.getOrCreateQualityInstance(qualityClass); //todo what if it doesn't exist for this patient?
        List<IAOMeasurementInstance> measurementInstances = qualityInstance.getMeasurements();
        List<Observation> observations = new ArrayList<>();
        for (IAOMeasurementInstance measurementInstance : measurementInstances) {
            Observation observation = createFHIRObservation(measurementInstance, qualityClass, patientID);
            observations.add(observation);
        }
        return observations;
    }


    /**
     * Creates an observation with the given FHIR parameters.
     *
     * @param theObservation the FHIR parameters
     */
    @Create
    public MethodOutcome createObservation(@ResourceParam Observation theObservation) {
       /*if (theObservation == null) {
            OperationOutcome operationOutcome = new OperationOutcome();
            operationOutcome.addIssue().setSeverity(OperationOutcome.IssueSeverity.FATAL).setCode(OperationOutcome.IssueType.NOTFOUND).setDiagnostics("Please give the observation you want to create.");
            throw new ResourceNotFoundException("Please give the observation you want to create.", operationOutcome); //todo (probably unnecessary)
        }*/
        String subject = theObservation.getSubject().getReference();
        if (subject == null) {
            operationOutcomeClass.throwInvalidRequest(OperationOutcome.IssueSeverity.FATAL, OperationOutcome.IssueType.INVALID, "The referenced patient is missing. Please enter the patient.");
        }
        String[] patientID = theObservation.getSubject().getReference().split("/");
        String id = patientID[1];
        BFOObjectInstance sdskPatient = Utils.instances.getPatientByID(id);
        if (sdskPatient == null) {
            operationOutcomeClass.throwResourceNotFound(OperationOutcome.IssueSeverity.FATAL, OperationOutcome.IssueType.NOTFOUND, "The patient with ID " + sdskPatient + " does not exist.");
        }
        CodeableConcept code = theObservation.getCode();
        if (code == null) {
            operationOutcomeClass.throwInvalidRequest(OperationOutcome.IssueSeverity.FATAL, OperationOutcome.IssueType.INVALID, "The code of the observation is missing. Please enter the code of the observation.");
        }
        if (code.isEmpty()) {
            operationOutcomeClass.throwInvalidRequest(OperationOutcome.IssueSeverity.FATAL, OperationOutcome.IssueType.INVALID, "The coding of the observation is missing. Please enter the coding of the observation value.");
        }
        String codeIRI = theObservation.getCode().getCoding().get(0).getCode();
        if (codeIRI == null || codeIRI.trim().isEmpty()) {
            operationOutcomeClass.throwInvalidRequest(OperationOutcome.IssueSeverity.FATAL, OperationOutcome.IssueType.INVALID, "The coding of the observation is missing. Please enter the coding of the observation value.");
        }
        if (!iriValidator.codeIsAvailable(codeIRI)) {
            operationOutcomeClass.throwInvalidRequest(OperationOutcome.IssueSeverity.FATAL, OperationOutcome.IssueType.INVALID, "The entered code is invalid. Please check your observation code.");
        }
        String systemIRI = theObservation.getCode().getCoding().get(0).getSystem();
        if (systemIRI == null || systemIRI.trim().isEmpty()) {
            operationOutcomeClass.throwInvalidRequest(OperationOutcome.IssueSeverity.FATAL, OperationOutcome.IssueType.INVALID, "The system of the observation is missing. Please enter the system of the value.");
        }
        if (!iriValidator.systemIsAvailable(systemIRI)) {
            operationOutcomeClass.throwInvalidRequest(OperationOutcome.IssueSeverity.FATAL, OperationOutcome.IssueType.INVALID, "The entered system is invalid. Please check your observation system.");
        }
        IRI observationIRI = Utils.ontology.getFactory().createIRI(systemIRI + codeIRI);
        BFOQualityClass qualityClass = new BFOQualityClassManaged(Utils.ontology, observationIRI);
        if (!iriValidator.iriIsAvailable(qualityClass)) {
            operationOutcomeClass.throwInvalidRequest(OperationOutcome.IssueSeverity.FATAL, OperationOutcome.IssueType.INVALID, "The entered observation IRI is invalid. Please check the combination of your system and code.");
        }
        if (theObservation.getValue() == null) {
            operationOutcomeClass.throwInvalidRequest(OperationOutcome.IssueSeverity.FATAL, OperationOutcome.IssueType.INVALID, "The value type is missing. Please enter the value type of the observation.");
        }
        if (theObservation.hasValueQuantity()) {
            BigDecimal value = theObservation.getValueQuantity().getValue();
            if (value == null) {
                operationOutcomeClass.throwInvalidRequest(OperationOutcome.IssueSeverity.FATAL, OperationOutcome.IssueType.INVALID, "The value is missing. Please enter the value of the observation.");
            }
            Double fhirValue = theObservation.getValueQuantity().getValue().doubleValue();
            if (fhirValue == null) { //todo
                operationOutcomeClass.throwInvalidRequest(OperationOutcome.IssueSeverity.FATAL, OperationOutcome.IssueType.INVALID, "Please give the fhirValue of the observation.");
            }
            if (fhirValue < 0) {
                operationOutcomeClass.throwInvalidRequest(OperationOutcome.IssueSeverity.FATAL, OperationOutcome.IssueType.INVALID, "The entered value is below zero. Please give the a valid value of the observation.");
            }
            String fhirUnitLabel = theObservation.getValueQuantity().getUnit();
            if (fhirUnitLabel == null || fhirUnitLabel.trim().isEmpty()) {
                operationOutcomeClass.throwInvalidRequest(OperationOutcome.IssueSeverity.FATAL, OperationOutcome.IssueType.INVALID, "The unit of the given value is missing. Please enter the unit of the value.");
            }
            UnitLabel sdskUnitLabel = UnitLabel.fromBeautifulString(fhirUnitLabel);
            Date date = new Date();
            Date fhirMeasurementDate = date;
            sdskPatient.getOrCreateQualityInstance(qualityClass).createMeasurementInstance(fhirValue, sdskUnitLabel, fhirMeasurementDate, sdskPatient);
        } else if (theObservation.hasValueCodeableConcept()) {
            CodeableConcept valueCodeableConcept = theObservation.getValueCodeableConcept();
            if (valueCodeableConcept == null || valueCodeableConcept.isEmpty()) {
                operationOutcomeClass.throwInvalidRequest(OperationOutcome.IssueSeverity.FATAL, OperationOutcome.IssueType.INVALID, "The coding of the observation is missing. Please enter the coding of the observation value.");
            }
            String fhirSystem = theObservation.getValueCodeableConcept().getCoding().get(0).getSystem();
            String fhirCode = theObservation.getValueCodeableConcept().getCoding().get(0).getCode();
            if (fhirSystem == null || fhirSystem.trim().isEmpty()) {
                operationOutcomeClass.throwInvalidRequest(OperationOutcome.IssueSeverity.FATAL, OperationOutcome.IssueType.INVALID, "The system is missing. Please enter the system of the value.");
            }
            if (!iriValidator.vSystemIsAvailable(qualityClass, fhirSystem)) {
                operationOutcomeClass.throwInvalidRequest(OperationOutcome.IssueSeverity.FATAL, OperationOutcome.IssueType.INVALID, "The entered system is invalid. Please check your value system.");
            }
            if (fhirCode == null || fhirCode.trim().isEmpty()) {
                operationOutcomeClass.throwInvalidRequest(OperationOutcome.IssueSeverity.FATAL, OperationOutcome.IssueType.INVALID, "The code is missing. Please enter the code of the value.");
            }
            if (!iriValidator.vCodeIsAvailable(qualityClass, fhirCode)) {
                operationOutcomeClass.throwInvalidRequest(OperationOutcome.IssueSeverity.FATAL, OperationOutcome.IssueType.INVALID, "The entered code is invalid. Please check your value code.");
            }

            IRI codeIRICat = Utils.ontology.getFactory().createIRI(fhirSystem + fhirCode);

            if (!iriValidator.vIriIsAvailable(qualityClass, codeIRICat)) {
                operationOutcomeClass.throwInvalidRequest(OperationOutcome.IssueSeverity.FATAL, OperationOutcome.IssueType.INVALID, "The entered IRI is invalid. Please check the combination of your system and code.");
            }
            Date fhirMeasurementDate = new Date();
            if (codeIRI.equals("http://sfb125.de/ontology/factorontology/true_value_specification") || codeIRI.equals("http://sfb125.de/ontology/factorontology/false_value_specification")) {
                UnitLabel unitLabel = UnitLabel.UNITLESS;
                sdskPatient.getOrCreateQualityInstance(qualityClass).createCategoricalMeasurementInstance(codeIRICat, Utils.ontology.getFactory().createIRI("http://sfb125.de/ontology/factorontology/boolean_measurement_datum"), fhirMeasurementDate, sdskPatient);
            } else {
                UnitLabel unitLabel = UnitLabel.CATEGORICAL;
                sdskPatient.getOrCreateQualityInstance(qualityClass).createCategoricalMeasurementInstance(codeIRICat, Utils.ontology.getFactory().createIRI("http://purl.obolibrary.org/obo/OBI_0000938"), fhirMeasurementDate, sdskPatient);
            }
        }
        return new MethodOutcome().setId(theObservation.getIdElement());
    }


    /**
     * Creates a FHIR observation object from the SDSK quality object.
     *
     * @param measurementInstance the measurement instance of the quality
     * @param qualityClass        the BFO quality class
     * @param patientID           the patient's ID
     * @return FHIR observation object
     */
    public Observation createFHIRObservation(IAOMeasurementInstance measurementInstance, BFOQualityClass qualityClass, ReferenceParam patientID) {
        Observation observation = new Observation();
        observation.addIdentifier().setSystem(measurementInstance.getIRI().getNamespace()).setValue(measurementInstance.getIRI().getLocalName());
        observation.getCode().addCoding().setCode(qualityClass.getIRI().getLocalName()).setSystem(qualityClass.getIRI().getNamespace()).setDisplay(qualityClass.getLabel(Language.getStandardLanguage()));
        Date sdsdkDate = measurementInstance.getMeasurementDate();
        observation.setIssued(sdsdkDate);
        observation.getSubject().setReference(patientID.getValue());
        observation.setId(measurementInstance.getURI());
        observation = setFHIRValueType(measurementInstance, observation);
        return observation;
    }

    /**
     * Set the value type of the quality in the FHIR observation object.
     *
     * @param measurementInstance the measurement instance of the quality
     * @param observation         FHIR observation object
     * @return FHIR observation object with value type set
     */
    public Observation setFHIRValueType(IAOMeasurementInstance measurementInstance, Observation observation) {
        UnitLabel unitLabel = measurementInstance.getMeasurementUnitLabel();
        if (unitLabel == UnitLabel.CATEGORICAL) {
            Object valueCategorical = measurementInstance.getMeasurementValue(unitLabel);
            IRI valueString = measurementInstance.getValueSpecification();
            observation.getValueCodeableConcept().addCoding().setSystem(valueString.getNamespace()).setCode(valueString.getLocalName()).setDisplay(valueCategorical.toString());
        } else if (unitLabel == UnitLabel.UNITLESS) {
            Object valueBoolean = measurementInstance.getMeasurementValue(unitLabel);
            IRI valueSepc = measurementInstance.getValueSpecification();
            observation.getValueCodeableConcept().addCoding().setSystem(valueSepc.getNamespace()).setCode(valueSepc.getLocalName()).setDisplay(valueBoolean.toString());
        } else {
            Object valueNumerical = measurementInstance.getMeasurementValue(unitLabel);
            IRI labelIRI1 = Utils.ontology.getFactory().createIRI(unitLabel.getURI());
            observation.setValue(new Quantity().setValue((double) valueNumerical).setUnit(unitLabel.getBeautifulString()).setSystem(labelIRI1.getNamespace()).setCode(labelIRI1.getLocalName()));
        }
        return observation;
    }
}