/**
 * 
 */
package server;

import java.util.ArrayList;
import java.util.HashMap;

import error.DuplicateServerException;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;

import model.Gender;

/**
 * @author Keno März
 *
 */
public class Server {
	private static final String BASE_URL = "http://localhost:8080";
	private static int instances = 0;

	
	public Server() {
		System.out.println("Constructing Server");
		instances ++;
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
	
	public String createPatient(Gender gender) {
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
	}
	
	public String createObservation(String patientID) {
		return null;
	}
	
	public ArrayList<String> getPatients() {
		return null;
	}
	
	public HashMap<String, Object> getObservations(String patientID) {
		return null;
	}
	
}
