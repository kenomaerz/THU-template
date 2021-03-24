package server;

import static org.junit.Assert.*;

import org.junit.Test;

import model.Gender;
import server.Server;

public class ServerTest {

	@Test
	public void test() {
		Server server = new Server();
		//server.testConnection();
		server.createPatient("Doe", "Jane", Gender.FEMALE);
	}

}
