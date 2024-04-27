package com.patientportal.repository;

import com.patientportal.model.Condition;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@ApplicationScoped
public class ConditionRepository implements PanacheRepositoryBase<Condition, UUID> {
    public Set<Condition> findByIds(Set<UUID> ids) {
        List<Condition> conditionList = list("id in ?1", ids);
        return new HashSet<>(conditionList);
    }
}
