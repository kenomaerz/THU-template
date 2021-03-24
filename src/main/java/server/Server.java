/**
 *
 */
package server;

import error.DuplicateServerException;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;
import model.Gender;
import model.Observation;

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

    public String createPatient(String lastname, String firstname, Gender gender) {
        System.out.println("TestConnection");
        HashMap<String, Object> body = new HashMap<>();
        HashMap<String, Object> name = new HashMap<>();
        Object[] names = {name};
        String[] firstnames = {firstname};
        body.put("resourceType", "Patient");
        name.put("family", lastname);
        name.put("given", firstnames);
        body.put("name", names);
        body.put("gender", gender.toString().toLowerCase());


        JSONObject jsonObject = new JSONObject(body);
        System.out.println(jsonObject);

        /*Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonParser jp = new JsonParser();
        JsonElement je = jp.parse(jsonObject.toString());
        String prettyJsonString = gson.toJson(je);

        System.out.println(prettyJsonString);*/

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


    public String createNumericalObservation(Observation observation, String patientID) {
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
        codings.put("system", observation.getObservationSystem());
        codings.put("code", observation.getObservationCode());
        body.put("subject", reference);
        reference.put("reference", "Patient/" + patientID);
        body.put("valueQuantity", valueQuantity);
        valueQuantity.put("value", observation.getNumericalValue());
        valueQuantity.put("unit", observation.getUnit());

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

    public String createCategoricalObservation(Observation observation, String patientID) {
        System.out.println("TestConnection");
        HashMap<String, Object> body = new HashMap<>();
        HashMap<String, Object> code = new HashMap<>();
        HashMap<String, Object> codings = new HashMap<>();
        HashMap<String, Object> reference = new HashMap<>();
        HashMap<String, Object> valueCodeableConcept = new HashMap<>();
        HashMap<String, Object> codingsValue = new HashMap<>();
        Object[] coding = {codings};
        body.put("resourceType", "Observation");
        body.put("code", code);
        code.put("coding", coding);
        codings.put("system", observation.getObservationSystem());
        codings.put("code", observation.getObservationCode());
        body.put("subject", reference);
        reference.put("reference", "Patient/" + patientID);
        body.put("valueCodeableConcept", valueCodeableConcept);
        Object[] codingValue = {codingsValue};
        valueCodeableConcept.put("coding", codingValue);
        codingsValue.put("system", observation.getValueSystem());
        codingsValue.put("code", observation.getValueCode());

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
        ArrayList<String> patientIDs = new ArrayList<>();
        JSONArray items = jsonObj.getJSONArray("entry");
        System.out.println(items);

        for (int i = 0; i < items.length(); i++) {
            JSONObject objects = items.getJSONObject(i);
            JSONObject objectResource = (JSONObject) objects.get("resource");
            String id = objectResource.getString("id");
            patientIDs.add(id);
        }
        System.out.println(patientIDs);
        return patientIDs;
    }

    public Observation getObservations(String patientID) {
        HttpResponse<JsonNode> request = Unirest.get("http://localhost:8080/Observation?subject=" + patientID)
                .asJson();
        JSONObject jsonObj = request.getBody().getObject();
        System.out.println(jsonObj);
        System.out.println(request.getStatus());
        return null;
    }

    // todo getObservation

}

