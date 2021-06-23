package controller;

import java.io.IOException;
import com.mashape.unirest.http.exceptions.UnirestException;

import modelCTSNOMED.CTmodel;
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
		//ToDo: Informationen aus Model holen + in Datei schreiben (siehe: Datei.java)
	}

}