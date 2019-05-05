package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.CeApp;

import com.mycompany.myapp.domain.AnalytesOfInterest;
import com.mycompany.myapp.repository.AnalytesOfInterestRepository;
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
 * Test class for the AnalytesOfInterestResource REST controller.
 *
 * @see AnalytesOfInterestResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CeApp.class)
public class AnalytesOfInterestResourceIntTest {

    @Autowired
    private AnalytesOfInterestRepository analytesOfInterestRepository;

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

    private MockMvc restAnalytesOfInterestMockMvc;

    private AnalytesOfInterest analytesOfInterest;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AnalytesOfInterestResource analytesOfInterestResource = new AnalytesOfInterestResource(analytesOfInterestRepository);
        this.restAnalytesOfInterestMockMvc = MockMvcBuilders.standaloneSetup(analytesOfInterestResource)
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
    public static AnalytesOfInterest createEntity(EntityManager em) {
        AnalytesOfInterest analytesOfInterest = new AnalytesOfInterest();
        return analytesOfInterest;
    }

    @Before
    public void initTest() {
        analytesOfInterest = createEntity(em);
    }

    @Test
    @Transactional
    public void createAnalytesOfInterest() throws Exception {
        int databaseSizeBeforeCreate = analytesOfInterestRepository.findAll().size();

        // Create the AnalytesOfInterest
        restAnalytesOfInterestMockMvc.perform(post("/api/analytes-of-interests")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(analytesOfInterest)))
            .andExpect(status().isCreated());

        // Validate the AnalytesOfInterest in the database
        List<AnalytesOfInterest> analytesOfInterestList = analytesOfInterestRepository.findAll();
        assertThat(analytesOfInterestList).hasSize(databaseSizeBeforeCreate + 1);
        AnalytesOfInterest testAnalytesOfInterest = analytesOfInterestList.get(analytesOfInterestList.size() - 1);
    }

    @Test
    @Transactional
    public void createAnalytesOfInterestWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = analytesOfInterestRepository.findAll().size();

        // Create the AnalytesOfInterest with an existing ID
        analytesOfInterest.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAnalytesOfInterestMockMvc.perform(post("/api/analytes-of-interests")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(analytesOfInterest)))
            .andExpect(status().isBadRequest());

        // Validate the AnalytesOfInterest in the database
        List<AnalytesOfInterest> analytesOfInterestList = analytesOfInterestRepository.findAll();
        assertThat(analytesOfInterestList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllAnalytesOfInterests() throws Exception {
        // Initialize the database
        analytesOfInterestRepository.saveAndFlush(analytesOfInterest);

        // Get all the analytesOfInterestList
        restAnalytesOfInterestMockMvc.perform(get("/api/analytes-of-interests?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(analytesOfInterest.getId().intValue())));
    }
    
    @Test
    @Transactional
    public void getAnalytesOfInterest() throws Exception {
        // Initialize the database
        analytesOfInterestRepository.saveAndFlush(analytesOfInterest);

        // Get the analytesOfInterest
        restAnalytesOfInterestMockMvc.perform(get("/api/analytes-of-interests/{id}", analytesOfInterest.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(analytesOfInterest.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingAnalytesOfInterest() throws Exception {
        // Get the analytesOfInterest
        restAnalytesOfInterestMockMvc.perform(get("/api/analytes-of-interests/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAnalytesOfInterest() throws Exception {
        // Initialize the database
        analytesOfInterestRepository.saveAndFlush(analytesOfInterest);

        int databaseSizeBeforeUpdate = analytesOfInterestRepository.findAll().size();

        // Update the analytesOfInterest
        AnalytesOfInterest updatedAnalytesOfInterest = analytesOfInterestRepository.findById(analytesOfInterest.getId()).get();
        // Disconnect from session so that the updates on updatedAnalytesOfInterest are not directly saved in db
        em.detach(updatedAnalytesOfInterest);

        restAnalytesOfInterestMockMvc.perform(put("/api/analytes-of-interests")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAnalytesOfInterest)))
            .andExpect(status().isOk());

        // Validate the AnalytesOfInterest in the database
        List<AnalytesOfInterest> analytesOfInterestList = analytesOfInterestRepository.findAll();
        assertThat(analytesOfInterestList).hasSize(databaseSizeBeforeUpdate);
        AnalytesOfInterest testAnalytesOfInterest = analytesOfInterestList.get(analytesOfInterestList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingAnalytesOfInterest() throws Exception {
        int databaseSizeBeforeUpdate = analytesOfInterestRepository.findAll().size();

        // Create the AnalytesOfInterest

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAnalytesOfInterestMockMvc.perform(put("/api/analytes-of-interests")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(analytesOfInterest)))
            .andExpect(status().isBadRequest());

        // Validate the AnalytesOfInterest in the database
        List<AnalytesOfInterest> analytesOfInterestList = analytesOfInterestRepository.findAll();
        assertThat(analytesOfInterestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAnalytesOfInterest() throws Exception {
        // Initialize the database
        analytesOfInterestRepository.saveAndFlush(analytesOfInterest);

        int databaseSizeBeforeDelete = analytesOfInterestRepository.findAll().size();

        // Delete the analytesOfInterest
        restAnalytesOfInterestMockMvc.perform(delete("/api/analytes-of-interests/{id}", analytesOfInterest.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<AnalytesOfInterest> analytesOfInterestList = analytesOfInterestRepository.findAll();
        assertThat(analytesOfInterestList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AnalytesOfInterest.class);
        AnalytesOfInterest analytesOfInterest1 = new AnalytesOfInterest();
        analytesOfInterest1.setId(1L);
        AnalytesOfInterest analytesOfInterest2 = new AnalytesOfInterest();
        analytesOfInterest2.setId(analytesOfInterest1.getId());
        assertThat(analytesOfInterest1).isEqualTo(analytesOfInterest2);
        analytesOfInterest2.setId(2L);
        assertThat(analytesOfInterest1).isNotEqualTo(analytesOfInterest2);
        analytesOfInterest1.setId(null);
        assertThat(analytesOfInterest1).isNotEqualTo(analytesOfInterest2);
    }
}
