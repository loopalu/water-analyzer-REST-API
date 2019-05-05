package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.CeApp;

import com.mycompany.myapp.domain.Analyte;
import com.mycompany.myapp.repository.AnalyteRepository;
import com.mycompany.myapp.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;


import static com.mycompany.myapp.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the AnalyteResource REST controller.
 *
 * @see AnalyteResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CeApp.class)
public class AnalyteResourceIntTest {

    private static final String DEFAULT_ANALYTE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_ANALYTE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ANALYTE_GROUP = "AAAAAAAAAA";
    private static final String UPDATED_ANALYTE_GROUP = "BBBBBBBBBB";

    @Autowired
    private AnalyteRepository analyteRepository;

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

    private MockMvc restAnalyteMockMvc;

    private Analyte analyte;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AnalyteResource analyteResource = new AnalyteResource(analyteRepository);
        this.restAnalyteMockMvc = MockMvcBuilders.standaloneSetup(analyteResource)
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
    public static Analyte createEntity(EntityManager em) {
        Analyte analyte = new Analyte()
            .analyteName(DEFAULT_ANALYTE_NAME)
            .analyteGroup(DEFAULT_ANALYTE_GROUP);
        return analyte;
    }

    @Before
    public void initTest() {
        analyte = createEntity(em);
    }

    @Test
    @Transactional
    public void createAnalyte() throws Exception {
        int databaseSizeBeforeCreate = analyteRepository.findAll().size();

        // Create the Analyte
        restAnalyteMockMvc.perform(post("/api/analytes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(analyte)))
            .andExpect(status().isCreated());

        // Validate the Analyte in the database
        List<Analyte> analyteList = analyteRepository.findAll();
        assertThat(analyteList).hasSize(databaseSizeBeforeCreate + 1);
        Analyte testAnalyte = analyteList.get(analyteList.size() - 1);
        assertThat(testAnalyte.getAnalyteName()).isEqualTo(DEFAULT_ANALYTE_NAME);
        assertThat(testAnalyte.getAnalyteGroup()).isEqualTo(DEFAULT_ANALYTE_GROUP);
    }

    @Test
    @Transactional
    public void createAnalyteWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = analyteRepository.findAll().size();

        // Create the Analyte with an existing ID
        analyte.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAnalyteMockMvc.perform(post("/api/analytes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(analyte)))
            .andExpect(status().isBadRequest());

        // Validate the Analyte in the database
        List<Analyte> analyteList = analyteRepository.findAll();
        assertThat(analyteList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkAnalyteNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = analyteRepository.findAll().size();
        // set the field null
        analyte.setAnalyteName(null);

        // Create the Analyte, which fails.

        restAnalyteMockMvc.perform(post("/api/analytes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(analyte)))
            .andExpect(status().isBadRequest());

        List<Analyte> analyteList = analyteRepository.findAll();
        assertThat(analyteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAnalyteGroupIsRequired() throws Exception {
        int databaseSizeBeforeTest = analyteRepository.findAll().size();
        // set the field null
        analyte.setAnalyteGroup(null);

        // Create the Analyte, which fails.

        restAnalyteMockMvc.perform(post("/api/analytes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(analyte)))
            .andExpect(status().isBadRequest());

        List<Analyte> analyteList = analyteRepository.findAll();
        assertThat(analyteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAnalytes() throws Exception {
        // Initialize the database
        analyteRepository.saveAndFlush(analyte);

        // Get all the analyteList
        restAnalyteMockMvc.perform(get("/api/analytes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(analyte.getId().intValue())))
            .andExpect(jsonPath("$.[*].analyteName").value(hasItem(DEFAULT_ANALYTE_NAME.toString())))
            .andExpect(jsonPath("$.[*].analyteGroup").value(hasItem(DEFAULT_ANALYTE_GROUP.toString())));
    }
    
    @Test
    @Transactional
    public void getAnalyte() throws Exception {
        // Initialize the database
        analyteRepository.saveAndFlush(analyte);

        // Get the analyte
        restAnalyteMockMvc.perform(get("/api/analytes/{id}", analyte.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(analyte.getId().intValue()))
            .andExpect(jsonPath("$.analyteName").value(DEFAULT_ANALYTE_NAME.toString()))
            .andExpect(jsonPath("$.analyteGroup").value(DEFAULT_ANALYTE_GROUP.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAnalyte() throws Exception {
        // Get the analyte
        restAnalyteMockMvc.perform(get("/api/analytes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAnalyte() throws Exception {
        // Initialize the database
        analyteRepository.saveAndFlush(analyte);

        int databaseSizeBeforeUpdate = analyteRepository.findAll().size();

        // Update the analyte
        Analyte updatedAnalyte = analyteRepository.findById(analyte.getId()).get();
        // Disconnect from session so that the updates on updatedAnalyte are not directly saved in db
        em.detach(updatedAnalyte);
        updatedAnalyte
            .analyteName(UPDATED_ANALYTE_NAME)
            .analyteGroup(UPDATED_ANALYTE_GROUP);

        restAnalyteMockMvc.perform(put("/api/analytes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAnalyte)))
            .andExpect(status().isOk());

        // Validate the Analyte in the database
        List<Analyte> analyteList = analyteRepository.findAll();
        assertThat(analyteList).hasSize(databaseSizeBeforeUpdate);
        Analyte testAnalyte = analyteList.get(analyteList.size() - 1);
        assertThat(testAnalyte.getAnalyteName()).isEqualTo(UPDATED_ANALYTE_NAME);
        assertThat(testAnalyte.getAnalyteGroup()).isEqualTo(UPDATED_ANALYTE_GROUP);
    }

    @Test
    @Transactional
    public void updateNonExistingAnalyte() throws Exception {
        int databaseSizeBeforeUpdate = analyteRepository.findAll().size();

        // Create the Analyte

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAnalyteMockMvc.perform(put("/api/analytes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(analyte)))
            .andExpect(status().isBadRequest());

        // Validate the Analyte in the database
        List<Analyte> analyteList = analyteRepository.findAll();
        assertThat(analyteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAnalyte() throws Exception {
        // Initialize the database
        analyteRepository.saveAndFlush(analyte);

        int databaseSizeBeforeDelete = analyteRepository.findAll().size();

        // Delete the analyte
        restAnalyteMockMvc.perform(delete("/api/analytes/{id}", analyte.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Analyte> analyteList = analyteRepository.findAll();
        assertThat(analyteList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Analyte.class);
        Analyte analyte1 = new Analyte();
        analyte1.setId(1L);
        Analyte analyte2 = new Analyte();
        analyte2.setId(analyte1.getId());
        assertThat(analyte1).isEqualTo(analyte2);
        analyte2.setId(2L);
        assertThat(analyte1).isNotEqualTo(analyte2);
        analyte1.setId(null);
        assertThat(analyte1).isNotEqualTo(analyte2);
    }
}
