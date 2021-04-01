package server;

import model.ObservationModel;
import org.hl7.fhir.r4.model.Enumerations;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class ServerFHIRTest {

    //private static String patientID;
    //private static String observationID;
    //private static ArrayList<String> allPatients;
    //private static ArrayList<String> allPatientsEmpty;
    //private static ArrayList<ObservationModel> observations1;
    //private static ArrayList<ObservationModel> observations2;
    //private static ArrayList<ObservationModel> observations3;
    private static ServerFHIR server;

    @BeforeClass
    public static void setUp() {
        server = new ServerFHIR();
        server.testConnection();
    }

    @After
    public void afterClassClearContext() throws Exception {
        System.out.println("After");
        //  TestUtil.clearAllStaticFieldsForUnitTest();
        server.ctx.getRestfulClientFactory().setConnectTimeout(1);
    }

    @Test
    public void testCreatePatient() {
        //ServerFHIR server = new ServerFHIR();
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
        ArrayList<String> allPatientsEmpty = server.getPatients();
        assertEquals(0, allPatientsEmpty.size());
        server.createPatient("Doe", "John", Enumerations.AdministrativeGender.MALE);
        ArrayList<String> allPatients = server.getPatients();
        assertEquals(1, allPatients.size());
    }

    @Test
    public void testGetAllObsForPatient() {
        String patientID = server.createPatient("Doe", "Jane", Enumerations.AdministrativeGender.FEMALE);
        ArrayList<ObservationModel> observations1 = server.getObservationsOfPatient(patientID);
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