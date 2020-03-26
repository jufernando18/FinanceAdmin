package com.jferza.financeadmin.web.rest;

import com.jferza.financeadmin.service.AuditRegistryService;
import com.jferza.financeadmin.web.rest.errors.BadRequestAlertException;
import com.jferza.financeadmin.service.dto.AuditRegistryDTO;

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
 * REST controller for managing {@link com.jferza.financeadmin.domain.AuditRegistry}.
 */
@RestController
@RequestMapping("/api")
public class AuditRegistryResource {

    private final Logger log = LoggerFactory.getLogger(AuditRegistryResource.class);

    private static final String ENTITY_NAME = "auditRegistry";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AuditRegistryService auditRegistryService;

    public AuditRegistryResource(AuditRegistryService auditRegistryService) {
        this.auditRegistryService = auditRegistryService;
    }

    /**
     * {@code POST  /audit-registries} : Create a new auditRegistry.
     *
     * @param auditRegistryDTO the auditRegistryDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new auditRegistryDTO, or with status {@code 400 (Bad Request)} if the auditRegistry has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/audit-registries")
    public ResponseEntity<AuditRegistryDTO> createAuditRegistry(@Valid @RequestBody AuditRegistryDTO auditRegistryDTO) throws URISyntaxException {
        log.debug("REST request to save AuditRegistry : {}", auditRegistryDTO);
        if (auditRegistryDTO.getId() != null) {
            throw new BadRequestAlertException("A new auditRegistry cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AuditRegistryDTO result = auditRegistryService.save(auditRegistryDTO);
        return ResponseEntity.created(new URI("/api/audit-registries/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /audit-registries} : Updates an existing auditRegistry.
     *
     * @param auditRegistryDTO the auditRegistryDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated auditRegistryDTO,
     * or with status {@code 400 (Bad Request)} if the auditRegistryDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the auditRegistryDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/audit-registries")
    public ResponseEntity<AuditRegistryDTO> updateAuditRegistry(@Valid @RequestBody AuditRegistryDTO auditRegistryDTO) throws URISyntaxException {
        log.debug("REST request to update AuditRegistry : {}", auditRegistryDTO);
        if (auditRegistryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AuditRegistryDTO result = auditRegistryService.save(auditRegistryDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, auditRegistryDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /audit-registries} : get all the auditRegistries.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of auditRegistries in body.
     */
    @GetMapping("/audit-registries")
    public ResponseEntity<List<AuditRegistryDTO>> getAllAuditRegistries(Pageable pageable) {
        log.debug("REST request to get a page of AuditRegistries");
        Page<AuditRegistryDTO> page = auditRegistryService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /audit-registries/:id} : get the "id" auditRegistry.
     *
     * @param id the id of the auditRegistryDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the auditRegistryDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/audit-registries/{id}")
    public ResponseEntity<AuditRegistryDTO> getAuditRegistry(@PathVariable Long id) {
        log.debug("REST request to get AuditRegistry : {}", id);
        Optional<AuditRegistryDTO> auditRegistryDTO = auditRegistryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(auditRegistryDTO);
    }

    /**
     * {@code DELETE  /audit-registries/:id} : delete the "id" auditRegistry.
     *
     * @param id the id of the auditRegistryDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/audit-registries/{id}")
    public ResponseEntity<Void> deleteAuditRegistry(@PathVariable Long id) {
        log.debug("REST request to delete AuditRegistry : {}", id);
        auditRegistryService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
