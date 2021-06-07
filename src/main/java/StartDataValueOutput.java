import java.util.List;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import controller.Controller;

public class StartDataValueOutput {

	public static void main(String[] args) {
		Controller controller = new Controller();
		
 		controller.start();
 		
 		String fileName= "./src/CSVData/DATA_2021-03-31_2145.csv";
 		File file= new File(fileName);
 		
 		 List<List<String>> lines = new ArrayList<>();
         Scanner inputStream;
         
         try{
             inputStream = new Scanner(file);

             while(inputStream.hasNext()){
                 String line= inputStream.next();
                 String[] values = line.split(",");
                 
                 lines.add(Arrays.asList(values));
             }

             inputStream.close();
         }catch (FileNotFoundException e) {
             e.printStackTrace();
         }
         
         int lineNo = 1;
         for(List<String> line: lines) {
             int columnNo = 1;
             for (String value: line) {
                 System.out.println("Line " + lineNo + " Column " + columnNo + ": " + value);
                 columnNo++;
             }
             lineNo++;	

	}

//		Model model;
//		try {
//			model = new Model();
//			Controller controller = new Controller(model);
//			controller.start();
//		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
////			e.printStackTrace();
//		}
//
//	}

	}
}
	
//Atelier graphique
//dummy Customer, all values are x and the customerNumber is 497