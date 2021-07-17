package controller;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mashape.unirest.http.Unirest;

import com.mashape.unirest.http.exceptions.UnirestException;

import modelCTSNOMED.CTmodel;
import modelCTSNOMED.CTmodel.CTDescription;
import view.CTView;

public class ctcontroller {

	private CTmodel model;
	private CTView view;
	
	
	public ctcontroller(CTmodel model, CTView view) {
		this.model = model;
		this.view = view;
	}
	
	public void setView(CTView view) {
		this.view = view;
	}
	
	public void showTerm(String term, String semanticTag, int limit) {
		try {
			model.ctDescriptions(term, semanticTag, limit);
			view.printDescriptions(model.getDescriptions());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnirestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void showSemanticTags() {
		try {
			model.ctSemanticTags();
			view.printSemanticTags(model.getSemanticTags());
		} catch (UnirestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void saveToFile() {
		
		/*
		 * descriptions = new ArrayList<CTDescription>(); String body =
		 * Unirest.get("https://snowstorm.test-nictiz.nl/browser/MAIN/descriptions?")
		 * .queryString("term", Arrays.asList(term)) .queryString("semanticTag",
		 * Arrays.asList(semanticTag)) .queryString("limit", Integer.toString(limit))
		 * .asString() .getBody();
		 * 
		 * String jsonString = body; JSONObject obj = new JSONObject(jsonString);
		 * JSONArray arr = obj.getJSONArray("items");
		 * 
		 * for (int i = 0; i < arr.length(); i++) {
		 * 
		 * String termCT = arr.getJSONObject(i).getString("term"); JSONObject concept_id
		 * = arr.getJSONObject(i).getJSONObject("concept"); descriptions.add(new
		 * CTDescription(termCT, concept_id.getString("conceptId")));
		 * 
		 * 
		 * 
		 * try {
		 * 
		 * BufferedWriter bw = new BufferedWriter(new FileWriter(term + ".txt", true));
		 * // File datei = new File (term + ".txt");
		 * 
		 * // writer = new FileWriter(datei, true); model.ctDescriptions(term,
		 * semanticTag, limit); //bw.write(model.ctDescriptions(term, semanticTag,
		 * limit)); bw.write(view.printDescriptions(model.getDescriptions()));
		 * //writer.write(System.getProperty("line.separator"));
		 * 
		 * bw.flush(); bw.close();
		 * 
		 * 
		 * } catch (IOException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); } catch (UnirestException e) { // TODO Auto-generated
		 * catch block e.printStackTrace(); }
		 */
	}

}
