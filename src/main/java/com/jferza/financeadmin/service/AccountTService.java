package com.jferza.financeadmin.service;

import com.jferza.financeadmin.service.dto.AccountTDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.jferza.financeadmin.domain.AccountT}.
 */
public interface AccountTService {

    /**
     * Save a accountT.
     *
     * @param accountTDTO the entity to save.
     * @return the persisted entity.
     */
    AccountTDTO save(AccountTDTO accountTDTO);

    /**
     * Get all the accountTS.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<AccountTDTO> findAll(Pageable pageable);

    /**
     * Get the "id" accountT.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AccountTDTO> findOne(Long id);

    /**
     * Delete the "id" accountT.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
