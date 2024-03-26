package com.patientportal.resource;

import com.patientportal.dto.UserDTO;
import com.patientportal.response.UserResponse;
import com.patientportal.service.UserService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.stream.Collectors;

@Path("api/v1/users")
public class UserResource {
    @Inject
    UserService userService;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response create(UserDTO user) {
        userService.create(user);

        return Response.ok().build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll() {
        List<UserResponse> userResponses = userService.getAll().stream()
                .map(UserResponse::new)
                .collect(Collectors.toList());
        return Response.ok(userResponses).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public Response getOne(@PathParam("id") Long id) {
        return Response.ok(new UserResponse(userService.getById(id))).build();
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public Response update(@PathParam("id") Long id, UserDTO user) {
//        userService.update(id, user);
        return Response.ok().build();
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public Response delete(@PathParam("id") Long id) {
        userService.delete(id);
        return Response.ok().build();
    }
}
