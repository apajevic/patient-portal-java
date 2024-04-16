package com.patientportal.service;

import com.patientportal.dto.AppointmentDTO;
import com.patientportal.exception.BusinessException;
import com.patientportal.exception.TechnicalException;
import com.patientportal.model.Appointment;
import com.patientportal.model.User;
import com.patientportal.repository.AppointmentRepository;
import com.patientportal.repository.UserRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.Response;
import com.patientportal.dto.UpdateAppointmentDTO;

import java.util.List;

@ApplicationScoped
public class AppointmentService {
    @Inject
    AppointmentRepository appointmentRepository;

    @Inject
    UserRepository userRepository;

    @Transactional
    public Appointment create(AppointmentDTO request) throws BusinessException, TechnicalException {
        User patient = userRepository.findByIdOptional(request.patientId())
                .orElseThrow(() -> new BusinessException(Response.Status.BAD_REQUEST.getStatusCode(),
                        "Patient with id " + request.patientId() + " not found"));

        User doctor = userRepository.findByIdOptional(request.doctorId())
                .orElseThrow(() -> new BusinessException(Response.Status.BAD_REQUEST.getStatusCode(),
                        "Doctor with id " + request.doctorId() + " not found"));

        Appointment appointment = new Appointment(patient, doctor, request.date(), request.startTime(), request.endTime(), request.location(), request.description());
        appointmentRepository.persist(appointment);
        if(appointmentRepository.isPersistent(appointment)){
            return appointment;
        }
        throw new TechnicalException(
                Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(),
                "Appointment could not be created"
        );
    }

    public Appointment getById(Long id) throws BusinessException {
        return appointmentRepository.findByIdOptional(id)
                .orElseThrow(() -> new BusinessException(
                        Response.Status.NOT_FOUND.getStatusCode(),
                        "Appointment with id " + id + " not found"
                ));
    }

    public List<Appointment> getAll() throws BusinessException {
        List<Appointment> appointments = appointmentRepository.listAll();

        if (appointments == null || appointments.isEmpty()) {
            throw new BusinessException(
                    Response.Status.NOT_FOUND.getStatusCode(),
                    "No appointments found"
            );
        }
        return appointments;
    }


    @Transactional
    public Appointment update(Long id, UpdateAppointmentDTO request) throws BusinessException, TechnicalException {
        Appointment appointment = appointmentRepository.findByIdOptional(id)
                .orElseThrow(() -> new BusinessException(
                        Response.Status.NOT_FOUND.getStatusCode(),
                        "Appointment with id " + id + " not found"
                ));

        if(request.date() != null){
            appointment.setDate(request.date());
        }

        if(request.startTime() != null){
            appointment.setStartTime(request.startTime());
        }

        if(request.endTime() != null){
            appointment.setEndTime(request.endTime());
        }

        if(request.location() != null){
            appointment.setLocation(request.location());
        }

        if(request.status() != null){
            appointment.setStatus(request.status());
        }

        if(request.conclusion() != null){
            appointment.setConclusion(request.conclusion());
        }

        if(request.description() != null){
            appointment.setDescription(request.description());
        }

        appointmentRepository.persist(appointment);
        if(appointmentRepository.isPersistent(appointment)){
            return appointment;
        }
        throw new TechnicalException(
                Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(),
                "Appointment could not be updated"
        );
    }

    @Transactional
    public void delete(Long id) throws BusinessException {
        Appointment appointment = appointmentRepository.findByIdOptional(id)
                .orElseThrow(() -> new BusinessException(
                        Response.Status.NOT_FOUND.getStatusCode(),
                        "Appointment with id " + id + " not found"
                ));
        appointmentRepository.delete(appointment);
    }
}
