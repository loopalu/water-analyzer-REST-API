package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.CeApp;

import com.mycompany.myapp.domain.ExperimentResults;
import com.mycompany.myapp.repository.ExperimentResultsRepository;
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
 * Test class for the ExperimentResultsResource REST controller.
 *
 * @see ExperimentResultsResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CeApp.class)
public class ExperimentResultsResourceIntTest {

    private static final Long DEFAULT_MEASURING_POINT = 1L;
    private static final Long UPDATED_MEASURING_POINT = 2L;

    private static final Long DEFAULT_VOLTAGE_VALUE = 1L;
    private static final Long UPDATED_VOLTAGE_VALUE = 2L;

    @Autowired
    private ExperimentResultsRepository experimentResultsRepository;

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

    private MockMvc restExperimentResultsMockMvc;

    private ExperimentResults experimentResults;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ExperimentResultsResource experimentResultsResource = new ExperimentResultsResource(experimentResultsRepository);
        this.restExperimentResultsMockMvc = MockMvcBuilders.standaloneSetup(experimentResultsResource)
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
    public static ExperimentResults createEntity(EntityManager em) {
        ExperimentResults experimentResults = new ExperimentResults()
            .measuringPoint(DEFAULT_MEASURING_POINT)
            .voltageValue(DEFAULT_VOLTAGE_VALUE);
        return experimentResults;
    }

    @Before
    public void initTest() {
        experimentResults = createEntity(em);
    }

    @Test
    @Transactional
    public void createExperimentResults() throws Exception {
        int databaseSizeBeforeCreate = experimentResultsRepository.findAll().size();

        // Create the ExperimentResults
        restExperimentResultsMockMvc.perform(post("/api/experiment-results")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(experimentResults)))
            .andExpect(status().isCreated());

        // Validate the ExperimentResults in the database
        List<ExperimentResults> experimentResultsList = experimentResultsRepository.findAll();
        assertThat(experimentResultsList).hasSize(databaseSizeBeforeCreate + 1);
        ExperimentResults testExperimentResults = experimentResultsList.get(experimentResultsList.size() - 1);
        assertThat(testExperimentResults.getMeasuringPoint()).isEqualTo(DEFAULT_MEASURING_POINT);
        assertThat(testExperimentResults.getVoltageValue()).isEqualTo(DEFAULT_VOLTAGE_VALUE);
    }

    @Test
    @Transactional
    public void createExperimentResultsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = experimentResultsRepository.findAll().size();

        // Create the ExperimentResults with an existing ID
        experimentResults.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restExperimentResultsMockMvc.perform(post("/api/experiment-results")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(experimentResults)))
            .andExpect(status().isBadRequest());

        // Validate the ExperimentResults in the database
        List<ExperimentResults> experimentResultsList = experimentResultsRepository.findAll();
        assertThat(experimentResultsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllExperimentResults() throws Exception {
        // Initialize the database
        experimentResultsRepository.saveAndFlush(experimentResults);

        // Get all the experimentResultsList
        restExperimentResultsMockMvc.perform(get("/api/experiment-results?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(experimentResults.getId().intValue())))
            .andExpect(jsonPath("$.[*].measuringPoint").value(hasItem(DEFAULT_MEASURING_POINT.intValue())))
            .andExpect(jsonPath("$.[*].voltageValue").value(hasItem(DEFAULT_VOLTAGE_VALUE.intValue())));
    }
    
    @Test
    @Transactional
    public void getExperimentResults() throws Exception {
        // Initialize the database
        experimentResultsRepository.saveAndFlush(experimentResults);

        // Get the experimentResults
        restExperimentResultsMockMvc.perform(get("/api/experiment-results/{id}", experimentResults.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(experimentResults.getId().intValue()))
            .andExpect(jsonPath("$.measuringPoint").value(DEFAULT_MEASURING_POINT.intValue()))
            .andExpect(jsonPath("$.voltageValue").value(DEFAULT_VOLTAGE_VALUE.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingExperimentResults() throws Exception {
        // Get the experimentResults
        restExperimentResultsMockMvc.perform(get("/api/experiment-results/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateExperimentResults() throws Exception {
        // Initialize the database
        experimentResultsRepository.saveAndFlush(experimentResults);

        int databaseSizeBeforeUpdate = experimentResultsRepository.findAll().size();

        // Update the experimentResults
        ExperimentResults updatedExperimentResults = experimentResultsRepository.findById(experimentResults.getId()).get();
        // Disconnect from session so that the updates on updatedExperimentResults are not directly saved in db
        em.detach(updatedExperimentResults);
        updatedExperimentResults
            .measuringPoint(UPDATED_MEASURING_POINT)
            .voltageValue(UPDATED_VOLTAGE_VALUE);

        restExperimentResultsMockMvc.perform(put("/api/experiment-results")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedExperimentResults)))
            .andExpect(status().isOk());

        // Validate the ExperimentResults in the database
        List<ExperimentResults> experimentResultsList = experimentResultsRepository.findAll();
        assertThat(experimentResultsList).hasSize(databaseSizeBeforeUpdate);
        ExperimentResults testExperimentResults = experimentResultsList.get(experimentResultsList.size() - 1);
        assertThat(testExperimentResults.getMeasuringPoint()).isEqualTo(UPDATED_MEASURING_POINT);
        assertThat(testExperimentResults.getVoltageValue()).isEqualTo(UPDATED_VOLTAGE_VALUE);
    }

    @Test
    @Transactional
    public void updateNonExistingExperimentResults() throws Exception {
        int databaseSizeBeforeUpdate = experimentResultsRepository.findAll().size();

        // Create the ExperimentResults

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restExperimentResultsMockMvc.perform(put("/api/experiment-results")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(experimentResults)))
            .andExpect(status().isBadRequest());

        // Validate the ExperimentResults in the database
        List<ExperimentResults> experimentResultsList = experimentResultsRepository.findAll();
        assertThat(experimentResultsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteExperimentResults() throws Exception {
        // Initialize the database
        experimentResultsRepository.saveAndFlush(experimentResults);

        int databaseSizeBeforeDelete = experimentResultsRepository.findAll().size();

        // Delete the experimentResults
        restExperimentResultsMockMvc.perform(delete("/api/experiment-results/{id}", experimentResults.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ExperimentResults> experimentResultsList = experimentResultsRepository.findAll();
        assertThat(experimentResultsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ExperimentResults.class);
        ExperimentResults experimentResults1 = new ExperimentResults();
        experimentResults1.setId(1L);
        ExperimentResults experimentResults2 = new ExperimentResults();
        experimentResults2.setId(experimentResults1.getId());
        assertThat(experimentResults1).isEqualTo(experimentResults2);
        experimentResults2.setId(2L);
        assertThat(experimentResults1).isNotEqualTo(experimentResults2);
        experimentResults1.setId(null);
        assertThat(experimentResults1).isNotEqualTo(experimentResults2);
    }
}
