package com.scrumflow.infrastructure.repository;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.scrumflow.domain.model.Feature;

@Repository
public interface FeatureRepository extends JpaRepository<Feature, Long> {

    List<Feature> findAll(Specification<Feature> spec);

    List<Feature> findAllBySprintsNull();
}
