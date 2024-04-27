package com.patientportal.repository;

import com.patientportal.model.Prescription;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.UUID;

@ApplicationScoped
public class PrescriptionRepository implements PanacheRepositoryBase<Prescription, UUID> {
}
