package server;

import model.Gender;
import model.Observation;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class ServerTest {

    private static Server server;

    //throws exception
    @BeforeClass
    public static void setUp() {
        server = new Server();
        server.testConnection();
    }

    @Test
    public void testCreatePatient() {
        //Server server = new Server();
        //server.testConnection();
        String patientID = server.createPatient("Doe", "Jane", Gender.FEMALE);
        assertEquals(36, patientID.length());
    }

    @Test
    public void testCreateNumericalObs() {
        Observation observation = new Observation("http://sfb125.de/ontology/ihCCApplicationOntology/", "bilirubin_concentration", 1, "mg/dl");
        String patientID = server.createPatient("Doe", "Jane", Gender.FEMALE);
        String observationID = server.createNumericalObservation(observation, patientID);
        assertEquals(167, observationID.length());
    }

    @Test
    public void testCreateCategoricalObs() {
        Observation observation = new Observation("http://sfb125.de/ontology/ihCCApplicationOntology/", "lymph_node_staging", "http://sfb125.de/ontology/ihCCApplicationOntology/", "cN1");
        String patientID = server.createPatient("Doe", "Jane", Gender.FEMALE);
        String observationID = server.createCategoricalObservation(observation, patientID);
        assertEquals(162, observationID.length());

    }

    //todo beachte auch leere patients list!!
    /*@Test
    public void testGetAllPatients() {
        //Server server = new Server();
        //server.testConnection();
        ArrayList<String> allPatientsEmpty = server.getPatients();
        assertEquals(3, allPatientsEmpty.size());
        server.createPatient("Doe", "Jane", Gender.FEMALE);
        ArrayList<String> allPatients = server.getPatients();
        assertEquals(4, allPatients.size());
    }*/
    /*
    @Test
    public void testGetAllObsForPatient() {
        //Server server = new Server();
        //server.testConnection();
        patient = server.createPatient("Doe", "Jane", Gender.FEMALE);
    }*/
}
