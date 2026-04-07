package com.cts.collections.repository;

import com.cts.collections.entity.CollectionAction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CollectionActionRepository extends JpaRepository<CollectionAction, Long> {

    List<CollectionAction> findByLoanAccountId(Long loanAccountId);

    Page<CollectionAction> findAll(Pageable pageable);
}
