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
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;

import java.io.File;
import java.io.FileWriter;


public class CTmodel2 {

	
	
	
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

	public void ctDescriptions(String term, String semanticTag) throws IOException, UnirestException {
		
		FileWriter writer;
		File datei = new File ("Concept.txt");
		

		String body = Unirest.get("https://snowstorm.test-nictiz.nl/browser/MAIN/descriptions?")
				.queryString("term", Arrays.asList(term))
				.queryString("semanticTag", Arrays.asList(semanticTag))
				.queryString("limit", "10")
				.asString()
				.getBody();

		String jsonString = body;
		JSONObject obj = new JSONObject(jsonString);
		JSONArray arr = obj.getJSONArray("items");

		for (int i = 0; i < arr.length(); i++) {
			
			String termCT = arr.getJSONObject(i).getString("term");
			JSONObject concept_id = arr.getJSONObject(i).getJSONObject("concept");
			System.out.println(i + 1 + ". " + "\tTerm: " + termCT + "\n\tConcept ID: " + concept_id.get("conceptId") + "\n ");
			
		
			
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
}
//Link:
//http://kong.github.io/unirest-java/#requests