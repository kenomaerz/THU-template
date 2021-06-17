package Prototyp;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Datei {

	public static void main (String[] args) {
		
		FileWriter writer;
		File datei = new File ("neuedatei.txt");
		
		try {
			writer = new FileWriter(datei, true);
			writer.write("in Datei geschrieben");
			writer.write(System.getProperty("line.separator"));
			
			writer.flush();
			writer.close();
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		
		
		
	}
	
	
	
}
