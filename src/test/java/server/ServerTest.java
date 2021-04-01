package server;

import model.ObservationModel;
import org.hl7.fhir.r4.model.Enumerations;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

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
        ObservationModel observation = new ObservationModel("http://sfb125.de/ontology/ihCCApplicationOntology/", "bilirubin_concentration", 1, "mg/dl");
        String patientID = server.createPatient("Doe", "John", Enumerations.AdministrativeGender.MALE);
        String observationID = server.createNumericalObservation(observation, patientID);
        assertEquals(167, observationID.length());
    }

    @Test
    public void testCreateCategoricalObs() {
        ObservationModel observation = new ObservationModel("http://sfb125.de/ontology/ihCCApplicationOntology/", "lymph_node_staging", "http://sfb125.de/ontology/ihCCApplicationOntology/", "cN1");
        String patientID = server.createPatient("Doe", "Jane", Enumerations.AdministrativeGender.FEMALE);
        String observationID = server.createCategoricalObservation(observation, patientID);
        assertEquals(162, observationID.length());
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
        ArrayList<ObservationModel> observations1 = server.getObservationsOfPatient(patientID);
        observations1.size();
        assertEquals(1, observations1.size());
        ObservationModel observationNumerical = new ObservationModel("http://sfb125.de/ontology/ihCCApplicationOntology/", "bilirubin_concentration", 1, "mg/dl");
        server.createNumericalObservation(observationNumerical, patientID);
        ArrayList<ObservationModel> observations2 = server.getObservationsOfPatient(patientID);
        assertEquals(2, observations2.size());
        ObservationModel observationCategorical = new ObservationModel("http://sfb125.de/ontology/ihCCApplicationOntology/", "lymph_node_staging", "http://sfb125.de/ontology/ihCCApplicationOntology/", "cN1");
        server.createCategoricalObservation(observationCategorical, patientID);
        ArrayList<ObservationModel> observations3 = server.getObservationsOfPatient(patientID);
        assertEquals(3, observations3.size());
    }
}