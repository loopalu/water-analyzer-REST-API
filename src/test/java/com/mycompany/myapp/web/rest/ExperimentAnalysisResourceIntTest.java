package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.CeApp;

import com.mycompany.myapp.domain.ExperimentAnalysis;
import com.mycompany.myapp.repository.ExperimentAnalysisRepository;
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
 * Test class for the ExperimentAnalysisResource REST controller.
 *
 * @see ExperimentAnalysisResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CeApp.class)
public class ExperimentAnalysisResourceIntTest {

    private static final Long DEFAULT_MEASURING_POINT = 1L;
    private static final Long UPDATED_MEASURING_POINT = 2L;

    private static final Long DEFAULT_VOLTAGE_EXPERIMENT = 1L;
    private static final Long UPDATED_VOLTAGE_EXPERIMENT = 2L;

    private static final Long DEFAULT_VOLTAGE_SMOOTHED = 1L;
    private static final Long UPDATED_VOLTAGE_SMOOTHED = 2L;

    private static final Long DEFAULT_VALUE_MOVING_AVERAGE_SUBTRACTED = 1L;
    private static final Long UPDATED_VALUE_MOVING_AVERAGE_SUBTRACTED = 2L;

    private static final Long DEFAULT_VALUE_OVER_THRESHOLD = 1L;
    private static final Long UPDATED_VALUE_OVER_THRESHOLD = 2L;

    @Autowired
    private ExperimentAnalysisRepository experimentAnalysisRepository;

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

    private MockMvc restExperimentAnalysisMockMvc;

    private ExperimentAnalysis experimentAnalysis;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ExperimentAnalysisResource experimentAnalysisResource = new ExperimentAnalysisResource(experimentAnalysisRepository);
        this.restExperimentAnalysisMockMvc = MockMvcBuilders.standaloneSetup(experimentAnalysisResource)
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
    public static ExperimentAnalysis createEntity(EntityManager em) {
        ExperimentAnalysis experimentAnalysis = new ExperimentAnalysis()
            .measuringPoint(DEFAULT_MEASURING_POINT)
            .voltageExperiment(DEFAULT_VOLTAGE_EXPERIMENT)
            .voltageSmoothed(DEFAULT_VOLTAGE_SMOOTHED)
            .valueMovingAverageSubtracted(DEFAULT_VALUE_MOVING_AVERAGE_SUBTRACTED)
            .valueOverThreshold(DEFAULT_VALUE_OVER_THRESHOLD);
        return experimentAnalysis;
    }

    @Before
    public void initTest() {
        experimentAnalysis = createEntity(em);
    }

