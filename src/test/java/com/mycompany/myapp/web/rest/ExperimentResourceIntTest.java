package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.CeApp;

import com.mycompany.myapp.domain.Experiment;
import com.mycompany.myapp.repository.ExperimentRepository;
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
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;


import static com.mycompany.myapp.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ExperimentResource REST controller.
 *
 * @see ExperimentResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CeApp.class)
public class ExperimentResourceIntTest {

    private static final Instant DEFAULT_EXPERIMENT_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_EXPERIMENT_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_EXPERIMENT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_EXPERIMENT_TYPE = "BBBBBBBBBB";

    @Autowired
    private ExperimentRepository experimentRepository;

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

    private MockMvc restExperimentMockMvc;

    private Experiment experiment;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ExperimentResource experimentResource = new ExperimentResource(experimentRepository);
        this.restExperimentMockMvc = MockMvcBuilders.standaloneSetup(experimentResource)
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
    public static Experiment createEntity(EntityManager em) {
        Experiment experiment = new Experiment()
            .experimentTime(DEFAULT_EXPERIMENT_TIME)
            .experimentType(DEFAULT_EXPERIMENT_TYPE);
        return experiment;
    }

    @Before
    public void initTest() {
        experiment = createEntity(em);
    }

    @Test
    @Transactional
    public void createExperiment() throws Exception {
        int databaseSizeBeforeCreate = experimentRepository.findAll().size();

        // Create the Experiment
        restExperimentMockMvc.perform(post("/api/experiments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(experiment)))
            .andExpect(status().isCreated());

        // Validate the Experiment in the database
        List<Experiment> experimentList = experimentRepository.findAll();
        assertThat(experimentList).hasSize(databaseSizeBeforeCreate + 1);
        Experiment testExperiment = experimentList.get(experimentList.size() - 1);
        assertThat(testExperiment.getExperimentTime()).isEqualTo(DEFAULT_EXPERIMENT_TIME);
        assertThat(testExperiment.getExperimentType()).isEqualTo(DEFAULT_EXPERIMENT_TYPE);
    }

    @Test
    @Transactional
    public void createExperimentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = experimentRepository.findAll().size();

        // Create the Experiment with an existing ID
        experiment.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restExperimentMockMvc.perform(post("/api/experiments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(experiment)))
            .andExpect(status().isBadRequest());

        // Validate the Experiment in the database
        List<Experiment> experimentList = experimentRepository.findAll();
        assertThat(experimentList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkExperimentTimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = experimentRepository.findAll().size();
        // set the field null
        experiment.setExperimentTime(null);

        // Create the Experiment, which fails.

        restExperimentMockMvc.perform(post("/api/experiments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(experiment)))
            .andExpect(status().isBadRequest());

        List<Experiment> experimentList = experimentRepository.findAll();
        assertThat(experimentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkExperimentTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = experimentRepository.findAll().size();
        // set the field null
        experiment.setExperimentType(null);

        // Create the Experiment, which fails.

        restExperimentMockMvc.perform(post("/api/experiments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(experiment)))
            .andExpect(status().isBadRequest());

        List<Experiment> experimentList = experimentRepository.findAll();
        assertThat(experimentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllExperiments() throws Exception {
        // Initialize the database
        experimentRepository.saveAndFlush(experiment);

        // Get all the experimentList
        restExperimentMockMvc.perform(get("/api/experiments?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(experiment.getId().intValue())))
            .andExpect(jsonPath("$.[*].experimentTime").value(hasItem(DEFAULT_EXPERIMENT_TIME.toString())))
            .andExpect(jsonPath("$.[*].experimentType").value(hasItem(DEFAULT_EXPERIMENT_TYPE.toString())));
    }
    
    @Test
    @Transactional
    public void getExperiment() throws Exception {
        // Initialize the database
        experimentRepository.saveAndFlush(experiment);

        // Get the experiment
        restExperimentMockMvc.perform(get("/api/experiments/{id}", experiment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(experiment.getId().intValue()))
            .andExpect(jsonPath("$.experimentTime").value(DEFAULT_EXPERIMENT_TIME.toString()))
            .andExpect(jsonPath("$.experimentType").value(DEFAULT_EXPERIMENT_TYPE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingExperiment() throws Exception {
        // Get the experiment
        restExperimentMockMvc.perform(get("/api/experiments/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateExperiment() throws Exception {
        // Initialize the database
        experimentRepository.saveAndFlush(experiment);

        int databaseSizeBeforeUpdate = experimentRepository.findAll().size();

        // Update the experiment
        Experiment updatedExperiment = experimentRepository.findById(experiment.getId()).get();
        // Disconnect from session so that the updates on updatedExperiment are not directly saved in db
        em.detach(updatedExperiment);
        updatedExperiment
            .experimentTime(UPDATED_EXPERIMENT_TIME)
            .experimentType(UPDATED_EXPERIMENT_TYPE);

        restExperimentMockMvc.perform(put("/api/experiments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedExperiment)))
            .andExpect(status().isOk());

        // Validate the Experiment in the database
        List<Experiment> experimentList = experimentRepository.findAll();
        assertThat(experimentList).hasSize(databaseSizeBeforeUpdate);
        Experiment testExperiment = experimentList.get(experimentList.size() - 1);
        assertThat(testExperiment.getExperimentTime()).isEqualTo(UPDATED_EXPERIMENT_TIME);
        assertThat(testExperiment.getExperimentType()).isEqualTo(UPDATED_EXPERIMENT_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingExperiment() throws Exception {
        int databaseSizeBeforeUpdate = experimentRepository.findAll().size();

        // Create the Experiment

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restExperimentMockMvc.perform(put("/api/experiments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(experiment)))
            .andExpect(status().isBadRequest());

        // Validate the Experiment in the database
        List<Experiment> experimentList = experimentRepository.findAll();
        assertThat(experimentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteExperiment() throws Exception {
        // Initialize the database
        experimentRepository.saveAndFlush(experiment);

        int databaseSizeBeforeDelete = experimentRepository.findAll().size();

        // Delete the experiment
        restExperimentMockMvc.perform(delete("/api/experiments/{id}", experiment.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Experiment> experimentList = experimentRepository.findAll();
        assertThat(experimentList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Experiment.class);
        Experiment experiment1 = new Experiment();
        experiment1.setId(1L);
        Experiment experiment2 = new Experiment();
        experiment2.setId(experiment1.getId());
        assertThat(experiment1).isEqualTo(experiment2);
        experiment2.setId(2L);
        assertThat(experiment1).isNotEqualTo(experiment2);
        experiment1.setId(null);
        assertThat(experiment1).isNotEqualTo(experiment2);
    }
}
