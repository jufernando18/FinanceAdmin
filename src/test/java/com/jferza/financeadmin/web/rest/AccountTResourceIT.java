package com.jferza.financeadmin.web.rest;

import com.jferza.financeadmin.FinanceAdminApp;
import com.jferza.financeadmin.domain.AccountT;
import com.jferza.financeadmin.repository.AccountTRepository;
import com.jferza.financeadmin.service.AccountTService;
import com.jferza.financeadmin.service.dto.AccountTDTO;
import com.jferza.financeadmin.service.mapper.AccountTMapper;
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

import com.jferza.financeadmin.domain.enumeration.TransactionType;
import com.jferza.financeadmin.domain.enumeration.TransactionType;
/**
 * Integration tests for the {@link AccountTResource} REST controller.
 */
@SpringBootTest(classes = FinanceAdminApp.class)
public class AccountTResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Integer DEFAULT_CODE = 1;
    private static final Integer UPDATED_CODE = 2;

    private static final TransactionType DEFAULT_INCREASE_WHEN = TransactionType.DEBIT;
    private static final TransactionType UPDATED_INCREASE_WHEN = TransactionType.CREDIT;

    private static final TransactionType DEFAULT_DECREASE_WHEN = TransactionType.DEBIT;
    private static final TransactionType UPDATED_DECREASE_WHEN = TransactionType.CREDIT;

    private static final Integer DEFAULT_BALANCE = 1;
    private static final Integer UPDATED_BALANCE = 2;

    @Autowired
    private AccountTRepository accountTRepository;

    @Autowired
    private AccountTMapper accountTMapper;

    @Autowired
    private AccountTService accountTService;

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

    private MockMvc restAccountTMockMvc;

    private AccountT accountT;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AccountTResource accountTResource = new AccountTResource(accountTService);
        this.restAccountTMockMvc = MockMvcBuilders.standaloneSetup(accountTResource)
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
    public static AccountT createEntity(EntityManager em) {
        AccountT accountT = new AccountT()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .code(DEFAULT_CODE)
            .increaseWhen(DEFAULT_INCREASE_WHEN)
            .decreaseWhen(DEFAULT_DECREASE_WHEN)
            .balance(DEFAULT_BALANCE);
        return accountT;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AccountT createUpdatedEntity(EntityManager em) {
        AccountT accountT = new AccountT()
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .code(UPDATED_CODE)
            .increaseWhen(UPDATED_INCREASE_WHEN)
            .decreaseWhen(UPDATED_DECREASE_WHEN)
            .balance(UPDATED_BALANCE);
        return accountT;
    }

    @BeforeEach
    public void initTest() {
        accountT = createEntity(em);
    }

    @Test
    @Transactional
    public void createAccountT() throws Exception {
        int databaseSizeBeforeCreate = accountTRepository.findAll().size();

        // Create the AccountT
        AccountTDTO accountTDTO = accountTMapper.toDto(accountT);
        restAccountTMockMvc.perform(post("/api/account-ts")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(accountTDTO)))
            .andExpect(status().isCreated());

        // Validate the AccountT in the database
        List<AccountT> accountTList = accountTRepository.findAll();
        assertThat(accountTList).hasSize(databaseSizeBeforeCreate + 1);
        AccountT testAccountT = accountTList.get(accountTList.size() - 1);
        assertThat(testAccountT.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testAccountT.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testAccountT.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testAccountT.getIncreaseWhen()).isEqualTo(DEFAULT_INCREASE_WHEN);
        assertThat(testAccountT.getDecreaseWhen()).isEqualTo(DEFAULT_DECREASE_WHEN);
        assertThat(testAccountT.getBalance()).isEqualTo(DEFAULT_BALANCE);
    }

    @Test
    @Transactional
    public void createAccountTWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = accountTRepository.findAll().size();

        // Create the AccountT with an existing ID
        accountT.setId(1L);
        AccountTDTO accountTDTO = accountTMapper.toDto(accountT);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAccountTMockMvc.perform(post("/api/account-ts")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(accountTDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AccountT in the database
        List<AccountT> accountTList = accountTRepository.findAll();
        assertThat(accountTList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = accountTRepository.findAll().size();
        // set the field null
        accountT.setName(null);

        // Create the AccountT, which fails.
        AccountTDTO accountTDTO = accountTMapper.toDto(accountT);

        restAccountTMockMvc.perform(post("/api/account-ts")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(accountTDTO)))
            .andExpect(status().isBadRequest());

        List<AccountT> accountTList = accountTRepository.findAll();
        assertThat(accountTList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkBalanceIsRequired() throws Exception {
        int databaseSizeBeforeTest = accountTRepository.findAll().size();
        // set the field null
        accountT.setBalance(null);

        // Create the AccountT, which fails.
        AccountTDTO accountTDTO = accountTMapper.toDto(accountT);

        restAccountTMockMvc.perform(post("/api/account-ts")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(accountTDTO)))
            .andExpect(status().isBadRequest());

        List<AccountT> accountTList = accountTRepository.findAll();
        assertThat(accountTList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAccountTS() throws Exception {
        // Initialize the database
        accountTRepository.saveAndFlush(accountT);

        // Get all the accountTList
        restAccountTMockMvc.perform(get("/api/account-ts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(accountT.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].increaseWhen").value(hasItem(DEFAULT_INCREASE_WHEN.toString())))
            .andExpect(jsonPath("$.[*].decreaseWhen").value(hasItem(DEFAULT_DECREASE_WHEN.toString())))
            .andExpect(jsonPath("$.[*].balance").value(hasItem(DEFAULT_BALANCE)));
    }
    
    @Test
    @Transactional
    public void getAccountT() throws Exception {
        // Initialize the database
        accountTRepository.saveAndFlush(accountT);

        // Get the accountT
        restAccountTMockMvc.perform(get("/api/account-ts/{id}", accountT.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(accountT.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.increaseWhen").value(DEFAULT_INCREASE_WHEN.toString()))
            .andExpect(jsonPath("$.decreaseWhen").value(DEFAULT_DECREASE_WHEN.toString()))
            .andExpect(jsonPath("$.balance").value(DEFAULT_BALANCE));
    }

    @Test
    @Transactional
    public void getNonExistingAccountT() throws Exception {
        // Get the accountT
        restAccountTMockMvc.perform(get("/api/account-ts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAccountT() throws Exception {
        // Initialize the database
        accountTRepository.saveAndFlush(accountT);

        int databaseSizeBeforeUpdate = accountTRepository.findAll().size();

        // Update the accountT
        AccountT updatedAccountT = accountTRepository.findById(accountT.getId()).get();
        // Disconnect from session so that the updates on updatedAccountT are not directly saved in db
        em.detach(updatedAccountT);
        updatedAccountT
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .code(UPDATED_CODE)
            .increaseWhen(UPDATED_INCREASE_WHEN)
            .decreaseWhen(UPDATED_DECREASE_WHEN)
            .balance(UPDATED_BALANCE);
        AccountTDTO accountTDTO = accountTMapper.toDto(updatedAccountT);

        restAccountTMockMvc.perform(put("/api/account-ts")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(accountTDTO)))
            .andExpect(status().isOk());

        // Validate the AccountT in the database
        List<AccountT> accountTList = accountTRepository.findAll();
        assertThat(accountTList).hasSize(databaseSizeBeforeUpdate);
        AccountT testAccountT = accountTList.get(accountTList.size() - 1);
        assertThat(testAccountT.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testAccountT.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testAccountT.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testAccountT.getIncreaseWhen()).isEqualTo(UPDATED_INCREASE_WHEN);
        assertThat(testAccountT.getDecreaseWhen()).isEqualTo(UPDATED_DECREASE_WHEN);
        assertThat(testAccountT.getBalance()).isEqualTo(UPDATED_BALANCE);
    }

    @Test
    @Transactional
    public void updateNonExistingAccountT() throws Exception {
        int databaseSizeBeforeUpdate = accountTRepository.findAll().size();

        // Create the AccountT
        AccountTDTO accountTDTO = accountTMapper.toDto(accountT);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAccountTMockMvc.perform(put("/api/account-ts")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(accountTDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AccountT in the database
        List<AccountT> accountTList = accountTRepository.findAll();
        assertThat(accountTList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAccountT() throws Exception {
        // Initialize the database
        accountTRepository.saveAndFlush(accountT);

        int databaseSizeBeforeDelete = accountTRepository.findAll().size();

        // Delete the accountT
        restAccountTMockMvc.perform(delete("/api/account-ts/{id}", accountT.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AccountT> accountTList = accountTRepository.findAll();
        assertThat(accountTList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
