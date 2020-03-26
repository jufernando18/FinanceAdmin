package com.jferza.financeadmin.web.rest;

import com.jferza.financeadmin.FinanceAdminApp;
import com.jferza.financeadmin.domain.AuditRegistry;
import com.jferza.financeadmin.repository.AuditRegistryRepository;
import com.jferza.financeadmin.service.AuditRegistryService;
import com.jferza.financeadmin.service.dto.AuditRegistryDTO;
import com.jferza.financeadmin.service.mapper.AuditRegistryMapper;
import com.jferza.financeadmin.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static com.jferza.financeadmin.web.rest.TestUtil.sameInstant;
import static com.jferza.financeadmin.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.jferza.financeadmin.domain.enumeration.State;
/**
 * Integration tests for the {@link AuditRegistryResource} REST controller.
 */
@SpringBootTest(classes = FinanceAdminApp.class)
public class AuditRegistryResourceIT {

    private static final State DEFAULT_STATE = State.ADDED;
    private static final State UPDATED_STATE = State.MODIFIED;

    private static final Long DEFAULT_CREATED_BY = 1L;
    private static final Long UPDATED_CREATED_BY = 2L;

    private static final ZonedDateTime DEFAULT_CREATED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Long DEFAULT_LAST_MODIFIED_BY = 1L;
    private static final Long UPDATED_LAST_MODIFIED_BY = 2L;

    private static final ZonedDateTime DEFAULT_LAST_MODIFIED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_LAST_MODIFIED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private AuditRegistryRepository auditRegistryRepository;

    @Autowired
    private AuditRegistryMapper auditRegistryMapper;

    @Autowired
    private AuditRegistryService auditRegistryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restAuditRegistryMockMvc;

