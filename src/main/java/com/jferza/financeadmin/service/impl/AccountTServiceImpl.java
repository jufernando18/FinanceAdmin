package com.jferza.financeadmin.service.impl;

import com.jferza.financeadmin.service.AccountTService;
import com.jferza.financeadmin.domain.AccountT;
import com.jferza.financeadmin.repository.AccountTRepository;
import com.jferza.financeadmin.service.dto.AccountTDTO;
import com.jferza.financeadmin.service.mapper.AccountTMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link AccountT}.
 */
@Service
@Transactional
public class AccountTServiceImpl implements AccountTService {

    private final Logger log = LoggerFactory.getLogger(AccountTServiceImpl.class);

    private final AccountTRepository accountTRepository;

    private final AccountTMapper accountTMapper;

    public AccountTServiceImpl(AccountTRepository accountTRepository, AccountTMapper accountTMapper) {
        this.accountTRepository = accountTRepository;
        this.accountTMapper = accountTMapper;
    }

    /**
     * Save a accountT.
     *
     * @param accountTDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public AccountTDTO save(AccountTDTO accountTDTO) {
        log.debug("Request to save AccountT : {}", accountTDTO);
        AccountT accountT = accountTMapper.toEntity(accountTDTO);
        accountT = accountTRepository.save(accountT);
        return accountTMapper.toDto(accountT);
    }

    /**
     * Get all the accountTS.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<AccountTDTO> findAll(Pageable pageable) {
        log.debug("Request to get all AccountTS");
        return accountTRepository.findAll(pageable)
            .map(accountTMapper::toDto);
    }

    /**
     * Get one accountT by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<AccountTDTO> findOne(Long id) {
        log.debug("Request to get AccountT : {}", id);
        return accountTRepository.findById(id)
            .map(accountTMapper::toDto);
    }

    /**
     * Delete the accountT by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete AccountT : {}", id);
        accountTRepository.deleteById(id);
    }
}
