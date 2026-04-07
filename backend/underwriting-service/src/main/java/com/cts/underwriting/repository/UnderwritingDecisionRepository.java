package com.cts.underwriting.repository;

import com.cts.underwriting.entity.UnderwritingDecision;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UnderwritingDecisionRepository extends JpaRepository<UnderwritingDecision, Long> {

    List<UnderwritingDecision> findByApplicationId(Long applicationId);
}
