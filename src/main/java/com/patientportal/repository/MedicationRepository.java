package com.patientportal.repository;

import com.patientportal.model.Medication;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class MedicationRepository implements PanacheRepository<Medication> {
}
