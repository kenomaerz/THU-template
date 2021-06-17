package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import kong.unirest.json.JSONObject;

public class HttpClient {
	
	public static void main (String[] args) {
		
		StringBuffer response = new StringBuffer();
		
		try {
			 
			 
			
			URL url = new URL("https://snowstorm.test-nictiz.nl/MAIN/concepts?activeFilter=true");
			 //URL url = new URL("https://jsonplaceholder.typicode.com/albums"); 
			 HttpURLConnection con = (HttpURLConnection) url.openConnection();
			 
			 con.setRequestMethod("GET");
			 con.setConnectTimeout(5000);
			 con.setReadTimeout(5000);
			 
			 int status = con.getResponseCode();
			 System.out.println(status);
			 
			 if(status > 299) {
				 
				 BufferedReader in = new BufferedReader ( new InputStreamReader(con.getInputStream()));
				 String inputLine;
				 response = new StringBuffer();
				 while ((inputLine = in.readLine()) != null) {
						  
						  response.append(inputLine); 
				} 
				 in.close();
			 } else {
				 BufferedReader in = new BufferedReader ( new InputStreamReader(con.getInputStream()));
				 String inputLine;
				 response = new StringBuffer();
				 while ((inputLine = in.readLine()) != null) {
					  
					  response.append(inputLine); 
			} 
			 in.close();
			 }
			 
			 
			 System.out.println(response.toString());
			 
			 
			 
			 
		} catch (Exception e) {
			 
			 System.out.println(e); }
		
			/*
			 * } catch (MailformedURLException e) { e.printStacTrace(); } catch (IOException
			 * e) { e.printStackTrace(); }
			 */
		
		/*
		 * try {
		 * 
		 * String url = "http://api.fixer.io/latest?base=USD";
		 * 
		 * URL obj = new URL(url); HttpURLConnection con = (HttpURLConnection)
		 * obj.openConnection();
		 * 
		 * int responseCode = con.getResponseCode();
		 * System.out.println("\nSending 'GET' request to URL : " + url);
		 * System.out.println("Response Code : " + responseCode); 
		 * BufferedReader in =
		 * new BufferedReader ( new InputStreamReader(con.getInputStream())); 
		 * String inputLine; 
		 * StringBuffer response = new StringBuffer(); 
		 * while ((inputLine =
		 * in.readLine()) != null) {
		 * 
		 * response.append(inputLine); } in.close();
		 * 
		 * 
		 * JSONObject myresponse = new JSONObject (response.toString());
		 * 
		 * System.out.println(myresponse);
		 * 
		 * System.out.println("base " + myresponse.getString("base"));
		 * System.out.println("date " + myresponse.getString("date"));
		 * System.out.println("rates " + myresponse.getString("rates"));
		 * 
		 * JSONObject rates_object = new JSONObject
		 * (myresponse.getJSONObject("rates").toString());
		 * 
		 * System.out.println("rates " + rates_object);
		 * 
		 * System.out.println("AUD " + rates_object.getDouble("AUD"));
		 * System.out.println("BGN " + rates_object.getDouble("BGN"));
		 * System.out.println("BRL " + rates_object.getDouble("BRL"));
		 * System.out.println("CAD " + rates_object.getDouble("CAD"));
		 * 
		 * 
		 * } catch (Exception e) {
		 * 
		 * System.out.println(e); }
		 */
		
		
	}
	
	

}
