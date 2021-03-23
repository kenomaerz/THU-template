package de.dkfz.cami.sdsk.server.api;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.rest.server.interceptor.ExceptionHandlingInterceptor;
import ca.uhn.fhir.rest.server.interceptor.ResponseHighlighterInterceptor;
import de.dkfz.cami.sdsk.server.RepositoryMode;
import de.dkfz.cami.sdsk.server.Utils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import java.io.File;

/**
 *
 */
@WebServlet("/*")
public class FhirSimpleRestfulServer extends ca.uhn.fhir.rest.server.RestfulServer {

    /**
     * Set FHIR Context.
     * Register the resource providers and the interceptors.
     *
     */
    @lombok.SneakyThrows
    @Override
    protected void initialize() throws ServletException {
        // Create a context for the appropriate FHIR version
        setFhirContext(FhirContext.forR4());

        String home = System.getProperty("user.home");
        File managerDir = new File(home + File.separatorChar + ".apiserver" + File.separatorChar + "repos" + File.separatorChar);
        Utils.initialize(managerDir, RepositoryMode.MEMORY_TRANSIENT); //todo MEMORY_TRANSIENT

        // Register resource providers
        registerProvider(new PatientResourceProvider());
        registerProvider(new ObservationResourceProvider());

        // Register interceptors
        // Format the responses in nice HTML
        registerInterceptor(new ResponseHighlighterInterceptor());
        registerInterceptor(new ExceptionHandlingInterceptor());
    }
}