package com.patientportal.repository;

import com.patientportal.model.Condition;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@ApplicationScoped
public class ConditionRepository implements PanacheRepository<Condition> {
    public Set<Condition> findByIds(Set<Long> ids) {
        List<Condition> conditionList = list("id in ?1", ids);
        return new HashSet<>(conditionList);
    }
}
