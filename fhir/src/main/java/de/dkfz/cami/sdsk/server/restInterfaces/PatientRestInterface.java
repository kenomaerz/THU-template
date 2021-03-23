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


public class PatientRestInterface {

    public void createPatientRest(String familyname, String prename, String gender) {

        String payload = "{\n" +
                "    \"resourceType\": \"Patient\",\n" +
                "    \"name\": [\n" +
                "        {\n" +
                "            \"family\": \"" + familyname + "\",\n" +
                "            \"given\": [\n" +
                "                \"" + prename + "\"\n" +
                "            ]\n" +
                "        }\n" +
                "    ],\n" +
                "    \"gender\": \"" + gender + "\"\n" +
                "}";
        StringEntity entity = new StringEntity(payload,
                ContentType.APPLICATION_JSON);

        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost request = new HttpPost("http://localhost:8080/Patient");
        request.setEntity(entity);

        HttpResponse response = null;
        try {
            response = httpClient.execute(request);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(response.getStatusLine().getStatusCode());
    }

    public void getAllPatientsRest() {

        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpGet request = new HttpGet("http://localhost:8080/Patient");
        HttpResponse response = null;
        try {
            response = httpClient.execute(request);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(response.getStatusLine().getStatusCode());
        printResponse(response);
    }

    public void getPatientByIdRest(String patientID) {

        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpGet request = new HttpGet("http://localhost:8080/Patient/" + patientID);
        HttpResponse response = null;
        try {
            response = httpClient.execute(request);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(response.getStatusLine().getStatusCode());
        printResponse(response);
    }


    public void printResponse(HttpResponse response){
        StringBuilder sb = new StringBuilder();
        try {
            BufferedReader reader =
                    new BufferedReader(new InputStreamReader(response.getEntity().getContent()), 65728);
            String line = null;

            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        }
        catch (IOException e) { e.printStackTrace(); }
        catch (Exception e) { e.printStackTrace(); }


        System.out.println("Response body " + sb.toString());
    }
}
