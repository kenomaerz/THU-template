package server;

public class Main {
    public static void main(String[] args) {
        ServerFHIR fhir = new ServerFHIR();
        fhir.getPatients();
    }
}
