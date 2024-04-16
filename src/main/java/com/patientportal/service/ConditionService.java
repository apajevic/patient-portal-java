package com.patientportal.service;

import com.patientportal.dto.UpdateConditionDTO;
import com.patientportal.exception.BusinessException;
import com.patientportal.exception.TechnicalException;
import com.patientportal.model.Condition;
import com.patientportal.repository.ConditionRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.Response;

import java.util.List;

@ApplicationScoped
public class ConditionService {
    @Inject
    ConditionRepository conditionRepository;

    @Transactional
    public Condition create(Condition condition) throws BusinessException, TechnicalException {
        Condition existingCondition = conditionRepository.find("name", condition.getName()).firstResult();
        if (existingCondition != null) {
            throw new BusinessException(
                    Response.Status.BAD_REQUEST.getStatusCode(),
                    "A condition with this name already exists"
            );
        }

        conditionRepository.persist(condition);
        if(conditionRepository.isPersistent(condition)){
            return condition;
        }
        throw new TechnicalException(
                Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(),
                "Condition could not be created"
        );
    }

    public Condition getById(Long id) throws BusinessException {
        return conditionRepository.findByIdOptional(id)
                .orElseThrow(() -> new BusinessException(
                        Response.Status.NOT_FOUND.getStatusCode(),
                        "Condition with id " + id + " not found"
                ));
    }

    public List<Condition> getAll() throws BusinessException {
        List<Condition> conditions = conditionRepository.listAll();

        if (conditions == null || conditions.isEmpty()) {
            throw new BusinessException(
                    Response.Status.NOT_FOUND.getStatusCode(),
                    "No conditions found"
            );
        }
        return conditions;
    }

    @Transactional
    public Condition update(Long id, UpdateConditionDTO updatedCondition) throws BusinessException, TechnicalException {
        Condition condition = conditionRepository.findByIdOptional(id)
                .orElseThrow(() -> new BusinessException(
                        Response.Status.NOT_FOUND.getStatusCode(),
                        "Condition with id " + id + " not found"
                ));

        if(updatedCondition.name() != null){
            condition.setName(updatedCondition.name());
        }

        if(updatedCondition.description() != null){
            condition.setDescription(updatedCondition.description());
        }

        conditionRepository.persist(condition);
        if(conditionRepository.isPersistent(condition)){
            return condition;
        }
        throw new TechnicalException(
                Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(),
                "Condition could not be updated"
        );
    }

    @Transactional
    public void delete(Long id) throws BusinessException {
        Condition condition = conditionRepository.findByIdOptional(id)
                .orElseThrow(() -> new BusinessException(
                        Response.Status.NOT_FOUND.getStatusCode(),
                        "Condition with id " + id + " not found"
                ));
        conditionRepository.delete(condition);
    }
}
