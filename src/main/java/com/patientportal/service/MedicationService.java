package com.patientportal.service;

import com.patientportal.exception.BusinessException;
import com.patientportal.exception.TechnicalException;
import com.patientportal.model.Medication;
import com.patientportal.repository.MedicationRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.Response;

import java.util.List;

@ApplicationScoped
public class MedicationService {
    @Inject
    MedicationRepository medicationRepository;

    @Transactional
    public Medication create(Medication medication) throws BusinessException, TechnicalException {
        Medication existingMedication = medicationRepository.find("name", medication.getName()).firstResult();
        if (existingMedication != null) {
            throw new BusinessException(
                    Response.Status.BAD_REQUEST.getStatusCode(),
                    "A medication with this name already exists"
            );
        }

        medicationRepository.persist(medication);
        if(medicationRepository.isPersistent(medication)){
            return medication;
        }
        throw new TechnicalException(
                Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(),
                "Medication could not be created"
        );
    }

    public Medication getById(Long id) throws BusinessException {
        return medicationRepository.findByIdOptional(id)
                .orElseThrow(() -> new BusinessException(
                        Response.Status.NOT_FOUND.getStatusCode(),
                        "Medication with id " + id + " not found"
                ));
    }

    public List<Medication> getAll() throws BusinessException {
        List<Medication> medications = medicationRepository.listAll();

        if (medications == null || medications.isEmpty()) {
            throw new BusinessException(
                    Response.Status.NOT_FOUND.getStatusCode(),
                    "No medications found"
            );
        }
        return medications;
    }

    @Transactional
    public Medication update(Long id, Medication updatedMedication) throws BusinessException, TechnicalException {
        Medication medication = medicationRepository.findByIdOptional(id)
                .orElseThrow(() -> new BusinessException(
                        Response.Status.NOT_FOUND.getStatusCode(),
                        "Medication with id " + id + " not found"
                ));

        medication.setName(updatedMedication.getName());
        medication.setDosage(updatedMedication.getDosage());
        medication.setFrequency(updatedMedication.getFrequency());
        medication.setDuration(updatedMedication.getDuration());
        medication.setNotes(updatedMedication.getNotes());

        medicationRepository.persist(medication);
        if(medicationRepository.isPersistent(medication)){
            return medication;
        }
        throw new TechnicalException(
                Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(),
                "Medication could not be updated"
        );
    }

    @Transactional
    public void delete(Long id) throws BusinessException {
        Medication medication = medicationRepository.findByIdOptional(id)
                .orElseThrow(() -> new BusinessException(
                        Response.Status.NOT_FOUND.getStatusCode(),
                        "Medication with id " + id + " not found"
                ));
        medicationRepository.delete(medication);
    }
}
