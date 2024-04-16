package com.patientportal.repository;

import com.patientportal.model.Prescription;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class PrescriptionRepository implements PanacheRepository<Prescription> {
}
