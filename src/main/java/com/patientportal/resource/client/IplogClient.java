package com.patientportal.resource.client;

import com.patientportal.model.IPLog;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;


@Path("/data")
@RegisterRestClient
public interface IplogClient {
    @GET
    @Path("/client-ip")
    IPLog getIp();
}
