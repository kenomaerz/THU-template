import model.Gender;
import server.Server;

public class Main {

    public static void main(String[] args) {
        Server server = new Server();
        //server.createNumericalObservation("cbc7cef3-9bc4-4e2c-aecd-fe4e3ccbe136");
      //  server.createPatient();
      //  server.getPatients();
        server.getObservations("cbc7cef3-9bc4-4e2c-aecd-fe4e3ccbe136");
    }
}
