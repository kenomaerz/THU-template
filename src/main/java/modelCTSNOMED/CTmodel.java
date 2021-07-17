package modelCTSNOMED;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

public class CTmodel {
	
	private List<CTDescription> descriptions;
	private ArrayList<String> semanticTags;


	public void ctConcepts(String term) throws IOException, UnirestException {

		String body = Unirest.get("https://snowstorm.test-nictiz.nl/MAIN/concepts?activeFilter=true")
				.queryString("term", Arrays.asList(term))
				.queryString("termActive", "true")
				.queryString("offset", "0")
				.queryString("limit", "10")
				.asString()
				.getBody();

		String jsonString = body;
		JSONObject obj = new JSONObject(jsonString);
		JSONArray arr = obj.getJSONArray("items");

		for (int i = 0; i < arr.length(); i++) {

			String concept_id = arr.getJSONObject(i).getString("conceptId");
			JSONObject pt = arr.getJSONObject(i).getJSONObject("pt");
			JSONObject fsn = arr.getJSONObject(i).getJSONObject("fsn");

			System.out.println(i + 1 + ". " + "\tConcept ID: " + concept_id + "\n\tPrefered name: " + pt.get("term")
					+ "\n\tFully specified name: " + fsn.get("term") + "\n ");

		}
	}

	public void ctDescriptions(String term, String semanticTag, int limit) throws IOException, UnirestException {
		
		
	
		descriptions = new ArrayList<CTDescription>();
		String body = Unirest.get("https://snowstorm.test-nictiz.nl/browser/MAIN/descriptions?")
				.queryString("term", Arrays.asList(term))
				.queryString("semanticTag", Arrays.asList(semanticTag))
				.queryString("limit", Integer.toString(limit))
				.asString()
				.getBody();

		String jsonString = body;
		JSONObject obj = new JSONObject(jsonString);
		JSONArray arr = obj.getJSONArray("items");
		
		
		FileWriter writer;
		File datei = new File (term + ".txt");

		for (int i = 0; i < arr.length(); i++) {
			
			String termCT = arr.getJSONObject(i).getString("term");
			JSONObject concept_id = arr.getJSONObject(i).getJSONObject("concept");
			descriptions.add(new CTDescription(termCT, concept_id.getString("conceptId")));
		//System.out.println(i + 1 + ". " + "\tTerm: " + termCT + "\n\tConcept ID: " + concept_id.get("conceptId") + "\n ");
			
			try {
				writer = new FileWriter(datei, true);
				writer.write(i + 1 + ". " + "\tTerm: " + termCT + "\n\tConcept ID: " + concept_id.get("conceptId") + "\n ");
				//writer.write(System.getProperty("line.separator"));
				
				writer.flush();
				writer.close();
				
				
			} catch (IOException e) {
				e.printStackTrace();
			}
	
			

		}

	}
	
	public void ctSemanticTags() throws UnirestException {
		semanticTags = new ArrayList<String>();
		
		String body = Unirest.get("https://snowstorm.test-nictiz.nl/MAIN/descriptions/semantictags").asString().getBody();

		String jsonString = body;
		JSONObject obj = new JSONObject(jsonString);
		JSONArray arr = obj.names();
		for (int i = 0; i < arr.length(); i++) {
			semanticTags.add(arr.getString(i));
		}
	}
	
	public List<CTDescription> getDescriptions() {
		return descriptions;
	}
	
	public List<String> getSemanticTags() {
		return semanticTags;
	}
	
	public static class CTDescription {		
		//ToDo: add fields as required
		private String term;
		private String conceptId;
		
		public CTDescription(String term, String conceptId) {
			this.term = term;
			this.conceptId = conceptId;
		}
		
		public String getTerm() {
			return term;
		}
		
		public String getConceptId() {
			return conceptId;
		}
		
	}
}
//Link:
//http://kong.github.io/unirest-java/#requestsuests