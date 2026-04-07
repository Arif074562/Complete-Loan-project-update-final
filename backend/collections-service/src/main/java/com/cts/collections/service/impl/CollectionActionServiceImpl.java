package com.cts.collections.service.impl;

import com.cts.collections.dto.CollectionActionRequestDTO;
import com.cts.collections.dto.CollectionActionResponseDTO;
import com.cts.collections.entity.CollectionAction;
import com.cts.collections.mapper.CollectionActionMapper;
import com.cts.collections.repository.CollectionActionRepository;
import com.cts.collections.service.CollectionActionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class CollectionActionServiceImpl implements CollectionActionService {

    private final CollectionActionRepository repository;
    private final CollectionActionMapper mapper;

    @Override
    public CollectionActionResponseDTO create(CollectionActionRequestDTO request) {
        log.info("Creating collection action for loan account: {}", request.getLoanAccountId());
        CollectionAction entity = mapper.toEntity(request);
        if (entity.getActionDate() == null) {
            entity.setActionDate(LocalDate.now());
        }
        entity = repository.save(entity);
        log.info("Collection action created: {}", entity.getActionId());
        return mapper.toResponseDTO(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CollectionActionResponseDTO> getByLoanAccountId(Long loanAccountId) {
        log.info("Fetching collection actions for account: {}", loanAccountId);
        return mapper.toResponseDTOList(repository.findByLoanAccountId(loanAccountId));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CollectionActionResponseDTO> getAll(Pageable pageable) {
        log.info("Fetching all collection actions (pageable)");
        return repository.findAll(pageable).map(mapper::toResponseDTO);
    }
}
