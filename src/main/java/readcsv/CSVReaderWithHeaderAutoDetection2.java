package readcsv;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class CSVReaderWithHeaderAutoDetection2 {
    private static final String SAMPLE_CSV_FILE_PATH = "C:\\Users\\vybui\\eclipse-workspace\\readcsv\\src\\readcsv\\ihCCOntology_Excerpt (1).csv";
public static void main(String[] args) throws IOException {
		
		LinkedHashMap<String, String> map = new LinkedHashMap<>();
		
		try (

				Reader reader = Files.newBufferedReader(Paths.get(SAMPLE_CSV_FILE_PATH));
				CSVParser csvParser = new CSVParser(reader,
						CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());) {
			
			for (CSVRecord csvRecord : csvParser) {
				// Accessing values by Header names
				String parametername = csvRecord.get("Parametername");
				String IRI = csvRecord.get("IRI");
				String measurementType = csvRecord.get("Measurement-type");

				// Create an empty hash map
		       
		        map.put(parametername, " ");
		        
//				System.out.println("Record No - " + csvRecord.getRecordNumber());
//				System.out.println("---------------");
//				System.out.println("Parametername : " + parametername);
//				System.out.println("IRI : " + IRI);
//				System.out.println("Measurement-type : " + measurementType);
//				System.out.println("---------------\n\n");
			}
		
			// Print size and content
	        System.out.println("Size of map is: "+ map.size());
	        System.out.println(map);
		}
	}

//  https://www.callicoder.com/java-read-write-csv-file-apache-commons-csv/
//	http://commons.apache.org/proper/commons-csv/download_csv.cgi

}