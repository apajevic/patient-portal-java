package com.patientportal.resource;

import com.patientportal.exception.BusinessException;
import com.patientportal.mapper.BusinessExceptionMapper;
import com.patientportal.service.IplogService;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("api/v1/iplog")
public class IplogResource {

    @Inject IplogService iplogService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getIp() {
        try {
            return Response.ok(iplogService.getIp()).build();
        } catch (BusinessException e) {
            return new BusinessExceptionMapper().toResponse(e);
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }
}
