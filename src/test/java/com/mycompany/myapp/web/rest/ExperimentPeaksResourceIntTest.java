package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.CeApp;

import com.mycompany.myapp.domain.ExperimentPeaks;
import com.mycompany.myapp.repository.ExperimentPeaksRepository;
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
 * Test class for the ExperimentPeaksResource REST controller.
 *
 * @see ExperimentPeaksResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CeApp.class)
public class ExperimentPeaksResourceIntTest {

    private static final Long DEFAULT_PEAK_NUMBER = 1L;
    private static final Long UPDATED_PEAK_NUMBER = 2L;

    private static final Long DEFAULT_PEAK_START = 1L;
    private static final Long UPDATED_PEAK_START = 2L;

    private static final Long DEFAULT_PEAK_END = 1L;
    private static final Long UPDATED_PEAK_END = 2L;

    private static final Long DEFAULT_PEAK_HIGHEST = 1L;
    private static final Long UPDATED_PEAK_HIGHEST = 2L;

    private static final Float DEFAULT_PEAK_AREA = 1F;
    private static final Float UPDATED_PEAK_AREA = 2F;

    private static final Float DEFAULT_ANALYTE_CONCENTRATION = 1F;
    private static final Float UPDATED_ANALYTE_CONCENTRATION = 2F;

    @Autowired
    private ExperimentPeaksRepository experimentPeaksRepository;

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

    private MockMvc restExperimentPeaksMockMvc;

    private ExperimentPeaks experimentPeaks;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ExperimentPeaksResource experimentPeaksResource = new ExperimentPeaksResource(experimentPeaksRepository);
        this.restExperimentPeaksMockMvc = MockMvcBuilders.standaloneSetup(experimentPeaksResource)
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
    public static ExperimentPeaks createEntity(EntityManager em) {
        ExperimentPeaks experimentPeaks = new ExperimentPeaks()
            .peakNumber(DEFAULT_PEAK_NUMBER)
            .peakStart(DEFAULT_PEAK_START)
            .peakEnd(DEFAULT_PEAK_END)
            .peakHighest(DEFAULT_PEAK_HIGHEST)
            .peakArea(DEFAULT_PEAK_AREA)
            .analyteConcentration(DEFAULT_ANALYTE_CONCENTRATION);
        return experimentPeaks;
    }

    @Before
    public void initTest() {
        experimentPeaks = createEntity(em);
    }

