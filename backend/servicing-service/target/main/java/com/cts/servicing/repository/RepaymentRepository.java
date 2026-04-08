package com.cts.servicing.repository;

import com.cts.servicing.entity.Repayment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RepaymentRepository extends JpaRepository<Repayment, Long> {

    List<Repayment> findByLoanAccountId(Long loanAccountId);
}
