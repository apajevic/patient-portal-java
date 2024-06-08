package com.patientportal.dto;

import org.jboss.resteasy.reactive.PartType;
import org.jboss.resteasy.reactive.RestForm;
import org.jboss.resteasy.reactive.multipart.FileUpload;
import jakarta.ws.rs.core.MediaType;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ProfilePictureForm {
    @RestForm("file")
    @PartType(MediaType.APPLICATION_OCTET_STREAM)
    private FileUpload file;

    @RestForm("name")
    @PartType(MediaType.TEXT_PLAIN)
    private String name;
}
