package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.CeApp;

import com.mycompany.myapp.domain.BGEComposition;
import com.mycompany.myapp.repository.BGECompositionRepository;
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
 * Test class for the BGECompositionResource REST controller.
 *
 * @see BGECompositionResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CeApp.class)
public class BGECompositionResourceIntTest {

    private static final String DEFAULT_BACKGROUND_ELECTROLYTE = "AAAAAAAAAA";
    private static final String UPDATED_BACKGROUND_ELECTROLYTE = "BBBBBBBBBB";

    private static final Long DEFAULT_BGE_CONCENTRATION = 1L;
    private static final Long UPDATED_BGE_CONCENTRATION = 2L;

    @Autowired
    private BGECompositionRepository bGECompositionRepository;

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

    private MockMvc restBGECompositionMockMvc;

    private BGEComposition bGEComposition;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final BGECompositionResource bGECompositionResource = new BGECompositionResource(bGECompositionRepository);
        this.restBGECompositionMockMvc = MockMvcBuilders.standaloneSetup(bGECompositionResource)
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
    public static BGEComposition createEntity(EntityManager em) {
        BGEComposition bGEComposition = new BGEComposition()
            .backgroundElectrolyte(DEFAULT_BACKGROUND_ELECTROLYTE)
            .bgeConcentration(DEFAULT_BGE_CONCENTRATION);
        return bGEComposition;
    }

    @Before
    public void initTest() {
        bGEComposition = createEntity(em);
    }

    @Test
    @Transactional
    public void createBGEComposition() throws Exception {
        int databaseSizeBeforeCreate = bGECompositionRepository.findAll().size();

        // Create the BGEComposition
        restBGECompositionMockMvc.perform(post("/api/bge-compositions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bGEComposition)))
            .andExpect(status().isCreated());

        // Validate the BGEComposition in the database
        List<BGEComposition> bGECompositionList = bGECompositionRepository.findAll();
        assertThat(bGECompositionList).hasSize(databaseSizeBeforeCreate + 1);
        BGEComposition testBGEComposition = bGECompositionList.get(bGECompositionList.size() - 1);
        assertThat(testBGEComposition.getBackgroundElectrolyte()).isEqualTo(DEFAULT_BACKGROUND_ELECTROLYTE);
        assertThat(testBGEComposition.getBgeConcentration()).isEqualTo(DEFAULT_BGE_CONCENTRATION);
    }

    @Test
    @Transactional
    public void createBGECompositionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = bGECompositionRepository.findAll().size();

        // Create the BGEComposition with an existing ID
        bGEComposition.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBGECompositionMockMvc.perform(post("/api/bge-compositions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bGEComposition)))
            .andExpect(status().isBadRequest());

        // Validate the BGEComposition in the database
        List<BGEComposition> bGECompositionList = bGECompositionRepository.findAll();
        assertThat(bGECompositionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllBGECompositions() throws Exception {
        // Initialize the database
        bGECompositionRepository.saveAndFlush(bGEComposition);

        // Get all the bGECompositionList
        restBGECompositionMockMvc.perform(get("/api/bge-compositions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bGEComposition.getId().intValue())))
            .andExpect(jsonPath("$.[*].backgroundElectrolyte").value(hasItem(DEFAULT_BACKGROUND_ELECTROLYTE.toString())))
            .andExpect(jsonPath("$.[*].bgeConcentration").value(hasItem(DEFAULT_BGE_CONCENTRATION.intValue())));
    }
    
    @Test
    @Transactional
    public void getBGEComposition() throws Exception {
        // Initialize the database
        bGECompositionRepository.saveAndFlush(bGEComposition);

        // Get the bGEComposition
        restBGECompositionMockMvc.perform(get("/api/bge-compositions/{id}", bGEComposition.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(bGEComposition.getId().intValue()))
            .andExpect(jsonPath("$.backgroundElectrolyte").value(DEFAULT_BACKGROUND_ELECTROLYTE.toString()))
            .andExpect(jsonPath("$.bgeConcentration").value(DEFAULT_BGE_CONCENTRATION.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingBGEComposition() throws Exception {
        // Get the bGEComposition
        restBGECompositionMockMvc.perform(get("/api/bge-compositions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBGEComposition() throws Exception {
        // Initialize the database
        bGECompositionRepository.saveAndFlush(bGEComposition);

        int databaseSizeBeforeUpdate = bGECompositionRepository.findAll().size();

        // Update the bGEComposition
        BGEComposition updatedBGEComposition = bGECompositionRepository.findById(bGEComposition.getId()).get();
        // Disconnect from session so that the updates on updatedBGEComposition are not directly saved in db
        em.detach(updatedBGEComposition);
        updatedBGEComposition
            .backgroundElectrolyte(UPDATED_BACKGROUND_ELECTROLYTE)
            .bgeConcentration(UPDATED_BGE_CONCENTRATION);

        restBGECompositionMockMvc.perform(put("/api/bge-compositions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedBGEComposition)))
            .andExpect(status().isOk());

        // Validate the BGEComposition in the database
        List<BGEComposition> bGECompositionList = bGECompositionRepository.findAll();
        assertThat(bGECompositionList).hasSize(databaseSizeBeforeUpdate);
        BGEComposition testBGEComposition = bGECompositionList.get(bGECompositionList.size() - 1);
        assertThat(testBGEComposition.getBackgroundElectrolyte()).isEqualTo(UPDATED_BACKGROUND_ELECTROLYTE);
        assertThat(testBGEComposition.getBgeConcentration()).isEqualTo(UPDATED_BGE_CONCENTRATION);
    }

    @Test
    @Transactional
    public void updateNonExistingBGEComposition() throws Exception {
        int databaseSizeBeforeUpdate = bGECompositionRepository.findAll().size();

        // Create the BGEComposition

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBGECompositionMockMvc.perform(put("/api/bge-compositions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bGEComposition)))
            .andExpect(status().isBadRequest());

        // Validate the BGEComposition in the database
        List<BGEComposition> bGECompositionList = bGECompositionRepository.findAll();
        assertThat(bGECompositionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteBGEComposition() throws Exception {
        // Initialize the database
        bGECompositionRepository.saveAndFlush(bGEComposition);

        int databaseSizeBeforeDelete = bGECompositionRepository.findAll().size();

        // Delete the bGEComposition
        restBGECompositionMockMvc.perform(delete("/api/bge-compositions/{id}", bGEComposition.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<BGEComposition> bGECompositionList = bGECompositionRepository.findAll();
        assertThat(bGECompositionList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BGEComposition.class);
        BGEComposition bGEComposition1 = new BGEComposition();
        bGEComposition1.setId(1L);
        BGEComposition bGEComposition2 = new BGEComposition();
        bGEComposition2.setId(bGEComposition1.getId());
        assertThat(bGEComposition1).isEqualTo(bGEComposition2);
        bGEComposition2.setId(2L);
        assertThat(bGEComposition1).isNotEqualTo(bGEComposition2);
        bGEComposition1.setId(null);
        assertThat(bGEComposition1).isNotEqualTo(bGEComposition2);
    }
}
