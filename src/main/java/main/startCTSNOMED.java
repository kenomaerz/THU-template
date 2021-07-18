package main;

import java.awt.EventQueue;
import controller.CTController;
import modelCTSNOMED.CTModel;
import view.CTView;

public class startCTSNOMED {
	
	public static void main(String[] args) {

		EventQueue.invokeLater(new Runnable() {
			
			public void run() {
				
				try {

					System.out.println("SNOMED-CT Matching");
					System.out.println("------------------\n");

					CTModel model = new CTModel();
					CTController controller = new CTController(model, null);
					CTView view = new CTView(controller);

				} catch (Exception e) {
					e.printStackTrace();
				} 
			}
		});
	}
}