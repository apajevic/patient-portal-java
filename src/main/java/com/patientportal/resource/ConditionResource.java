package com.patientportal.resource;

import com.patientportal.dto.UpdateConditionDTO;
import com.patientportal.exception.BusinessException;
import com.patientportal.exception.TechnicalException;
import com.patientportal.mapper.BusinessExceptionMapper;
import com.patientportal.mapper.TechnicalExceptionMapper;
import com.patientportal.model.Condition;
import com.patientportal.service.ConditionService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Path("api/v1/conditions")
public class ConditionResource {
    @Inject
    ConditionService conditionService;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response create(Condition condition) {
        try {
            Condition createdCondition = conditionService.create(condition);
            Map<String, String> response = new HashMap<>();
            response.put("status:", "Condition created successfully");
            response.put("id:", createdCondition.getId().toString());
            response.put("name:", createdCondition.getName());

            return Response.status(Response.Status.CREATED).entity(response).build();
        } catch (BusinessException e) {
            return new BusinessExceptionMapper().toResponse(e);
        } catch (TechnicalException e) {
            return new TechnicalExceptionMapper().toResponse(e);
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll() {
        try {
            return Response.ok(conditionService.getAll()).build();
        } catch (BusinessException e) {
            return new BusinessExceptionMapper().toResponse(e);
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public Response getOne(@PathParam("id") UUID id) {
        try {
            return Response.ok(conditionService.getById(id)).build();
        } catch (BusinessException e) {
            return new BusinessExceptionMapper().toResponse(e);
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public Response update(@PathParam("id") UUID id, UpdateConditionDTO updatedCondition) {
        try {
            Condition condition = conditionService.update(id, updatedCondition);

            Map<String, String> response = new HashMap<>();
            response.put("status:", "Condition updated successfully");
            response.put("id:", condition.getId().toString());
            response.put("name:", condition.getName());
            return Response.ok(response).build();
        } catch (BusinessException e) {
            return new BusinessExceptionMapper().toResponse(e);
        } catch (TechnicalException e) {
            return new TechnicalExceptionMapper().toResponse(e);
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public Response delete(@PathParam("id") UUID id) {
        try {
            conditionService.delete(id);
            return Response.ok().build();
        } catch (BusinessException e) {
            return new BusinessExceptionMapper().toResponse(e);
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }
}
