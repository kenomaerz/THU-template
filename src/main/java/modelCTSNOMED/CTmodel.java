package modelCTSNOMED;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.json.JSONArray;
import org.json.JSONObject;

import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class CTmodel {

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

	public void ctDescriptions(String term) throws IOException, UnirestException {

		String body = Unirest.get("https://snowstorm.test-nictiz.nl/browser/MAIN/descriptions?")
				.queryString("term", "age")
				.queryString("semanticTag", "finding")
				.queryString("limit", "10")
				.asString()
				.getBody();

		String jsonString = body;
		JSONObject obj = new JSONObject(jsonString);
		JSONArray arr = obj.getJSONArray("items");

		for (int i = 0; i < arr.length(); i++) {
			String termCT = arr.getJSONObject(i).getString("term");
			System.out.println(i + 1 + ". " + "\tTerm: " + termCT + "\n ");

		}

	}
}
//  https://www.callicoder.com/java-read-write-csv-file-apache-commons-csv/
//	http://commons.apache.org/proper/commons-csv/download_csv.cgi