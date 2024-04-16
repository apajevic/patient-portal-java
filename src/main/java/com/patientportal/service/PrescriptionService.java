package com.patientportal.service;

import com.patientportal.dto.UpdatePrescriptionDTO;
import com.patientportal.exception.BusinessException;
import com.patientportal.exception.TechnicalException;
import com.patientportal.model.Prescription;
import com.patientportal.repository.PrescriptionRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.Response;

import java.util.List;

@ApplicationScoped
public class PrescriptionService {
    @Inject
    PrescriptionRepository prescriptionRepository;

    @Transactional
    public Prescription create(Prescription prescription) throws BusinessException, TechnicalException {
        Prescription existingPrescription = prescriptionRepository.find("id", prescription.getId()).firstResult();
        if (existingPrescription != null) {
            throw new BusinessException(
                    Response.Status.BAD_REQUEST.getStatusCode(),
                    "A prescription with this id already exists"
            );
        }

        prescriptionRepository.persist(prescription);
        if(prescriptionRepository.isPersistent(prescription)){
            return prescription;
        }
        throw new TechnicalException(
                Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(),
                "Prescription could not be created"
        );
    }

    public Prescription getById(Long id) throws BusinessException {
        return prescriptionRepository.findByIdOptional(id)
                .orElseThrow(() -> new BusinessException(
                        Response.Status.NOT_FOUND.getStatusCode(),
                        "Prescription with id " + id + " not found"
                ));
    }

    public List<Prescription> getAll() throws BusinessException {
        List<Prescription> prescriptions = prescriptionRepository.listAll();

        if (prescriptions == null || prescriptions.isEmpty()) {
            throw new BusinessException(
                    Response.Status.NOT_FOUND.getStatusCode(),
                    "No prescriptions found"
            );
        }
        return prescriptions;
    }

    @Transactional
    public Prescription update(Long id, UpdatePrescriptionDTO updatedPrescription) throws BusinessException, TechnicalException {
        Prescription prescription = prescriptionRepository.findByIdOptional(id)
                .orElseThrow(() -> new BusinessException(
                        Response.Status.NOT_FOUND.getStatusCode(),
                        "Prescription with id " + id + " not found"
                ));

        if(updatedPrescription.validUntil() != null){
            prescription.setValidUntil(updatedPrescription.validUntil());
        }

        if(updatedPrescription.notes() != null){
            prescription.setNotes(updatedPrescription.notes());
        }

        if(updatedPrescription.status() != null){
            prescription.setStatus(updatedPrescription.status());
        }

        prescriptionRepository.persist(prescription);
        if(prescriptionRepository.isPersistent(prescription)){
            return prescription;
        }
        throw new TechnicalException(
                Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(),
                "Prescription could not be updated"
        );
    }

    @Transactional
    public void delete(Long id) throws BusinessException {
        Prescription prescription = prescriptionRepository.findByIdOptional(id)
                .orElseThrow(() -> new BusinessException(
                        Response.Status.NOT_FOUND.getStatusCode(),
                        "Prescription with id " + id + " not found"
                ));
        prescriptionRepository.delete(prescription);
    }
}
