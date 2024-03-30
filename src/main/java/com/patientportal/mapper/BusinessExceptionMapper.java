package com.patientportal.mapper;

import com.patientportal.dto.ErrorMessageDTO;
import com.patientportal.exception.BusinessException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class BusinessExceptionMapper implements ExceptionMapper<BusinessException> {
    @Override
    public Response toResponse(BusinessException exception) {
        ErrorMessageDTO errorMessage = new ErrorMessageDTO(exception.getStatus(), exception.getMessage());

        return Response.
                status(errorMessage.status()).
                entity(errorMessage)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}
