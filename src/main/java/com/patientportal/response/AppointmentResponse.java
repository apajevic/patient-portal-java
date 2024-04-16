package com.patientportal.response;

import com.patientportal.model.Appointment;
import com.patientportal.model.Prescription;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;

@NoArgsConstructor
@Getter
@Setter
public class AppointmentResponse {
    private Long id;
    private UserResponse patient;
    private UserResponse doctor;
    private Prescription prescription;
    private Date date;
    private String startTime;
    private String endTime;
    private String location;
    private String description;
    private String conclusion;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public AppointmentResponse(Appointment appointment) {
        this.id = appointment.getId();
        this.patient = new UserResponse(appointment.getPatient());
        this.doctor = new UserResponse(appointment.getDoctor());
        this.prescription = appointment.getPrescription();
        this.date = appointment.getDate();
        this.startTime = appointment.getStartTime();
        this.endTime = appointment.getEndTime();
        this.location = appointment.getLocation();
        this.description = appointment.getDescription();
        this.conclusion = appointment.getConclusion();
        this.status = appointment.getStatus();
        this.createdAt = appointment.getCreatedAt();
        this.updatedAt = appointment.getUpdatedAt();
    }
}

