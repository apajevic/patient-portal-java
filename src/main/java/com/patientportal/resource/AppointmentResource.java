package com.patientportal.resource;

import com.patientportal.dto.AppointmentDTO;
import com.patientportal.dto.UpdateAppointmentDTO;
import com.patientportal.exception.BusinessException;
import com.patientportal.exception.TechnicalException;
import com.patientportal.mapper.BusinessExceptionMapper;
import com.patientportal.mapper.TechnicalExceptionMapper;
import com.patientportal.model.Appointment;
import com.patientportal.response.AppointmentResponse;
import com.patientportal.service.AppointmentService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Path("api/v1/appointments")
public class AppointmentResource {
    @Inject
    AppointmentService appointmentService;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response create(AppointmentDTO request) {
        try {
            Appointment appointment = appointmentService.create(request);

            Map<String, String> response = new HashMap<>();
            response.put("status:", "Appointment created successfully");
            response.put("id:", appointment.getId().toString());

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
            List<AppointmentResponse> appointmentResponses = appointmentService.getAll().stream()
                    .map(AppointmentResponse::new)
                    .collect(Collectors.toList());

            return Response.ok(appointmentResponses).build();
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
            return Response.ok(new AppointmentResponse(appointmentService.getById(id))).build();
        } catch (BusinessException e) {
            return new BusinessExceptionMapper().toResponse(e);
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    @PATCH
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public Response update(@PathParam("id") UUID id, UpdateAppointmentDTO request) {
        try {
            Appointment appointment = appointmentService.update(id, request);

            Map<String, String> response = new HashMap<>();
            response.put("status:", "Appointment updated successfully");
            response.put("id:", appointment.getId().toString());

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
            appointmentService.delete(id);
            return Response.ok().build();
        } catch (BusinessException e) {
            return new BusinessExceptionMapper().toResponse(e);
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }
}
