/**
 *
 */
package server;

import error.DuplicateServerException;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import kong.unirest.json.JSONObject;
import model.Gender;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author Keno März
 *
 */
public class Server {
    private static final String BASE_URL = "http://localhost:8080";
    private static int instances = 0;


    public Server() {
        System.out.println("Constructing Server");
        instances++;
        if (instances > 1) {
            throw new DuplicateServerException("More than one instance of server created! Please reuse previous instance!");
        }
    }

    public boolean testConnection() {
        System.out.println("TestConnection");
        HttpResponse<JsonNode> response = Unirest.post("http://httpbin.org/post")
                .header("accept", "application/json")
                .queryString("apiKey", "123")
                .field("parameter", "value")
                .field("firstname", "Gary")
                .asJson();
        System.out.println(response.getStatusText());
        System.out.println(response.isSuccess());
        System.out.println(response.getStatus());
        return false;
    }

    //todo delete after discussed
    /*public String createPatient() {
        System.out.println("TestConnection");

        HttpResponse<JsonNode> response = Unirest.post("http://localhost:8080/Patient")
                .header("content-type", "application/json")
                .body("{\n" +
                        "    \"resourceType\": \"Patient\",\n" +
                        "    \"name\": [\n" +
                        "        {\n" +
                        "            \"family\": \"Doe\",\n" +
                        "            \"given\": [\n" +
                        "                \"John\"\n" +
                        "            ]\n" +
                        "        }\n" +
                        "    ],\n" +
                        "    \"gender\": \"male\"\n" +
                        "}")
                .asJson();
        System.out.println(response.getBody());
        System.out.println(response.getStatusText());
        System.out.println(response.isSuccess());
        System.out.println(response.getStatus());

        return null;
    }*/

/*	public String createPatient(Gender gender) {
		System.out.println("TestConnection");
		HashMap<String, Object> body = new HashMap<>();
		body.put("resourceType", "Patient");
		HashMap<String, Object> name= new HashMap<>();
		name.put("family", "Doe");
		name.put("given", new ArrayList<String>().add("John"));
		body.put("name", new ArrayList<Object>().add(name));

		HttpResponse<JsonNode> response = Unirest.post("http://localhost:8080/Patient")
			      .header("accept", "application/json")
			      .body(body)
			      .asJson();
		System.out.println(response.getBody());
		System.out.println(response.getStatusText());
		System.out.println(response.isSuccess());
		System.out.println(response.getStatus());
		
		return null;
	}*/

    //todo firstnames, lastname
    public String createPatient(Gender gender) {
        System.out.println("TestConnection");
        HashMap<String, Object> body = new HashMap<>();
        HashMap<String, Object> name = new HashMap<>();
        Object[] names = {name};
        String[] firstnames = {"John"};
        body.put("resourceType", "Patient");
        name.put("family", "Doe");
        name.put("given", firstnames);
        body.put("name", names);
        body.put("gender", gender);


        JSONObject jsonObject = new JSONObject(body);
        System.out.println(jsonObject);

        HttpResponse<JsonNode> response = Unirest.post("http://localhost:8080/Patient")
                .header("content-type", "application/json")
                .body(jsonObject)
                .asJson();
        System.out.println(response.getBody());
        System.out.println(response.getStatusText());
        System.out.println(response.isSuccess());
        System.out.println(response.getStatus());

        return null;
    }


    public String createNumericalObservation(String observationSystem, String observationCode, String patientID, String value, String unit) {
        System.out.println("TestConnection");
        HashMap<String, Object> body = new HashMap<>();
        HashMap<String, Object> code = new HashMap<>();
        HashMap<String, Object> codings = new HashMap<>();
        HashMap<String, Object> reference = new HashMap<>();
        HashMap<String, Object> valueQuantity = new HashMap<>();
        Object[] coding = {codings};
        body.put("resourceType", "Observation");
        body.put("code", code);
        code.put("coding", coding);
        codings.put("system", "http://sfb125.de/ontology/ihCCApplicationOntology/");
        codings.put("code", "albumin_concentration");
        body.put("subject", reference);
        reference.put("reference", "Patient/" + patientID);
        body.put("valueQuantity", valueQuantity);
        valueQuantity.put("value", 0.4);
        valueQuantity.put("unit", "g/l");


        JSONObject jsonObject = new JSONObject(body);
        System.out.println(jsonObject);

        HttpResponse<JsonNode> response = Unirest.post("http://localhost:8080/Observation")
                .header("content-type", "application/json")
                .body(jsonObject)
                .asJson();
        System.out.println(response.getBody());
        System.out.println(response.getStatusText());
        System.out.println(response.isSuccess());
        System.out.println(response.getStatus());

        return null;
    }

    public ArrayList<String> getPatients() {

        HttpResponse<JsonNode> request = Unirest.get("http://localhost:8080/Patient")
                .asJson();
        JSONObject jsonObj = request.getBody().getObject();
        System.out.println(jsonObj);
       /* ArrayList<String> resultsEnd = new ArrayList<>();
        JSONArray c = jsonObj.getJSONArray("id");
        for (int i = 0; i < c.length(); i++) {
            JSONObject obj = c.getJSONObject(i);
            String A = obj.getString("id");
            System.out.println(A);
        }
        System.out.println(resultsEnd);*/
        System.out.println(request.getStatus());
        return null;
    }

    public HashMap<String, Object> getObservations(String patientID) {
        HttpResponse<JsonNode> request = Unirest.get("http://localhost:8080/Observation?subject=" + patientID)
                .asJson();
        JSONObject jsonObj = request.getBody().getObject();
        System.out.println(jsonObj);
        System.out.println(request.getStatus());
        return null;
    }

}

