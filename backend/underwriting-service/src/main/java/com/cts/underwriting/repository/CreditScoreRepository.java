package com.cts.underwriting.repository;

import com.cts.underwriting.entity.CreditScore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CreditScoreRepository extends JpaRepository<CreditScore, Long> {

    List<CreditScore> findByApplicationId(Long applicationId);
}
