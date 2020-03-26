package com.jferza.financeadmin.service.impl;

import com.jferza.financeadmin.service.AuditRegistryService;
import com.jferza.financeadmin.domain.AuditRegistry;
import com.jferza.financeadmin.repository.AuditRegistryRepository;
import com.jferza.financeadmin.service.dto.AuditRegistryDTO;
import com.jferza.financeadmin.service.mapper.AuditRegistryMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link AuditRegistry}.
 */
@Service
@Transactional
public class AuditRegistryServiceImpl implements AuditRegistryService {

    private final Logger log = LoggerFactory.getLogger(AuditRegistryServiceImpl.class);

    private final AuditRegistryRepository auditRegistryRepository;

    private final AuditRegistryMapper auditRegistryMapper;

    public AuditRegistryServiceImpl(AuditRegistryRepository auditRegistryRepository, AuditRegistryMapper auditRegistryMapper) {
        this.auditRegistryRepository = auditRegistryRepository;
        this.auditRegistryMapper = auditRegistryMapper;
    }

    /**
     * Save a auditRegistry.
     *
     * @param auditRegistryDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public AuditRegistryDTO save(AuditRegistryDTO auditRegistryDTO) {
        log.debug("Request to save AuditRegistry : {}", auditRegistryDTO);
        AuditRegistry auditRegistry = auditRegistryMapper.toEntity(auditRegistryDTO);
        auditRegistry = auditRegistryRepository.save(auditRegistry);
        return auditRegistryMapper.toDto(auditRegistry);
    }

    /**
     * Get all the auditRegistries.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<AuditRegistryDTO> findAll(Pageable pageable) {
        log.debug("Request to get all AuditRegistries");
        return auditRegistryRepository.findAll(pageable)
            .map(auditRegistryMapper::toDto);
    }

    /**
     * Get one auditRegistry by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<AuditRegistryDTO> findOne(Long id) {
        log.debug("Request to get AuditRegistry : {}", id);
        return auditRegistryRepository.findById(id)
            .map(auditRegistryMapper::toDto);
    }

    /**
     * Delete the auditRegistry by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete AuditRegistry : {}", id);
        auditRegistryRepository.deleteById(id);
    }
}
