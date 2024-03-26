package com.patientportal.resource;

import com.patientportal.dto.AppointmentDTO;
import com.patientportal.model.Appointment;
import com.patientportal.service.AppointmentService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.HashMap;
import java.util.Map;

@Path("api/v1/appointments")
public class AppointmentResource {
    @Inject
    AppointmentService appointmentService;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response create(AppointmentDTO request) {
        Appointment appointment = appointmentService.create(request);

        Map<String, String> response = new HashMap<>();
        response.put("status:", "Appointment created successfully");
        response.put("id:", appointment.getId().toString());

        return Response.status(Response.Status.CREATED).entity(response).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll() {
        return Response.ok(appointmentService.getAll()).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public Response getOne(@PathParam("id") Long id) {
        return Response.ok(appointmentService.getById(id)).build();
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public Response update(@PathParam("id") Long id, AppointmentDTO appointment) {
//        appointmentService.update(id, appointment);
        return Response.ok().build();
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public Response delete(@PathParam("id") Long id) {
        appointmentService.delete(id);
        return Response.ok().build();
    }
}
