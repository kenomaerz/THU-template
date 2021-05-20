package server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class MeinServer {

	public static void main(String[] args) {
		
		MeinServer server = new MeinServer(8000);
		server.startListening();

	}
	
	
	private int port;

	public MeinServer (int port) {
		
		this.port=port;
		
		
	}
	
	public void startListening () {
		
		System.out.println("[Server] Sever starten...");
		
		new Thread(new Runnable() {
			
			@Override
			public void run(){
				
				while(true) {
				
				try {
				
				ServerSocket serverSocket = new ServerSocket(port);
				
				System.out.println("[Server] Warten auf Verbindung...");
				Socket remoteClientSocket = serverSocket.accept(); 
				
				System.out.println("[Server] Client verbunden: " + remoteClientSocket.getRemoteSocketAddress());
				
				//Daten aus der Socket zu lesen
				//BufferedReader kann Daten nachlade, wenn welche zur Verfügung stehen
				Scanner s = new Scanner(new BufferedReader(new InputStreamReader(remoteClientSocket.getInputStream())));
				if (s.hasNextLine()) {
					System.out.println("[Server] Nachricht vom Client: " + s.nextLine());
				}
				
				PrintWriter pw = new PrintWriter(new OutputStreamWriter(remoteClientSocket.getOutputStream()));
				pw.println("Mir geht es gut, danke der Nachfrage!");
				pw.flush();
				
				//Verbindung schließen
				s.close();
				remoteClientSocket.close();
				serverSocket.close();
				
				
				}catch(Exception e) {
					e.printStackTrace();
				}
				
				}
			}
			}).start();
	}
}