    @Test
    @Transactional
    public void createExperimentAnalysis() throws Exception {
        int databaseSizeBeforeCreate = experimentAnalysisRepository.findAll().size();

        // Create the ExperimentAnalysis
        restExperimentAnalysisMockMvc.perform(post("/api/experiment-analyses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(experimentAnalysis)))
            .andExpect(status().isCreated());

        // Validate the ExperimentAnalysis in the database
        List<ExperimentAnalysis> experimentAnalysisList = experimentAnalysisRepository.findAll();
        assertThat(experimentAnalysisList).hasSize(databaseSizeBeforeCreate + 1);
        ExperimentAnalysis testExperimentAnalysis = experimentAnalysisList.get(experimentAnalysisList.size() - 1);
        assertThat(testExperimentAnalysis.getMeasuringPoint()).isEqualTo(DEFAULT_MEASURING_POINT);
        assertThat(testExperimentAnalysis.getVoltageExperiment()).isEqualTo(DEFAULT_VOLTAGE_EXPERIMENT);
        assertThat(testExperimentAnalysis.getVoltageSmoothed()).isEqualTo(DEFAULT_VOLTAGE_SMOOTHED);
        assertThat(testExperimentAnalysis.getValueMovingAverageSubtracted()).isEqualTo(DEFAULT_VALUE_MOVING_AVERAGE_SUBTRACTED);
        assertThat(testExperimentAnalysis.getValueOverThreshold()).isEqualTo(DEFAULT_VALUE_OVER_THRESHOLD);
    }

    @Test
    @Transactional
    public void createExperimentAnalysisWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = experimentAnalysisRepository.findAll().size();

        // Create the ExperimentAnalysis with an existing ID
        experimentAnalysis.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restExperimentAnalysisMockMvc.perform(post("/api/experiment-analyses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(experimentAnalysis)))
            .andExpect(status().isBadRequest());

        // Validate the ExperimentAnalysis in the database
        List<ExperimentAnalysis> experimentAnalysisList = experimentAnalysisRepository.findAll();
        assertThat(experimentAnalysisList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkMeasuringPointIsRequired() throws Exception {
        int databaseSizeBeforeTest = experimentAnalysisRepository.findAll().size();
        // set the field null
        experimentAnalysis.setMeasuringPoint(null);

        // Create the ExperimentAnalysis, which fails.

        restExperimentAnalysisMockMvc.perform(post("/api/experiment-analyses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(experimentAnalysis)))
            .andExpect(status().isBadRequest());

        List<ExperimentAnalysis> experimentAnalysisList = experimentAnalysisRepository.findAll();
        assertThat(experimentAnalysisList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkVoltageExperimentIsRequired() throws Exception {
        int databaseSizeBeforeTest = experimentAnalysisRepository.findAll().size();
        // set the field null
        experimentAnalysis.setVoltageExperiment(null);

        // Create the ExperimentAnalysis, which fails.

        restExperimentAnalysisMockMvc.perform(post("/api/experiment-analyses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(experimentAnalysis)))
            .andExpect(status().isBadRequest());

        List<ExperimentAnalysis> experimentAnalysisList = experimentAnalysisRepository.findAll();
        assertThat(experimentAnalysisList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkVoltageSmoothedIsRequired() throws Exception {
        int databaseSizeBeforeTest = experimentAnalysisRepository.findAll().size();
        // set the field null
        experimentAnalysis.setVoltageSmoothed(null);

        // Create the ExperimentAnalysis, which fails.

        restExperimentAnalysisMockMvc.perform(post("/api/experiment-analyses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(experimentAnalysis)))
            .andExpect(status().isBadRequest());

        List<ExperimentAnalysis> experimentAnalysisList = experimentAnalysisRepository.findAll();
        assertThat(experimentAnalysisList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkValueMovingAverageSubtractedIsRequired() throws Exception {
        int databaseSizeBeforeTest = experimentAnalysisRepository.findAll().size();
        // set the field null
        experimentAnalysis.setValueMovingAverageSubtracted(null);

        // Create the ExperimentAnalysis, which fails.

        restExperimentAnalysisMockMvc.perform(post("/api/experiment-analyses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(experimentAnalysis)))
            .andExpect(status().isBadRequest());

        List<ExperimentAnalysis> experimentAnalysisList = experimentAnalysisRepository.findAll();
        assertThat(experimentAnalysisList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkValueOverThresholdIsRequired() throws Exception {
        int databaseSizeBeforeTest = experimentAnalysisRepository.findAll().size();
        // set the field null
        experimentAnalysis.setValueOverThreshold(null);

        // Create the ExperimentAnalysis, which fails.

        restExperimentAnalysisMockMvc.perform(post("/api/experiment-analyses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(experimentAnalysis)))
            .andExpect(status().isBadRequest());

        List<ExperimentAnalysis> experimentAnalysisList = experimentAnalysisRepository.findAll();
        assertThat(experimentAnalysisList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllExperimentAnalyses() throws Exception {
        // Initialize the database
        experimentAnalysisRepository.saveAndFlush(experimentAnalysis);

        // Get all the experimentAnalysisList
        restExperimentAnalysisMockMvc.perform(get("/api/experiment-analyses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(experimentAnalysis.getId().intValue())))
            .andExpect(jsonPath("$.[*].measuringPoint").value(hasItem(DEFAULT_MEASURING_POINT.intValue())))
            .andExpect(jsonPath("$.[*].voltageExperiment").value(hasItem(DEFAULT_VOLTAGE_EXPERIMENT.intValue())))
            .andExpect(jsonPath("$.[*].voltageSmoothed").value(hasItem(DEFAULT_VOLTAGE_SMOOTHED.intValue())))
            .andExpect(jsonPath("$.[*].valueMovingAverageSubtracted").value(hasItem(DEFAULT_VALUE_MOVING_AVERAGE_SUBTRACTED.intValue())))
            .andExpect(jsonPath("$.[*].valueOverThreshold").value(hasItem(DEFAULT_VALUE_OVER_THRESHOLD.intValue())));
    }
    
    @Test
    @Transactional
    public void getExperimentAnalysis() throws Exception {
        // Initialize the database
        experimentAnalysisRepository.saveAndFlush(experimentAnalysis);

        // Get the experimentAnalysis
        restExperimentAnalysisMockMvc.perform(get("/api/experiment-analyses/{id}", experimentAnalysis.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(experimentAnalysis.getId().intValue()))
            .andExpect(jsonPath("$.measuringPoint").value(DEFAULT_MEASURING_POINT.intValue()))
            .andExpect(jsonPath("$.voltageExperiment").value(DEFAULT_VOLTAGE_EXPERIMENT.intValue()))
            .andExpect(jsonPath("$.voltageSmoothed").value(DEFAULT_VOLTAGE_SMOOTHED.intValue()))
            .andExpect(jsonPath("$.valueMovingAverageSubtracted").value(DEFAULT_VALUE_MOVING_AVERAGE_SUBTRACTED.intValue()))
            .andExpect(jsonPath("$.valueOverThreshold").value(DEFAULT_VALUE_OVER_THRESHOLD.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingExperimentAnalysis() throws Exception {
        // Get the experimentAnalysis
        restExperimentAnalysisMockMvc.perform(get("/api/experiment-analyses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateExperimentAnalysis() throws Exception {
        // Initialize the database
        experimentAnalysisRepository.saveAndFlush(experimentAnalysis);

        int databaseSizeBeforeUpdate = experimentAnalysisRepository.findAll().size();

        // Update the experimentAnalysis
        ExperimentAnalysis updatedExperimentAnalysis = experimentAnalysisRepository.findById(experimentAnalysis.getId()).get();
        // Disconnect from session so that the updates on updatedExperimentAnalysis are not directly saved in db
        em.detach(updatedExperimentAnalysis);
        updatedExperimentAnalysis
            .measuringPoint(UPDATED_MEASURING_POINT)
            .voltageExperiment(UPDATED_VOLTAGE_EXPERIMENT)
            .voltageSmoothed(UPDATED_VOLTAGE_SMOOTHED)
            .valueMovingAverageSubtracted(UPDATED_VALUE_MOVING_AVERAGE_SUBTRACTED)
            .valueOverThreshold(UPDATED_VALUE_OVER_THRESHOLD);

        restExperimentAnalysisMockMvc.perform(put("/api/experiment-analyses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedExperimentAnalysis)))
            .andExpect(status().isOk());

        // Validate the ExperimentAnalysis in the database
        List<ExperimentAnalysis> experimentAnalysisList = experimentAnalysisRepository.findAll();
        assertThat(experimentAnalysisList).hasSize(databaseSizeBeforeUpdate);
        ExperimentAnalysis testExperimentAnalysis = experimentAnalysisList.get(experimentAnalysisList.size() - 1);
        assertThat(testExperimentAnalysis.getMeasuringPoint()).isEqualTo(UPDATED_MEASURING_POINT);
        assertThat(testExperimentAnalysis.getVoltageExperiment()).isEqualTo(UPDATED_VOLTAGE_EXPERIMENT);
        assertThat(testExperimentAnalysis.getVoltageSmoothed()).isEqualTo(UPDATED_VOLTAGE_SMOOTHED);
        assertThat(testExperimentAnalysis.getValueMovingAverageSubtracted()).isEqualTo(UPDATED_VALUE_MOVING_AVERAGE_SUBTRACTED);
        assertThat(testExperimentAnalysis.getValueOverThreshold()).isEqualTo(UPDATED_VALUE_OVER_THRESHOLD);
    }

    @Test
    @Transactional
    public void updateNonExistingExperimentAnalysis() throws Exception {
        int databaseSizeBeforeUpdate = experimentAnalysisRepository.findAll().size();

        // Create the ExperimentAnalysis

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restExperimentAnalysisMockMvc.perform(put("/api/experiment-analyses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(experimentAnalysis)))
            .andExpect(status().isBadRequest());

        // Validate the ExperimentAnalysis in the database
        List<ExperimentAnalysis> experimentAnalysisList = experimentAnalysisRepository.findAll();
        assertThat(experimentAnalysisList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteExperimentAnalysis() throws Exception {
        // Initialize the database
        experimentAnalysisRepository.saveAndFlush(experimentAnalysis);

        int databaseSizeBeforeDelete = experimentAnalysisRepository.findAll().size();

        // Delete the experimentAnalysis
        restExperimentAnalysisMockMvc.perform(delete("/api/experiment-analyses/{id}", experimentAnalysis.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ExperimentAnalysis> experimentAnalysisList = experimentAnalysisRepository.findAll();
        assertThat(experimentAnalysisList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ExperimentAnalysis.class);
        ExperimentAnalysis experimentAnalysis1 = new ExperimentAnalysis();
        experimentAnalysis1.setId(1L);
        ExperimentAnalysis experimentAnalysis2 = new ExperimentAnalysis();
        experimentAnalysis2.setId(experimentAnalysis1.getId());
        assertThat(experimentAnalysis1).isEqualTo(experimentAnalysis2);
        experimentAnalysis2.setId(2L);
        assertThat(experimentAnalysis1).isNotEqualTo(experimentAnalysis2);
        experimentAnalysis1.setId(null);
        assertThat(experimentAnalysis1).isNotEqualTo(experimentAnalysis2);
    }
}
