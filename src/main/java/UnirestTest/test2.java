

	package UnirestTest;

	import java.util.Arrays;
	import org.json.JSONObject;

	import com.mashape.unirest.http.Unirest;
	import com.mashape.unirest.http.exceptions.UnirestException;

	import org.json.*;
	
	public class test2 {

		public static void main(String[] args) throws UnirestException {

//			String body = Unirest.get("https://snowstorm.test-nictiz.nl/MAIN/concepts?activeFilter=true")
//					.queryString("term", Arrays.asList("age"))
//					.queryString("termActive", "true")
					

					String body = Unirest.get("https://snowstorm.test-nictiz.nl/browser/MAIN/descriptions?")
							.queryString("term", "age")
							.queryString("semanticTag", "finding")
		
					
					//900000000000003001 (FSN)
//					.queryString("descriptionType", "900000000000003001")
					
//					.queryString("active", "true")
//					.queryString("conceptActive", "true")
//					.queryString("lang", "english")
//					.queryString("groupByConcept", "true")
//					.queryString("preferredIn", "900000000000509007")
//					.queryString("preferredIn", "900000000000508004")
//					.queryString("type", "900000000000013009")
					
					.asString().getBody();
			
			// Swagger Filter: https://snowstorm.test-nictiz.nl/MAIN/concepts?activeFilter=true&term=age&termActive=true&offset=0&limit=10
			// original Filter: https://browser.ihtsdotools.org/snowstorm/snomed-ct/browser/MAIN/2021-01-31/descriptions?&limit=100&term=age&active=true&conceptActive=true&lang=english&searchMode=WHOLE_WORD&groupByConcept=true&preferredIn=900000000000509007&preferredIn=900000000000508004&type=900000000000013009
		
			//System.out.println(body);
			
			String jsonString = body; 
			JSONObject obj = new JSONObject(jsonString);
			JSONArray arr = obj.getJSONArray("items");
			
//			for (int i = 0; i < arr.length(); i++) {
//				
//				String concept_id = arr.getJSONObject(i).getString("conceptId");
//				JSONObject pt = arr.getJSONObject(i).getJSONObject("pt");
//				JSONObject fsn = arr.getJSONObject(i).getJSONObject("fsn");
//				
//				System.out.println(i+1+". " + "\tConcept ID: " + concept_id + "\n\tPrefered name: " + pt.get("term") + "\n\tFully specified name: " + fsn.get("term") +"\n ");
//				
//			}
			for (int i = 0; i < arr.length(); i++) {
	            String termCT = arr.getJSONObject(i).getString("term");
	           // JSONObject concept_id = arr.getJSONObject(i).getJSONObject("conceptId");
//	            JSONObject pt = arr.getJSONObject(i).getJSONObject("pt");
//	            JSONObject fsn = arr.getJSONObject(i).getJSONObject("fsn");
	            System.out.println(i + 1 + ". " + "\tTerm: " + termCT  + "\n ");
	        
			}
		}
}
