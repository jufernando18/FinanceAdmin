package com.jferza.financeadmin.service.impl;

import com.jferza.financeadmin.service.UserRegistryService;
import com.jferza.financeadmin.domain.UserRegistry;
import com.jferza.financeadmin.repository.UserRegistryRepository;
import com.jferza.financeadmin.service.dto.UserRegistryDTO;
import com.jferza.financeadmin.service.mapper.UserRegistryMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link UserRegistry}.
 */
@Service
@Transactional
public class UserRegistryServiceImpl implements UserRegistryService {

    private final Logger log = LoggerFactory.getLogger(UserRegistryServiceImpl.class);

    private final UserRegistryRepository userRegistryRepository;

    private final UserRegistryMapper userRegistryMapper;

    public UserRegistryServiceImpl(UserRegistryRepository userRegistryRepository, UserRegistryMapper userRegistryMapper) {
        this.userRegistryRepository = userRegistryRepository;
        this.userRegistryMapper = userRegistryMapper;
    }

    /**
     * Save a userRegistry.
     *
     * @param userRegistryDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public UserRegistryDTO save(UserRegistryDTO userRegistryDTO) {
        log.debug("Request to save UserRegistry : {}", userRegistryDTO);
        UserRegistry userRegistry = userRegistryMapper.toEntity(userRegistryDTO);
        userRegistry = userRegistryRepository.save(userRegistry);
        return userRegistryMapper.toDto(userRegistry);
    }

    /**
     * Get all the userRegistries.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<UserRegistryDTO> findAll(Pageable pageable) {
        log.debug("Request to get all UserRegistries");
        return userRegistryRepository.findAll(pageable)
            .map(userRegistryMapper::toDto);
    }

    /**
     * Get one userRegistry by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<UserRegistryDTO> findOne(Long id) {
        log.debug("Request to get UserRegistry : {}", id);
        return userRegistryRepository.findById(id)
            .map(userRegistryMapper::toDto);
    }

    /**
     * Delete the userRegistry by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete UserRegistry : {}", id);
        userRegistryRepository.deleteById(id);
    }
}
