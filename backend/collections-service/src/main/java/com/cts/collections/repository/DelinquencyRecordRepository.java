package com.cts.collections.repository;

import com.cts.collections.entity.DelinquencyRecord;
import com.cts.collections.enums.DelinquencyBucket;
import com.cts.collections.enums.DelinquencyStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DelinquencyRecordRepository extends JpaRepository<DelinquencyRecord, Long> {

    List<DelinquencyRecord> findByLoanAccountId(Long loanAccountId);

    List<DelinquencyRecord> findByBucket(DelinquencyBucket bucket);

    List<DelinquencyRecord> findByBucketAndStatus(DelinquencyBucket bucket, DelinquencyStatus status);
}
