package de.dkfz.cami.sdsk.server;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.eclipse.rdf4j.RDF4JException;
import org.eclipse.rdf4j.model.ValueFactory;
import org.eclipse.rdf4j.repository.Repository;
import org.eclipse.rdf4j.sail.config.SailImplConfig;
import org.eclipse.rdf4j.sail.inferencer.fc.config.DedupingInferencerConfig;
import org.eclipse.rdf4j.sail.memory.config.MemoryStoreConfig;
import org.eclipse.rdf4j.sail.nativerdf.config.NativeStoreConfig;
import org.eclipse.rdf4j.sail.spin.config.SpinSailConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.dkfz.cami.sdsk.core.model.SFBURI;
import de.dkfz.cami.sdsk.core.model.bfo.BFOObjectInstance;
import de.dkfz.cami.sdsk.core.model.managedmodel.ManagedInstanceModel;
import de.dkfz.cami.sdsk.core.model.managedmodel.ManagedModelRepository;
import de.dkfz.cami.sdsk.core.model.managedmodel.ManagedOntologyModel;

public class Utils {

	public static ManagedModelRepository modelRepo;

	public static ManagedOntologyModel ontology;
	public static ManagedInstanceModel instances;
	public static Repository federation;

	public static ValueFactory factory;
	public static boolean isInitialized;
	public static BFOObjectInstance currentPatient;
	private final static Logger logger = LoggerFactory.getLogger(Utils.class);


	/**
	 * This method initializes the Repository and sets all required data for the server to operate.
	 *
	 * If SPIN inferencing is activated, the managed models are configured with the same repository as back end.
	 * @throws MalformedURLException
	 * @throws RDF4JException
	 */
	public static void initialize(File repositoryLocation, RepositoryMode mode) throws RDF4JException, MalformedURLException {
		SailImplConfig backendConfig = null;
		switch (mode) {
			case MEMORY_TRANSIENT:
				logger.info("Starting in local transient memory mode...");
				logger.info("All changes will be lost once server is shut down!");
				logger.info("Use for testing only.....");
				backendConfig = new MemoryStoreConfig(false);
				break;

			case MEMORY_PERSISTENT:
				logger.info("Starting in local persistent memory mode...");
				logger.info("All changes will persist.");
				logger.info(
						"Server capabilities are limited by memory. Consider allocating more ram, e.g. by using the -Xmx1024m flag when starting the jar.");
				backendConfig = new MemoryStoreConfig(true, 1000 * 60 * 4); //Sync delay: 4 Minutes
				break;

			case NATIVE:
				logger.info("Starting in local persistent native mode...");
				logger.info("All changes will persist.");
				logger.info(
						"Triples are managed on HDD. This is slower than a memory-only approach but not limited by RAM.");
				backendConfig = new NativeStoreConfig();
				break;

			case REMOTE:
				logger.info("Remote repository capabilities currently deactivated....");
				logger.info("please use native or memory store");
				logger.info("Exiting...");
				System.exit(1);
				break;

			default:
				//logger.error("Unknown repository mode: " + mode);
				//logger.error("Exiting...");
				//System.exit(1);
				logger.info("Starting in local transient memory mode...");
				logger.info("All changes will be lost once server is shut down!");
				logger.info("Use for testing only.....");
				backendConfig = new MemoryStoreConfig(false);
				break;
		}

		backendConfig = createSailImplConfig(backendConfig);

		modelRepo = new ManagedModelRepository(repositoryLocation);

		// Prepare Ontology
		if ( ! modelRepo.hasRepository("ontology")) {
			modelRepo.createRepository("ontology", backendConfig);
			modelRepo.assignModelToRepository("ontology", ManagedOntologyModel.class);
		}

		ontology = modelRepo.getModel("ontology", ManagedOntologyModel.class);
		ontology.initRepo();

		if (modelRepo.getRepository("instances") == null)
			modelRepo.createRepository("instances", backendConfig);
		instances = new ManagedInstanceModel(modelRepo.getRepository("instances"), ontology);

		modelRepo.createFederation();
		federation = modelRepo.getRepository("federation_global");
		Utils.factory = Utils.ontology.getWritingConnection().getValueFactory();
		isInitialized = true;
	}

	/**
	 * Frees all occupied resources and and shuts down the repository
	 */
	public static void deinitialize(){
		ontology.closeConnection();
		ontology = null;
		instances.closeConnection();
		instances = null;
		modelRepo.shutDown();
	}


	/**
	 * Configures a sail according to the given parameters and return an
	 * initialized SAIL repository ready for use.
	 *
	 * @return An SailImplConfig that can be used to create a repository.
	 */
	private static SailImplConfig createSailImplConfig(SailImplConfig backendConfig) {
		return new DedupingInferencerConfig(backendConfig);
	}



	public static File getFile(String filename) throws IOException {
		ClassLoader classLoader = ClassLoader.getSystemClassLoader();
		InputStream in = classLoader.getResourceAsStream("A02_Ontology.ttl");
		File target = File.createTempFile("temp_", filename);
		Files.copy(in, target.toPath(), StandardCopyOption.REPLACE_EXISTING);
		return target;
	}

	/**
	 * Returns a private instanceModel with its own connection.
	 * Make sure to close it after usage!
	 */
	public static ManagedInstanceModel getInstanceModel(){
		return new ManagedInstanceModel(modelRepo.getRepository("instances"), ontology);
	}
}
