package server;

import model.AbstractObservationModel;
import model.BooleanObservationModel;
import model.CategorialObservationModel;
import model.NumericalObservationModel;

import org.hl7.fhir.r4.model.Enumerations;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ServerTest {
    private static Server server;

    @BeforeClass
    public static void setUp() {
        server = new Server();
        server.testConnection();
    }

    @Test
    public void testCreatePatient() {
        String patientID = server.createPatient("Doe", "Jane", Enumerations.AdministrativeGender.FEMALE);
        assertEquals(36, patientID.length());
    }

    @Test
    public void testCreateNumericalObs() {
        NumericalObservationModel observation = new NumericalObservationModel("http://sfb125.de/ontology/ihCCApplicationOntology/", "bilirubin_concentration", 1.123, "mg/dl");
        String patientID = server.createPatient("Doe", "John", Enumerations.AdministrativeGender.MALE);
        NumericalObservationModel newObservation = (NumericalObservationModel) server.createObservation(observation, patientID);
        assertEquals (newObservation.getValue(), 1.123, 0.000001);
    }

    @Test
    public void testCreateCategoricalObs() {
        CategorialObservationModel observation = new CategorialObservationModel("http://sfb125.de/ontology/ihCCApplicationOntology/", "lymph_node_staging", "http://sfb125.de/ontology/ihCCApplicationOntology/", "cN1");
        String patientID = server.createPatient("Doe", "Jane", Enumerations.AdministrativeGender.MALE);
        CategorialObservationModel newObservation = (CategorialObservationModel) server.createObservation(observation, patientID);
        assertEquals (newObservation.getValueCode(), "cN1");
        assertEquals (newObservation.getValueSystem(), "http://sfb125.de/ontology/ihCCApplicationOntology/");
    }

    @Test
    public void testCreateBooleanObs() {
        BooleanObservationModel observation = new BooleanObservationModel("http://sfb125.de/ontology/ihCCApplicationOntology/", "cirrhosis_observation", true);
        String patientID = server.createPatient("Doe", "Jane", Enumerations.AdministrativeGender.FEMALE);
        BooleanObservationModel newObservation = (BooleanObservationModel) server.createObservation(observation, patientID);
        assertTrue(newObservation.getValue());
    }

    @Test
    public void testGetAllPatients() {
        ArrayList<String> allPatients = server.getPatients();
        int numberAllPatients = allPatients.size();
        server.createPatient("Doe", "John", Enumerations.AdministrativeGender.MALE);
        ArrayList<String> allPatientsAdded = server.getPatients();
        assertEquals(numberAllPatients + 1, allPatientsAdded.size());
    }

    @Test
    public void testGetAllObsForPatient() {
        String patientID = server.createPatient("Doe", "Jane", Enumerations.AdministrativeGender.FEMALE);
        ArrayList<AbstractObservationModel> observations1 = server.getObservationsOfPatient(patientID);
        observations1.size();
        assertEquals(1, observations1.size());
        NumericalObservationModel observationNumerical = new NumericalObservationModel("http://sfb125.de/ontology/ihCCApplicationOntology/", "bilirubin_concentration", 1, "mg/dl");
        server.createObservation(observationNumerical, patientID);
        ArrayList<AbstractObservationModel> observations2 = server.getObservationsOfPatient(patientID);
        assertEquals(2, observations2.size());
        CategorialObservationModel observationCategorical = new CategorialObservationModel("http://sfb125.de/ontology/ihCCApplicationOntology/", "lymph_node_staging", "http://sfb125.de/ontology/ihCCApplicationOntology/", "cN1");
        server.createObservation(observationCategorical, patientID);
        ArrayList<AbstractObservationModel> observations3 = server.getObservationsOfPatient(patientID);
        assertEquals(3, observations3.size());
    }
}