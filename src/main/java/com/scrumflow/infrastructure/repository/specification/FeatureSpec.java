package com.scrumflow.infrastructure.repository.specification;

import org.springframework.data.jpa.domain.Specification;

import com.scrumflow.application.dto.filter.FeatureRequestFilterDTO;
import com.scrumflow.domain.model.Feature;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FeatureSpec {

    public static Specification<Feature> filtrarPor(FeatureRequestFilterDTO filtros) {
        return Specification.where(filtroPorProjectId(filtros.projectId()));
    }

    private static Specification<Feature> filtroPorProjectId(Long projectId) {
        return (root, query, criteriaBuilder) ->
                projectId != null
                        ? criteriaBuilder.equal(root.join("project").get("id"), projectId)
                        : criteriaBuilder.conjunction();
    }
}
