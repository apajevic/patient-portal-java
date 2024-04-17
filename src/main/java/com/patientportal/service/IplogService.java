package com.patientportal.service;

import com.patientportal.exception.BusinessException;
import com.patientportal.model.IPLog;
import com.patientportal.resource.client.IplogClient;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RestClient;


@ApplicationScoped
public class IplogService {

    @Inject
    @RestClient
    IplogClient iplogClient;


    public IPLog getIp() throws BusinessException {
        IPLog ipLog = iplogClient.getIp();

        if (ipLog == null) {
            throw new BusinessException(
                    Response.Status.NOT_FOUND.getStatusCode(),
                    "No ip log found"
            );
        }
        return ipLog;
    }
}
