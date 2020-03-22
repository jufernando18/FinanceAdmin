package com.jferza.financeadmin.web.rest;

import com.jferza.financeadmin.FinanceAdminApp;
import com.jferza.financeadmin.domain.UserRegistry;
import com.jferza.financeadmin.repository.UserRegistryRepository;
import com.jferza.financeadmin.service.UserRegistryService;
import com.jferza.financeadmin.service.dto.UserRegistryDTO;
import com.jferza.financeadmin.service.mapper.UserRegistryMapper;
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
import java.util.List;

import static com.jferza.financeadmin.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link UserRegistryResource} REST controller.
 */
@SpringBootTest(classes = FinanceAdminApp.class)
public class UserRegistryResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_USERNAME = "AAAAAAAAAA";
    private static final String UPDATED_USERNAME = "BBBBBBBBBB";

    private static final String DEFAULT_PASSWORD = "AAAAAAAAAA";
    private static final String UPDATED_PASSWORD = "BBBBBBBBBB";

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_TOKEN = "AAAAAAAAAA";
    private static final String UPDATED_TOKEN = "BBBBBBBBBB";

    private static final Boolean DEFAULT_SESSION = false;
    private static final Boolean UPDATED_SESSION = true;

    @Autowired
    private UserRegistryRepository userRegistryRepository;

    @Autowired
    private UserRegistryMapper userRegistryMapper;

    @Autowired
    private UserRegistryService userRegistryService;

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

    private MockMvc restUserRegistryMockMvc;

    private UserRegistry userRegistry;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final UserRegistryResource userRegistryResource = new UserRegistryResource(userRegistryService);
        this.restUserRegistryMockMvc = MockMvcBuilders.standaloneSetup(userRegistryResource)
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
    public static UserRegistry createEntity(EntityManager em) {
        UserRegistry userRegistry = new UserRegistry()
            .name(DEFAULT_NAME)
            .username(DEFAULT_USERNAME)
            .password(DEFAULT_PASSWORD)
            .title(DEFAULT_TITLE)
            .token(DEFAULT_TOKEN)
            .session(DEFAULT_SESSION);
        return userRegistry;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserRegistry createUpdatedEntity(EntityManager em) {
        UserRegistry userRegistry = new UserRegistry()
            .name(UPDATED_NAME)
            .username(UPDATED_USERNAME)
            .password(UPDATED_PASSWORD)
            .title(UPDATED_TITLE)
            .token(UPDATED_TOKEN)
            .session(UPDATED_SESSION);
        return userRegistry;
    }

    @BeforeEach
    public void initTest() {
        userRegistry = createEntity(em);
    }

    @Test
    @Transactional
    public void createUserRegistry() throws Exception {
        int databaseSizeBeforeCreate = userRegistryRepository.findAll().size();

        // Create the UserRegistry
        UserRegistryDTO userRegistryDTO = userRegistryMapper.toDto(userRegistry);
        restUserRegistryMockMvc.perform(post("/api/user-registries")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userRegistryDTO)))
            .andExpect(status().isCreated());

        // Validate the UserRegistry in the database
        List<UserRegistry> userRegistryList = userRegistryRepository.findAll();
        assertThat(userRegistryList).hasSize(databaseSizeBeforeCreate + 1);
        UserRegistry testUserRegistry = userRegistryList.get(userRegistryList.size() - 1);
        assertThat(testUserRegistry.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testUserRegistry.getUsername()).isEqualTo(DEFAULT_USERNAME);
        assertThat(testUserRegistry.getPassword()).isEqualTo(DEFAULT_PASSWORD);
        assertThat(testUserRegistry.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testUserRegistry.getToken()).isEqualTo(DEFAULT_TOKEN);
        assertThat(testUserRegistry.isSession()).isEqualTo(DEFAULT_SESSION);
    }

    @Test
    @Transactional
    public void createUserRegistryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = userRegistryRepository.findAll().size();

        // Create the UserRegistry with an existing ID
        userRegistry.setId(1L);
        UserRegistryDTO userRegistryDTO = userRegistryMapper.toDto(userRegistry);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserRegistryMockMvc.perform(post("/api/user-registries")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userRegistryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UserRegistry in the database
        List<UserRegistry> userRegistryList = userRegistryRepository.findAll();
        assertThat(userRegistryList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = userRegistryRepository.findAll().size();
        // set the field null
        userRegistry.setName(null);

        // Create the UserRegistry, which fails.
        UserRegistryDTO userRegistryDTO = userRegistryMapper.toDto(userRegistry);

        restUserRegistryMockMvc.perform(post("/api/user-registries")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userRegistryDTO)))
            .andExpect(status().isBadRequest());

        List<UserRegistry> userRegistryList = userRegistryRepository.findAll();
        assertThat(userRegistryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUsernameIsRequired() throws Exception {
        int databaseSizeBeforeTest = userRegistryRepository.findAll().size();
        // set the field null
        userRegistry.setUsername(null);

        // Create the UserRegistry, which fails.
        UserRegistryDTO userRegistryDTO = userRegistryMapper.toDto(userRegistry);

        restUserRegistryMockMvc.perform(post("/api/user-registries")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userRegistryDTO)))
            .andExpect(status().isBadRequest());

        List<UserRegistry> userRegistryList = userRegistryRepository.findAll();
        assertThat(userRegistryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPasswordIsRequired() throws Exception {
        int databaseSizeBeforeTest = userRegistryRepository.findAll().size();
        // set the field null
        userRegistry.setPassword(null);

        // Create the UserRegistry, which fails.
        UserRegistryDTO userRegistryDTO = userRegistryMapper.toDto(userRegistry);

        restUserRegistryMockMvc.perform(post("/api/user-registries")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userRegistryDTO)))
            .andExpect(status().isBadRequest());

        List<UserRegistry> userRegistryList = userRegistryRepository.findAll();
        assertThat(userRegistryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTokenIsRequired() throws Exception {
        int databaseSizeBeforeTest = userRegistryRepository.findAll().size();
        // set the field null
        userRegistry.setToken(null);

        // Create the UserRegistry, which fails.
        UserRegistryDTO userRegistryDTO = userRegistryMapper.toDto(userRegistry);

        restUserRegistryMockMvc.perform(post("/api/user-registries")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userRegistryDTO)))
            .andExpect(status().isBadRequest());

        List<UserRegistry> userRegistryList = userRegistryRepository.findAll();
        assertThat(userRegistryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSessionIsRequired() throws Exception {
        int databaseSizeBeforeTest = userRegistryRepository.findAll().size();
        // set the field null
        userRegistry.setSession(null);

        // Create the UserRegistry, which fails.
        UserRegistryDTO userRegistryDTO = userRegistryMapper.toDto(userRegistry);

        restUserRegistryMockMvc.perform(post("/api/user-registries")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userRegistryDTO)))
            .andExpect(status().isBadRequest());

        List<UserRegistry> userRegistryList = userRegistryRepository.findAll();
        assertThat(userRegistryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllUserRegistries() throws Exception {
        // Initialize the database
        userRegistryRepository.saveAndFlush(userRegistry);

        // Get all the userRegistryList
        restUserRegistryMockMvc.perform(get("/api/user-registries?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userRegistry.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].username").value(hasItem(DEFAULT_USERNAME)))
            .andExpect(jsonPath("$.[*].password").value(hasItem(DEFAULT_PASSWORD)))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].token").value(hasItem(DEFAULT_TOKEN)))
            .andExpect(jsonPath("$.[*].session").value(hasItem(DEFAULT_SESSION.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getUserRegistry() throws Exception {
        // Initialize the database
        userRegistryRepository.saveAndFlush(userRegistry);

        // Get the userRegistry
        restUserRegistryMockMvc.perform(get("/api/user-registries/{id}", userRegistry.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(userRegistry.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.username").value(DEFAULT_USERNAME))
            .andExpect(jsonPath("$.password").value(DEFAULT_PASSWORD))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE))
            .andExpect(jsonPath("$.token").value(DEFAULT_TOKEN))
            .andExpect(jsonPath("$.session").value(DEFAULT_SESSION.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingUserRegistry() throws Exception {
        // Get the userRegistry
        restUserRegistryMockMvc.perform(get("/api/user-registries/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUserRegistry() throws Exception {
        // Initialize the database
        userRegistryRepository.saveAndFlush(userRegistry);

        int databaseSizeBeforeUpdate = userRegistryRepository.findAll().size();

        // Update the userRegistry
        UserRegistry updatedUserRegistry = userRegistryRepository.findById(userRegistry.getId()).get();
        // Disconnect from session so that the updates on updatedUserRegistry are not directly saved in db
        em.detach(updatedUserRegistry);
        updatedUserRegistry
            .name(UPDATED_NAME)
            .username(UPDATED_USERNAME)
            .password(UPDATED_PASSWORD)
            .title(UPDATED_TITLE)
            .token(UPDATED_TOKEN)
            .session(UPDATED_SESSION);
        UserRegistryDTO userRegistryDTO = userRegistryMapper.toDto(updatedUserRegistry);

        restUserRegistryMockMvc.perform(put("/api/user-registries")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userRegistryDTO)))
            .andExpect(status().isOk());

        // Validate the UserRegistry in the database
        List<UserRegistry> userRegistryList = userRegistryRepository.findAll();
        assertThat(userRegistryList).hasSize(databaseSizeBeforeUpdate);
        UserRegistry testUserRegistry = userRegistryList.get(userRegistryList.size() - 1);
        assertThat(testUserRegistry.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testUserRegistry.getUsername()).isEqualTo(UPDATED_USERNAME);
        assertThat(testUserRegistry.getPassword()).isEqualTo(UPDATED_PASSWORD);
        assertThat(testUserRegistry.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testUserRegistry.getToken()).isEqualTo(UPDATED_TOKEN);
        assertThat(testUserRegistry.isSession()).isEqualTo(UPDATED_SESSION);
    }

    @Test
    @Transactional
    public void updateNonExistingUserRegistry() throws Exception {
        int databaseSizeBeforeUpdate = userRegistryRepository.findAll().size();

        // Create the UserRegistry
        UserRegistryDTO userRegistryDTO = userRegistryMapper.toDto(userRegistry);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserRegistryMockMvc.perform(put("/api/user-registries")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userRegistryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UserRegistry in the database
        List<UserRegistry> userRegistryList = userRegistryRepository.findAll();
        assertThat(userRegistryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteUserRegistry() throws Exception {
        // Initialize the database
        userRegistryRepository.saveAndFlush(userRegistry);

        int databaseSizeBeforeDelete = userRegistryRepository.findAll().size();

        // Delete the userRegistry
        restUserRegistryMockMvc.perform(delete("/api/user-registries/{id}", userRegistry.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<UserRegistry> userRegistryList = userRegistryRepository.findAll();
        assertThat(userRegistryList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
