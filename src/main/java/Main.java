import model.Gender;
import server.Server;

public class Main {

    public static void main(String[] args) {
        Server server = new Server();
        // Observation numericalObservation = new Observation("http://sfb125.de/ontology/ihCCApplicationOntology/", "albumin_concentration", 0.4, "g/l");
        //Observation categoricalObservation = new Observation("http://sfb125.de/ontology/ihCCApplicationOntology/", "fatty_liver_severity", "http://sfb125.de/ontology/ihCCApplicationOntology/", "fatty_liver_stage_0");
        // server.createNumericalObservation(numericalObservation, "378cfbde-d9bb-4bba-9b34-206a9d4c3b4b");
        // server.createCategoricalObservation(categoricalObservation, "378cfbde-d9bb-4bba-9b34-206a9d4c3b4b");
       // server.createPatient("Doe", "John", Gender.MALE);
        server.getPatients();
        //server.getObservations("cbc7cef3-9bc4-4e2c-aecd-fe4e3ccbe136");
    }
}