    private AuditRegistry auditRegistry;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AuditRegistryResource auditRegistryResource = new AuditRegistryResource(auditRegistryService);
        this.restAuditRegistryMockMvc = MockMvcBuilders.standaloneSetup(auditRegistryResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AuditRegistry createEntity(EntityManager em) {
        AuditRegistry auditRegistry = new AuditRegistry()
            .state(DEFAULT_STATE)
            .createdBy(DEFAULT_CREATED_BY)
            .createdDate(DEFAULT_CREATED_DATE)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY)
            .lastModifiedDate(DEFAULT_LAST_MODIFIED_DATE);
        return auditRegistry;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AuditRegistry createUpdatedEntity(EntityManager em) {
        AuditRegistry auditRegistry = new AuditRegistry()
            .state(UPDATED_STATE)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        return auditRegistry;
    }

    @BeforeEach
    public void initTest() {
        auditRegistry = createEntity(em);
    }

    @Test
    @Transactional
    public void createAuditRegistry() throws Exception {
        int databaseSizeBeforeCreate = auditRegistryRepository.findAll().size();

        // Create the AuditRegistry
        AuditRegistryDTO auditRegistryDTO = auditRegistryMapper.toDto(auditRegistry);
        restAuditRegistryMockMvc.perform(post("/api/audit-registries")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(auditRegistryDTO)))
            .andExpect(status().isCreated());

        // Validate the AuditRegistry in the database
        List<AuditRegistry> auditRegistryList = auditRegistryRepository.findAll();
        assertThat(auditRegistryList).hasSize(databaseSizeBeforeCreate + 1);
        AuditRegistry testAuditRegistry = auditRegistryList.get(auditRegistryList.size() - 1);
        assertThat(testAuditRegistry.getState()).isEqualTo(DEFAULT_STATE);
        assertThat(testAuditRegistry.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testAuditRegistry.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testAuditRegistry.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testAuditRegistry.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void createAuditRegistryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = auditRegistryRepository.findAll().size();

        // Create the AuditRegistry with an existing ID
        auditRegistry.setId(1L);
        AuditRegistryDTO auditRegistryDTO = auditRegistryMapper.toDto(auditRegistry);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAuditRegistryMockMvc.perform(post("/api/audit-registries")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(auditRegistryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AuditRegistry in the database
        List<AuditRegistry> auditRegistryList = auditRegistryRepository.findAll();
        assertThat(auditRegistryList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkStateIsRequired() throws Exception {
        int databaseSizeBeforeTest = auditRegistryRepository.findAll().size();
        // set the field null
        auditRegistry.setState(null);

        // Create the AuditRegistry, which fails.
        AuditRegistryDTO auditRegistryDTO = auditRegistryMapper.toDto(auditRegistry);

        restAuditRegistryMockMvc.perform(post("/api/audit-registries")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(auditRegistryDTO)))
            .andExpect(status().isBadRequest());

        List<AuditRegistry> auditRegistryList = auditRegistryRepository.findAll();
        assertThat(auditRegistryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreatedByIsRequired() throws Exception {
        int databaseSizeBeforeTest = auditRegistryRepository.findAll().size();
        // set the field null
        auditRegistry.setCreatedBy(null);

        // Create the AuditRegistry, which fails.
        AuditRegistryDTO auditRegistryDTO = auditRegistryMapper.toDto(auditRegistry);

        restAuditRegistryMockMvc.perform(post("/api/audit-registries")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(auditRegistryDTO)))
            .andExpect(status().isBadRequest());

        List<AuditRegistry> auditRegistryList = auditRegistryRepository.findAll();
        assertThat(auditRegistryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreatedDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = auditRegistryRepository.findAll().size();
        // set the field null
        auditRegistry.setCreatedDate(null);

        // Create the AuditRegistry, which fails.
        AuditRegistryDTO auditRegistryDTO = auditRegistryMapper.toDto(auditRegistry);

        restAuditRegistryMockMvc.perform(post("/api/audit-registries")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(auditRegistryDTO)))
            .andExpect(status().isBadRequest());

        List<AuditRegistry> auditRegistryList = auditRegistryRepository.findAll();
        assertThat(auditRegistryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAuditRegistries() throws Exception {
        // Initialize the database
        auditRegistryRepository.saveAndFlush(auditRegistry);

        // Get all the auditRegistryList
        restAuditRegistryMockMvc.perform(get("/api/audit-registries?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(auditRegistry.getId().intValue())))
            .andExpect(jsonPath("$.[*].state").value(hasItem(DEFAULT_STATE.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.intValue())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(sameInstant(DEFAULT_CREATED_DATE))))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY.intValue())))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(sameInstant(DEFAULT_LAST_MODIFIED_DATE))));
    }
    
    @Test
    @Transactional
    public void getAuditRegistry() throws Exception {
        // Initialize the database
        auditRegistryRepository.saveAndFlush(auditRegistry);

        // Get the auditRegistry
        restAuditRegistryMockMvc.perform(get("/api/audit-registries/{id}", auditRegistry.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(auditRegistry.getId().intValue()))
            .andExpect(jsonPath("$.state").value(DEFAULT_STATE.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.intValue()))
            .andExpect(jsonPath("$.createdDate").value(sameInstant(DEFAULT_CREATED_DATE)))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY.intValue()))
            .andExpect(jsonPath("$.lastModifiedDate").value(sameInstant(DEFAULT_LAST_MODIFIED_DATE)));
    }

    @Test
    @Transactional
    public void getNonExistingAuditRegistry() throws Exception {
        // Get the auditRegistry
        restAuditRegistryMockMvc.perform(get("/api/audit-registries/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAuditRegistry() throws Exception {
        // Initialize the database
        auditRegistryRepository.saveAndFlush(auditRegistry);

        int databaseSizeBeforeUpdate = auditRegistryRepository.findAll().size();

        // Update the auditRegistry
        AuditRegistry updatedAuditRegistry = auditRegistryRepository.findById(auditRegistry.getId()).get();
        // Disconnect from session so that the updates on updatedAuditRegistry are not directly saved in db
        em.detach(updatedAuditRegistry);
        updatedAuditRegistry
            .state(UPDATED_STATE)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        AuditRegistryDTO auditRegistryDTO = auditRegistryMapper.toDto(updatedAuditRegistry);

        restAuditRegistryMockMvc.perform(put("/api/audit-registries")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(auditRegistryDTO)))
            .andExpect(status().isOk());

        // Validate the AuditRegistry in the database
        List<AuditRegistry> auditRegistryList = auditRegistryRepository.findAll();
        assertThat(auditRegistryList).hasSize(databaseSizeBeforeUpdate);
        AuditRegistry testAuditRegistry = auditRegistryList.get(auditRegistryList.size() - 1);
        assertThat(testAuditRegistry.getState()).isEqualTo(UPDATED_STATE);
        assertThat(testAuditRegistry.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testAuditRegistry.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testAuditRegistry.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testAuditRegistry.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingAuditRegistry() throws Exception {
        int databaseSizeBeforeUpdate = auditRegistryRepository.findAll().size();

        // Create the AuditRegistry
        AuditRegistryDTO auditRegistryDTO = auditRegistryMapper.toDto(auditRegistry);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAuditRegistryMockMvc.perform(put("/api/audit-registries")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(auditRegistryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AuditRegistry in the database
        List<AuditRegistry> auditRegistryList = auditRegistryRepository.findAll();
        assertThat(auditRegistryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAuditRegistry() throws Exception {
        // Initialize the database
        auditRegistryRepository.saveAndFlush(auditRegistry);

        int databaseSizeBeforeDelete = auditRegistryRepository.findAll().size();

        // Delete the auditRegistry
        restAuditRegistryMockMvc.perform(delete("/api/audit-registries/{id}", auditRegistry.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AuditRegistry> auditRegistryList = auditRegistryRepository.findAll();
        assertThat(auditRegistryList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
