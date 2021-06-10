package Prototyp;

import java.util.Arrays;
import java.util.List;

public class Mapping {

	private String term;
	private List<String> destinationTerms;
	
	public Mapping(String term, String... destinationTerms) {
		this.term = term;
		this.destinationTerms = Arrays.asList(destinationTerms);
	}
	
	public String getTerm() {
		return term;
	}
	
	public List<String> getDestinationTerms() {
		return destinationTerms;
	}
}


