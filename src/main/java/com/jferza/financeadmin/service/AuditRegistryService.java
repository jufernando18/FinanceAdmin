package com.jferza.financeadmin.service;

import com.jferza.financeadmin.service.dto.AuditRegistryDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.jferza.financeadmin.domain.AuditRegistry}.
 */
public interface AuditRegistryService {

    /**
     * Save a auditRegistry.
     *
     * @param auditRegistryDTO the entity to save.
     * @return the persisted entity.
     */
    AuditRegistryDTO save(AuditRegistryDTO auditRegistryDTO);

    /**
     * Get all the auditRegistries.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<AuditRegistryDTO> findAll(Pageable pageable);

    /**
     * Get the "id" auditRegistry.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AuditRegistryDTO> findOne(Long id);

    /**
     * Delete the "id" auditRegistry.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
