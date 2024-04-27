package com.patientportal.resource;

import com.patientportal.dto.UpdatePrescriptionDTO;
import com.patientportal.exception.BusinessException;
import com.patientportal.exception.TechnicalException;
import com.patientportal.mapper.BusinessExceptionMapper;
import com.patientportal.mapper.TechnicalExceptionMapper;
import com.patientportal.model.Prescription;
import com.patientportal.service.PrescriptionService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Path("api/v1/prescriptions")
public class PrescriptionResource {
    @Inject
    PrescriptionService prescriptionService;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response create(Prescription prescription) {
        try {
            Prescription createdPrescription = prescriptionService.create(prescription);
            Map<String, String> response = new HashMap<>();
            response.put("status:", "Prescription created successfully");
            response.put("id:", createdPrescription.getId().toString());

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
            return Response.ok(prescriptionService.getAll()).build();
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
            return Response.ok(prescriptionService.getById(id)).build();
        } catch (BusinessException e) {
            return new BusinessExceptionMapper().toResponse(e);
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public Response update(@PathParam("id") UUID id, UpdatePrescriptionDTO updatedPrescription) {
        try {
            Prescription prescription = prescriptionService.update(id, updatedPrescription);

            Map<String, String> response = new HashMap<>();
            response.put("status:", "Prescription updated successfully");
            response.put("id:", prescription.getId().toString());
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
            prescriptionService.delete(id);
            return Response.ok().build();
        } catch (BusinessException e) {
            return new BusinessExceptionMapper().toResponse(e);
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }
}
