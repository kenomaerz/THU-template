package Prototyp;

import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Scanner;

public class Prototyp {
	
	public static void main(String[] args) {
		//Ordnet string zu mapping objekt zu
		//z. B. "age" => mapping objekt für age (mapping objekt beinhaltet destination terms)
		HashMap<String, Mapping> mappings = new HashMap<String, Mapping>();
		mappings.put("age", new Mapping("age", "ageicd10", "age213")); //erlaubt Zugriff auf das Mapping Objekt über den Key "age" 
																		// über zb. age kommt man an das mapping objekt ran mit den entsprechenden begriffen
		mappings.put("agene", new Mapping("agene", "whatever"));
		//System.out.println(args[0]);
		//System.out.println(args[1]);
		
		Scanner scanner = new Scanner(new InputStreamReader(System.in));
		System.out.print("Begriff eingeben: ");
		String input = scanner.nextLine();
		
		/* prüft ob genau das eingegebene wort in den mappings drinsteht
		if (mappings.containsKey(input)) {
			Mapping mapping = mappings.get(input);

		}
		*/
		
		//Bsp: contains
		//"agene".contains("age") => true
		//"age".contains("age") => true
		//"sdgsdg".contains("age") => false
		mappings.keySet().stream().filter(key -> key.contains(input)).forEach(key -> printMapping(mappings.get(key))); //key.contains schaut ob begriff irgendwo im mapping drinne ist. get.key gibts dann aus
		
	}
	
	private static void printMapping(Mapping mapping) {
		System.out.println(mapping.getTerm());
		for(String term : mapping.getDestinationTerms()) {
			System.out.println(term);
		}
		System.out.println();
	}

}



