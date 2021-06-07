import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class ReadCSV {

	public static void main(String[] args) {
		String[] colNames = new String[5];
		String[] record_id = new String[100];
		String[] gender = new String[100];
		int[] hepatitis_b = new int[100];
		int[] bilirubin_concentration = new int[100];
		int[] form_1_complete = new int[100];
		int index = 0;
		

		try {
			Scanner scanner = new Scanner(new File("./src/CSVData/DATA_2021-03-31_2145.csv"));
			Scanner scanner2 = new Scanner(new File("./src/CSVData/DataDictionary_2021-03-31.csv"));
			Scanner scanner3 = new Scanner(new File("./src/CSVData/ihCCOntology_Excerpt.csv"));

			scanner.useDelimiter(",");
			scanner2.useDelimiter(",");
			scanner3.useDelimiter(",");
			System.out.print("******************************************************\n");
			for(int i=0; i<5; i++)
			{
			
				colNames[i] = scanner.next();
				//System.out.print(i);
			}
			//colNames[4] = scanner.next();
			 //Integer.parseInt("5");
			while (scanner.hasNextLine()) {
				//System.out.print(scanner.next() + ",");
				record_id[index] = scanner.next();
				gender[index] = scanner.next();
				hepatitis_b[index] = Integer.parseInt(scanner.next());
				bilirubin_concentration[index] = Integer.parseInt(scanner.next());
				form_1_complete[index] = Integer.parseInt(scanner.next());
				index++;
				
				//System.out.print(index);
			}
			
			System.out.printf(colNames[1]+" is "+gender[0]+"\n");
			scanner.close();
			System.out.print("******************************************************\n");
			while (scanner2.hasNext()) {
				System.out.print(scanner2.next() + ",");
			}
			scanner2.close();
			System.out.print("******************************************************\n");
			while (scanner3.hasNext()) {
				System.out.print(scanner3.next() + ",");
			}
			scanner3.close();

		} catch (FileNotFoundException e) {
			System.out.println(e);
		}

		// String line = br.readLine();

		// Scanner scanner = new Scanner(new
		// File("/Data-Value-Output/src/main/java/DataDictionary_2021-03-31.csv"));

	}

}
