package com.patientportal.resource;

import com.patientportal.dto.ProfilePictureForm;
import com.patientportal.dto.RegisterDTO;
import com.patientportal.dto.UpdateUserDTO;
import com.patientportal.exception.BusinessException;
import com.patientportal.exception.TechnicalException;
import com.patientportal.mapper.BusinessExceptionMapper;
import com.patientportal.mapper.TechnicalExceptionMapper;
import com.patientportal.model.User;
import com.patientportal.response.UserResponse;
import com.patientportal.service.UserService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.*;
import java.util.stream.Collectors;

@Path("api/v1/users")
public class UserResource {
    @Inject
    UserService userService;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response create(RegisterDTO request) {
        try {
            User user = userService.create(request);
            Map<String, String> response = new HashMap<>();
            response.put("status:", "User created successfully");
            response.put("id:", user.getId().toString());
            response.put("email:", user.getEmail());

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
            List<UserResponse> userResponses = userService.getAll().stream()
                    .map(UserResponse::new)
                    .collect(Collectors.toList());

            return Response.ok(userResponses).build();
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
            return Response.ok(new UserResponse(userService.getById(id))).build();
        } catch (BusinessException e) {
            return new BusinessExceptionMapper().toResponse(e);
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    @PATCH
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public Response update(@PathParam("id") UUID id, UpdateUserDTO request) {
        try {
            User user = userService.update(id, request);

            Map<String, String> response = new HashMap<>();
            response.put("status:", "User updated successfully");
            response.put("id:", user.getId().toString());
            response.put("email:", user.getEmail());
            return Response.ok(response).build();
        } catch (BusinessException e) {
            return new BusinessExceptionMapper().toResponse(e);
        } catch (TechnicalException e) {
            return new TechnicalExceptionMapper().toResponse(e);
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    @POST
    @Path("/{id}/profile-picture")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public Response uploadProfilePicture(@PathParam("id") UUID id, ProfilePictureForm form) {
        try {
            if (form.getFile() == null) {
                return Response.status(Response.Status.BAD_REQUEST).entity("No file uploaded").build();
            }

            User user = userService.saveProfilePicture(id, form);
            return Response.ok().entity(new UserResponse(user)).build();
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
        userService.delete(id);
        return Response.ok().build();
    }
}
