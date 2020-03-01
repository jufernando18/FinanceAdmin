package com.jferza.financeadmin.service;

import com.jferza.financeadmin.service.dto.UserRegistryDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.jferza.financeadmin.domain.UserRegistry}.
 */
public interface UserRegistryService {

    /**
     * Save a userRegistry.
     *
     * @param userRegistryDTO the entity to save.
     * @return the persisted entity.
     */
    UserRegistryDTO save(UserRegistryDTO userRegistryDTO);

    /**
     * Get all the userRegistries.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<UserRegistryDTO> findAll(Pageable pageable);

    /**
     * Get the "id" userRegistry.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<UserRegistryDTO> findOne(Long id);

    /**
     * Delete the "id" userRegistry.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
