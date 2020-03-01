package com.jferza.financeadmin.web.rest;

import com.jferza.financeadmin.service.UserRegistryService;
import com.jferza.financeadmin.web.rest.errors.BadRequestAlertException;
import com.jferza.financeadmin.service.dto.UserRegistryDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.jferza.financeadmin.domain.UserRegistry}.
 */
@RestController
@RequestMapping("/api")
public class UserRegistryResource {

    private final Logger log = LoggerFactory.getLogger(UserRegistryResource.class);

    private static final String ENTITY_NAME = "userRegistry";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UserRegistryService userRegistryService;

    public UserRegistryResource(UserRegistryService userRegistryService) {
        this.userRegistryService = userRegistryService;
    }

    /**
     * {@code POST  /user-registries} : Create a new userRegistry.
     *
     * @param userRegistryDTO the userRegistryDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new userRegistryDTO, or with status {@code 400 (Bad Request)} if the userRegistry has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/user-registries")
    public ResponseEntity<UserRegistryDTO> createUserRegistry(@Valid @RequestBody UserRegistryDTO userRegistryDTO) throws URISyntaxException {
        log.debug("REST request to save UserRegistry : {}", userRegistryDTO);
        if (userRegistryDTO.getId() != null) {
            throw new BadRequestAlertException("A new userRegistry cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UserRegistryDTO result = userRegistryService.save(userRegistryDTO);
        return ResponseEntity.created(new URI("/api/user-registries/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /user-registries} : Updates an existing userRegistry.
     *
     * @param userRegistryDTO the userRegistryDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated userRegistryDTO,
     * or with status {@code 400 (Bad Request)} if the userRegistryDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the userRegistryDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/user-registries")
    public ResponseEntity<UserRegistryDTO> updateUserRegistry(@Valid @RequestBody UserRegistryDTO userRegistryDTO) throws URISyntaxException {
        log.debug("REST request to update UserRegistry : {}", userRegistryDTO);
        if (userRegistryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        UserRegistryDTO result = userRegistryService.save(userRegistryDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, userRegistryDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /user-registries} : get all the userRegistries.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of userRegistries in body.
     */
    @GetMapping("/user-registries")
    public ResponseEntity<List<UserRegistryDTO>> getAllUserRegistries(Pageable pageable) {
        log.debug("REST request to get a page of UserRegistries");
        Page<UserRegistryDTO> page = userRegistryService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /user-registries/:id} : get the "id" userRegistry.
     *
     * @param id the id of the userRegistryDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the userRegistryDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/user-registries/{id}")
    public ResponseEntity<UserRegistryDTO> getUserRegistry(@PathVariable Long id) {
        log.debug("REST request to get UserRegistry : {}", id);
        Optional<UserRegistryDTO> userRegistryDTO = userRegistryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(userRegistryDTO);
    }

    /**
     * {@code DELETE  /user-registries/:id} : delete the "id" userRegistry.
     *
     * @param id the id of the userRegistryDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/user-registries/{id}")
    public ResponseEntity<Void> deleteUserRegistry(@PathVariable Long id) {
        log.debug("REST request to delete UserRegistry : {}", id);
        userRegistryService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
