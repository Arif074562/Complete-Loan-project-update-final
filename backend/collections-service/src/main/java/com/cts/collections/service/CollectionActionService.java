package com.cts.collections.service;

import com.cts.collections.dto.CollectionActionRequestDTO;
import com.cts.collections.dto.CollectionActionResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CollectionActionService {

    CollectionActionResponseDTO create(CollectionActionRequestDTO request);

    List<CollectionActionResponseDTO> getByLoanAccountId(Long loanAccountId);

    Page<CollectionActionResponseDTO> getAll(Pageable pageable);
}
