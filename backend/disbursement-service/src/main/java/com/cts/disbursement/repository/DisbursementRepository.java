package com.cts.disbursement.repository;

import com.cts.disbursement.entity.Disbursement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DisbursementRepository extends JpaRepository<Disbursement, Long> {

    Optional<Disbursement> findByApplicationId(Long applicationId);
}
