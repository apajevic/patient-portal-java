package com.patientportal.service;

import com.patientportal.dto.AppointmentDTO;
import com.patientportal.model.Appointment;
import com.patientportal.model.User;
import com.patientportal.repository.AppointmentRepository;
import com.patientportal.repository.UserRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

import java.util.List;

@ApplicationScoped
public class AppointmentService {
    @Inject
    AppointmentRepository appointmentRepository;

    @Inject
    UserRepository userRepository;

    @Transactional
    public Appointment create(AppointmentDTO request){
        User patient = userRepository.findByIdOptional(request.patientId())
                .orElseThrow(() -> new NotFoundException("Patient with id " + request.patientId() + " not found"));

        User doctor = userRepository.findByIdOptional(request.doctorId())
                .orElseThrow(() -> new NotFoundException("Doctor with id " + request.doctorId() + " not found"));

        Appointment appointment = new Appointment(patient, doctor, request.date(), request.startTime(), request.endTime(), request.location(), request.description());
        appointmentRepository.persist(appointment);
        return appointment;
    }

    public Appointment getById(Long id) {
        return appointmentRepository.findByIdOptional(id)
                .orElseThrow(() -> new NotFoundException("Appointment with id " + id + " not found"));
    }

    public List<Appointment> getAll() {
        List<Appointment> appointments = appointmentRepository.listAll();
        if(appointments.isEmpty()) {
            throw new NotFoundException("No appointments found");
        }
        return appointmentRepository.listAll();
    }

    @Transactional
    public void delete(Long id) {
        appointmentRepository.findByIdOptional(id)
                .ifPresentOrElse(appointmentRepository::delete, () -> {
                    throw new NotFoundException("User with id " + id + " not found");
                });
    }
}
