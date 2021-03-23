package de.dkfz.cami.sdsk.server.restInterfaces;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class ObservationRestInterface {

    public void createObservationtNumericalRest(String observationSystem, String observationCode, String patientID, String value, String valueUnit) {

        String payload = "{\n" +
                "    \"resourceType\": \"Observation\",\n" +
                "    \"code\": {\n" +
                "        \"coding\": [\n" +
                "            {\n" +
                "                \"system\": \"" + observationSystem + "\",\n" +
                "                \"code\": \"" + observationCode + "\"\n" +
                "            }\n" +
                "        ]\n" +
                "    },\n" +
                "    \"subject\": {\n" +
                "        \"reference\": \"Patient/" + patientID + "\"\n" +
                "    },\n" +
                "    \"valueQuantity\": {\n" +
                "        \"value\": " + value + ",\n" +
                "        \"unit\": \"" + valueUnit + "\"\n" +
                "    }\n" +
                "}";
        StringEntity entity = new StringEntity(payload,
                ContentType.APPLICATION_JSON);

        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost request = new HttpPost("http://localhost:8080/Observation");
        request.setEntity(entity);

        HttpResponse response = null;
        try {
            response = httpClient.execute(request);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(response.getStatusLine().getStatusCode());
    }

    public void createObservationtCategoricalRest(String observationSystem, String observationCode, String patientID, String valueSystem, String valueCode) {
        String payload = "{\n" +
                "    \"resourceType\": \"Observation\",\n" +
                "    \"code\": {\n" +
                "    \"coding\": [\n" +
                "      {\n" +
                "        \"system\": \"" + observationSystem + "\",\n" +
                "        \"code\": \"" + observationCode + "\"\n" +
                "      }\n" +
                "    ]\n" +
                "  },\n" +
                "  \"subject\":{\n" +
                "      \"reference\": \"Patient/" + patientID + "\"\n" +
                "  },\n" +
                "  \"valueCodeableConcept\": {\n" +
                "      \"coding\": [\n" +
                "          {\n" +
                "              \"system\": \"" + valueSystem + "\",\n" +
                "              \"code\": \"" + valueCode + "\"\n" +
                "          }\n" +
                "      ]\n" +
                "  }\n" +
                "}";
        StringEntity entity = new StringEntity(payload,
                ContentType.APPLICATION_JSON);

        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost request = new HttpPost("http://localhost:8080/Observation");
        request.setEntity(entity);

        HttpResponse response = null;
        try {
            response = httpClient.execute(request);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(response.getStatusLine().getStatusCode());
    }

    public void getObservationByIdRest(String observationID) {

        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpGet request = new HttpGet("http://localhost:8080/Observation/" + observationID);
        HttpResponse response = null;
        try {
            response = httpClient.execute(request);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(response.getStatusLine().getStatusCode());
        printResponse(response);
    }

    public void getObservationsForPatientRest(String patientID) {

        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpGet request = new HttpGet("http://localhost:8080/Observation?subject=Patient/" + patientID);
        HttpResponse response = null;
        try {
            response = httpClient.execute(request);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(response.getStatusLine().getStatusCode());
        printResponse(response);
    }

    public void getObservationsForPatientAndCodeRest(String patientID, String code) {
        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpGet request = new HttpGet("http://localhost:8080/Observation?subject=Patient/" + patientID + "&code=" + code);
        HttpResponse response = null;
        try {
            response = httpClient.execute(request);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(response.getStatusLine().getStatusCode());
        printResponse(response);
    }

    public void printResponse(HttpResponse response) {
        StringBuilder sb = new StringBuilder();
        try {
            BufferedReader reader =
                    new BufferedReader(new InputStreamReader(response.getEntity().getContent()), 65728);
            String line = null;

            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("Response body " + sb.toString());
    }
}