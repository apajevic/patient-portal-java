package com.patientportal.repository;

import com.patientportal.model.Medication;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.UUID;

@ApplicationScoped
public class MedicationRepository implements PanacheRepositoryBase<Medication, UUID> {
}
