package de.dkfz.cami.sdsk.server;

import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.MultivaluedMap;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Keno März
 * @date 26.09.2017
 * 
 * This version of a CORS filter allows both localhost and 127.0.0.1 domains as valid origins.
 * 
 */


public class CORSFilter implements ContainerResponseFilter {
	
	private List<String> allowOrigins = new ArrayList<String>();
	
    @Override
    public void filter(ContainerRequestContext containerRequestContext, ContainerResponseContext containerResponseContext) throws IOException {
        MultivaluedMap<String, Object> headers = containerResponseContext.getHeaders();

        String originHeader = containerRequestContext.getHeaderString("Origin");
        if(allowOrigins.contains(originHeader)) {
            headers.add("Access-Control-Allow-Origin", originHeader);

            headers.add("Access-Control-Allow-Headers",
                    "origin, content-type, accept, authorization");
            headers.add("Access-Control-Allow-Credentials", "true");
            headers.add("Access-Control-Allow-Methods",
                    "GET, POST, PUT, DELETE, OPTIONS, HEAD");
            headers.add("Cache-Control", "no-store");
        }
    }
}
