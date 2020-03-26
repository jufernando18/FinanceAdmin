package com.jferza.financeadmin.web.rest;

import com.jferza.financeadmin.service.AccountTService;
import com.jferza.financeadmin.web.rest.errors.BadRequestAlertException;
import com.jferza.financeadmin.service.dto.AccountTDTO;

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
 * REST controller for managing {@link com.jferza.financeadmin.domain.AccountT}.
 */
@RestController
@RequestMapping("/api")
public class AccountTResource {

    private final Logger log = LoggerFactory.getLogger(AccountTResource.class);

    private static final String ENTITY_NAME = "accountT";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AccountTService accountTService;

    public AccountTResource(AccountTService accountTService) {
        this.accountTService = accountTService;
    }

    /**
     * {@code POST  /account-ts} : Create a new accountT.
     *
     * @param accountTDTO the accountTDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new accountTDTO, or with status {@code 400 (Bad Request)} if the accountT has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/account-ts")
    public ResponseEntity<AccountTDTO> createAccountT(@Valid @RequestBody AccountTDTO accountTDTO) throws URISyntaxException {
        log.debug("REST request to save AccountT : {}", accountTDTO);
        if (accountTDTO.getId() != null) {
            throw new BadRequestAlertException("A new accountT cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AccountTDTO result = accountTService.save(accountTDTO);
        return ResponseEntity.created(new URI("/api/account-ts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /account-ts} : Updates an existing accountT.
     *
     * @param accountTDTO the accountTDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated accountTDTO,
     * or with status {@code 400 (Bad Request)} if the accountTDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the accountTDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/account-ts")
    public ResponseEntity<AccountTDTO> updateAccountT(@Valid @RequestBody AccountTDTO accountTDTO) throws URISyntaxException {
        log.debug("REST request to update AccountT : {}", accountTDTO);
        if (accountTDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AccountTDTO result = accountTService.save(accountTDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, accountTDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /account-ts} : get all the accountTS.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of accountTS in body.
     */
    @GetMapping("/account-ts")
    public ResponseEntity<List<AccountTDTO>> getAllAccountTS(Pageable pageable) {
        log.debug("REST request to get a page of AccountTS");
        Page<AccountTDTO> page = accountTService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /account-ts/:id} : get the "id" accountT.
     *
     * @param id the id of the accountTDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the accountTDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/account-ts/{id}")
    public ResponseEntity<AccountTDTO> getAccountT(@PathVariable Long id) {
        log.debug("REST request to get AccountT : {}", id);
        Optional<AccountTDTO> accountTDTO = accountTService.findOne(id);
        return ResponseUtil.wrapOrNotFound(accountTDTO);
    }

    /**
     * {@code DELETE  /account-ts/:id} : delete the "id" accountT.
     *
     * @param id the id of the accountTDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/account-ts/{id}")
    public ResponseEntity<Void> deleteAccountT(@PathVariable Long id) {
        log.debug("REST request to delete AccountT : {}", id);
        accountTService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