    @Test
    @Transactional
    public void createExperimentPeaks() throws Exception {
        int databaseSizeBeforeCreate = experimentPeaksRepository.findAll().size();

        // Create the ExperimentPeaks
        restExperimentPeaksMockMvc.perform(post("/api/experiment-peaks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(experimentPeaks)))
            .andExpect(status().isCreated());

        // Validate the ExperimentPeaks in the database
        List<ExperimentPeaks> experimentPeaksList = experimentPeaksRepository.findAll();
        assertThat(experimentPeaksList).hasSize(databaseSizeBeforeCreate + 1);
        ExperimentPeaks testExperimentPeaks = experimentPeaksList.get(experimentPeaksList.size() - 1);
        assertThat(testExperimentPeaks.getPeakNumber()).isEqualTo(DEFAULT_PEAK_NUMBER);
        assertThat(testExperimentPeaks.getPeakStart()).isEqualTo(DEFAULT_PEAK_START);
        assertThat(testExperimentPeaks.getPeakEnd()).isEqualTo(DEFAULT_PEAK_END);
        assertThat(testExperimentPeaks.getPeakHighest()).isEqualTo(DEFAULT_PEAK_HIGHEST);
        assertThat(testExperimentPeaks.getPeakArea()).isEqualTo(DEFAULT_PEAK_AREA);
        assertThat(testExperimentPeaks.getAnalyteConcentration()).isEqualTo(DEFAULT_ANALYTE_CONCENTRATION);
    }

    @Test
    @Transactional
    public void createExperimentPeaksWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = experimentPeaksRepository.findAll().size();

        // Create the ExperimentPeaks with an existing ID
        experimentPeaks.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restExperimentPeaksMockMvc.perform(post("/api/experiment-peaks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(experimentPeaks)))
            .andExpect(status().isBadRequest());

        // Validate the ExperimentPeaks in the database
        List<ExperimentPeaks> experimentPeaksList = experimentPeaksRepository.findAll();
        assertThat(experimentPeaksList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllExperimentPeaks() throws Exception {
        // Initialize the database
        experimentPeaksRepository.saveAndFlush(experimentPeaks);

        // Get all the experimentPeaksList
        restExperimentPeaksMockMvc.perform(get("/api/experiment-peaks?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(experimentPeaks.getId().intValue())))
            .andExpect(jsonPath("$.[*].peakNumber").value(hasItem(DEFAULT_PEAK_NUMBER.intValue())))
            .andExpect(jsonPath("$.[*].peakStart").value(hasItem(DEFAULT_PEAK_START.intValue())))
            .andExpect(jsonPath("$.[*].peakEnd").value(hasItem(DEFAULT_PEAK_END.intValue())))
            .andExpect(jsonPath("$.[*].peakHighest").value(hasItem(DEFAULT_PEAK_HIGHEST.intValue())))
            .andExpect(jsonPath("$.[*].peakArea").value(hasItem(DEFAULT_PEAK_AREA.doubleValue())))
            .andExpect(jsonPath("$.[*].analyteConcentration").value(hasItem(DEFAULT_ANALYTE_CONCENTRATION.doubleValue())));
    }
    
    @Test
    @Transactional
    public void getExperimentPeaks() throws Exception {
        // Initialize the database
        experimentPeaksRepository.saveAndFlush(experimentPeaks);

        // Get the experimentPeaks
        restExperimentPeaksMockMvc.perform(get("/api/experiment-peaks/{id}", experimentPeaks.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(experimentPeaks.getId().intValue()))
            .andExpect(jsonPath("$.peakNumber").value(DEFAULT_PEAK_NUMBER.intValue()))
            .andExpect(jsonPath("$.peakStart").value(DEFAULT_PEAK_START.intValue()))
            .andExpect(jsonPath("$.peakEnd").value(DEFAULT_PEAK_END.intValue()))
            .andExpect(jsonPath("$.peakHighest").value(DEFAULT_PEAK_HIGHEST.intValue()))
            .andExpect(jsonPath("$.peakArea").value(DEFAULT_PEAK_AREA.doubleValue()))
            .andExpect(jsonPath("$.analyteConcentration").value(DEFAULT_ANALYTE_CONCENTRATION.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingExperimentPeaks() throws Exception {
        // Get the experimentPeaks
        restExperimentPeaksMockMvc.perform(get("/api/experiment-peaks/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateExperimentPeaks() throws Exception {
        // Initialize the database
        experimentPeaksRepository.saveAndFlush(experimentPeaks);

        int databaseSizeBeforeUpdate = experimentPeaksRepository.findAll().size();

        // Update the experimentPeaks
        ExperimentPeaks updatedExperimentPeaks = experimentPeaksRepository.findById(experimentPeaks.getId()).get();
        // Disconnect from session so that the updates on updatedExperimentPeaks are not directly saved in db
        em.detach(updatedExperimentPeaks);
        updatedExperimentPeaks
            .peakNumber(UPDATED_PEAK_NUMBER)
            .peakStart(UPDATED_PEAK_START)
            .peakEnd(UPDATED_PEAK_END)
            .peakHighest(UPDATED_PEAK_HIGHEST)
            .peakArea(UPDATED_PEAK_AREA)
            .analyteConcentration(UPDATED_ANALYTE_CONCENTRATION);

        restExperimentPeaksMockMvc.perform(put("/api/experiment-peaks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedExperimentPeaks)))
            .andExpect(status().isOk());

        // Validate the ExperimentPeaks in the database
        List<ExperimentPeaks> experimentPeaksList = experimentPeaksRepository.findAll();
        assertThat(experimentPeaksList).hasSize(databaseSizeBeforeUpdate);
        ExperimentPeaks testExperimentPeaks = experimentPeaksList.get(experimentPeaksList.size() - 1);
        assertThat(testExperimentPeaks.getPeakNumber()).isEqualTo(UPDATED_PEAK_NUMBER);
        assertThat(testExperimentPeaks.getPeakStart()).isEqualTo(UPDATED_PEAK_START);
        assertThat(testExperimentPeaks.getPeakEnd()).isEqualTo(UPDATED_PEAK_END);
        assertThat(testExperimentPeaks.getPeakHighest()).isEqualTo(UPDATED_PEAK_HIGHEST);
        assertThat(testExperimentPeaks.getPeakArea()).isEqualTo(UPDATED_PEAK_AREA);
        assertThat(testExperimentPeaks.getAnalyteConcentration()).isEqualTo(UPDATED_ANALYTE_CONCENTRATION);
    }

    @Test
    @Transactional
    public void updateNonExistingExperimentPeaks() throws Exception {
        int databaseSizeBeforeUpdate = experimentPeaksRepository.findAll().size();

        // Create the ExperimentPeaks

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restExperimentPeaksMockMvc.perform(put("/api/experiment-peaks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(experimentPeaks)))
            .andExpect(status().isBadRequest());

        // Validate the ExperimentPeaks in the database
        List<ExperimentPeaks> experimentPeaksList = experimentPeaksRepository.findAll();
        assertThat(experimentPeaksList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteExperimentPeaks() throws Exception {
        // Initialize the database
        experimentPeaksRepository.saveAndFlush(experimentPeaks);

        int databaseSizeBeforeDelete = experimentPeaksRepository.findAll().size();

        // Delete the experimentPeaks
        restExperimentPeaksMockMvc.perform(delete("/api/experiment-peaks/{id}", experimentPeaks.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ExperimentPeaks> experimentPeaksList = experimentPeaksRepository.findAll();
        assertThat(experimentPeaksList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ExperimentPeaks.class);
        ExperimentPeaks experimentPeaks1 = new ExperimentPeaks();
        experimentPeaks1.setId(1L);
        ExperimentPeaks experimentPeaks2 = new ExperimentPeaks();
        experimentPeaks2.setId(experimentPeaks1.getId());
        assertThat(experimentPeaks1).isEqualTo(experimentPeaks2);
        experimentPeaks2.setId(2L);
        assertThat(experimentPeaks1).isNotEqualTo(experimentPeaks2);
        experimentPeaks1.setId(null);
        assertThat(experimentPeaks1).isNotEqualTo(experimentPeaks2);
    }
}
