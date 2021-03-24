import server.Server;

public class Main {

    public static void main(String[] args) {
        Server server = new Server();
        boolean serverConnection = server.testConnection();
        System.out.println(serverConnection);
        // Observation numericalObservation = new Observation("http://sfb125.de/ontology/ihCCApplicationOntology/", "albumin_concentration", 0.4, "g/l");
        //Observation categoricalObservation = new Observation("http://sfb125.de/ontology/ihCCApplicationOntology/", "fatty_liver_severity", "http://sfb125.de/ontology/ihCCApplicationOntology/", "fatty_liver_stage_0");
        // server.createNumericalObservation(numericalObservation, "378cfbde-d9bb-4bba-9b34-206a9d4c3b4b");
        // server.createCategoricalObservation(categoricalObservation, "378cfbde-d9bb-4bba-9b34-206a9d4c3b4b");
        // server.createPatient("Doe", "John", Gender.MALE);
        //server.getPatients();
       // server.getObservations("f40e5596-4f6c-40e7-ad5e-0c96891f3500");
    }
}
