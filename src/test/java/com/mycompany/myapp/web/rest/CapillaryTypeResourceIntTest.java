package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.CeApp;

import com.mycompany.myapp.domain.CapillaryType;
import com.mycompany.myapp.repository.CapillaryTypeRepository;
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
 * Test class for the CapillaryTypeResource REST controller.
 *
 * @see CapillaryTypeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CeApp.class)
public class CapillaryTypeResourceIntTest {

    private static final String DEFAULT_CAPILLARY_NAME = "AAAAAAAAAA";
    private static final String UPDATED_CAPILLARY_NAME = "BBBBBBBBBB";

    private static final Float DEFAULT_CAPILLARY_LENGTH = 1F;
    private static final Float UPDATED_CAPILLARY_LENGTH = 2F;

    private static final Float DEFAULT_EFFECTIVE_LENGTH = 1F;
    private static final Float UPDATED_EFFECTIVE_LENGTH = 2F;

    private static final Float DEFAULT_INNER_DIAMETER = 1F;
    private static final Float UPDATED_INNER_DIAMETER = 2F;

    private static final Float DEFAULT_OUTER_DIAMETER = 1F;
    private static final Float UPDATED_OUTER_DIAMETER = 2F;

    private static final Float DEFAULT_ELECTRIC_FORCE = 1F;
    private static final Float UPDATED_ELECTRIC_FORCE = 2F;

    @Autowired
    private CapillaryTypeRepository capillaryTypeRepository;

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

    private MockMvc restCapillaryTypeMockMvc;

    private CapillaryType capillaryType;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CapillaryTypeResource capillaryTypeResource = new CapillaryTypeResource(capillaryTypeRepository);
        this.restCapillaryTypeMockMvc = MockMvcBuilders.standaloneSetup(capillaryTypeResource)
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
    public static CapillaryType createEntity(EntityManager em) {
        CapillaryType capillaryType = new CapillaryType()
            .capillaryName(DEFAULT_CAPILLARY_NAME)
            .capillaryLength(DEFAULT_CAPILLARY_LENGTH)
            .effectiveLength(DEFAULT_EFFECTIVE_LENGTH)
            .innerDiameter(DEFAULT_INNER_DIAMETER)
            .outerDiameter(DEFAULT_OUTER_DIAMETER)
            .electricForce(DEFAULT_ELECTRIC_FORCE);
        return capillaryType;
    }

    @Before
    public void initTest() {
        capillaryType = createEntity(em);
    }

    @Test
    @Transactional
    public void createCapillaryType() throws Exception {
        int databaseSizeBeforeCreate = capillaryTypeRepository.findAll().size();

        // Create the CapillaryType
        restCapillaryTypeMockMvc.perform(post("/api/capillary-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(capillaryType)))
            .andExpect(status().isCreated());

        // Validate the CapillaryType in the database
        List<CapillaryType> capillaryTypeList = capillaryTypeRepository.findAll();
        assertThat(capillaryTypeList).hasSize(databaseSizeBeforeCreate + 1);
        CapillaryType testCapillaryType = capillaryTypeList.get(capillaryTypeList.size() - 1);
        assertThat(testCapillaryType.getCapillaryName()).isEqualTo(DEFAULT_CAPILLARY_NAME);
        assertThat(testCapillaryType.getCapillaryLength()).isEqualTo(DEFAULT_CAPILLARY_LENGTH);
        assertThat(testCapillaryType.getEffectiveLength()).isEqualTo(DEFAULT_EFFECTIVE_LENGTH);
        assertThat(testCapillaryType.getInnerDiameter()).isEqualTo(DEFAULT_INNER_DIAMETER);
        assertThat(testCapillaryType.getOuterDiameter()).isEqualTo(DEFAULT_OUTER_DIAMETER);
        assertThat(testCapillaryType.getElectricForce()).isEqualTo(DEFAULT_ELECTRIC_FORCE);
    }

    @Test
    @Transactional
    public void createCapillaryTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = capillaryTypeRepository.findAll().size();

        // Create the CapillaryType with an existing ID
        capillaryType.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCapillaryTypeMockMvc.perform(post("/api/capillary-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(capillaryType)))
            .andExpect(status().isBadRequest());

        // Validate the CapillaryType in the database
        List<CapillaryType> capillaryTypeList = capillaryTypeRepository.findAll();
        assertThat(capillaryTypeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkCapillaryNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = capillaryTypeRepository.findAll().size();
        // set the field null
        capillaryType.setCapillaryName(null);

        // Create the CapillaryType, which fails.

        restCapillaryTypeMockMvc.perform(post("/api/capillary-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(capillaryType)))
            .andExpect(status().isBadRequest());

        List<CapillaryType> capillaryTypeList = capillaryTypeRepository.findAll();
        assertThat(capillaryTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCapillaryLengthIsRequired() throws Exception {
        int databaseSizeBeforeTest = capillaryTypeRepository.findAll().size();
        // set the field null
        capillaryType.setCapillaryLength(null);

        // Create the CapillaryType, which fails.

        restCapillaryTypeMockMvc.perform(post("/api/capillary-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(capillaryType)))
            .andExpect(status().isBadRequest());

        List<CapillaryType> capillaryTypeList = capillaryTypeRepository.findAll();
        assertThat(capillaryTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEffectiveLengthIsRequired() throws Exception {
        int databaseSizeBeforeTest = capillaryTypeRepository.findAll().size();
        // set the field null
        capillaryType.setEffectiveLength(null);

        // Create the CapillaryType, which fails.

        restCapillaryTypeMockMvc.perform(post("/api/capillary-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(capillaryType)))
            .andExpect(status().isBadRequest());

        List<CapillaryType> capillaryTypeList = capillaryTypeRepository.findAll();
        assertThat(capillaryTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkInnerDiameterIsRequired() throws Exception {
        int databaseSizeBeforeTest = capillaryTypeRepository.findAll().size();
        // set the field null
        capillaryType.setInnerDiameter(null);

        // Create the CapillaryType, which fails.

        restCapillaryTypeMockMvc.perform(post("/api/capillary-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(capillaryType)))
            .andExpect(status().isBadRequest());

        List<CapillaryType> capillaryTypeList = capillaryTypeRepository.findAll();
        assertThat(capillaryTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkOuterDiameterIsRequired() throws Exception {
        int databaseSizeBeforeTest = capillaryTypeRepository.findAll().size();
        // set the field null
        capillaryType.setOuterDiameter(null);

        // Create the CapillaryType, which fails.

        restCapillaryTypeMockMvc.perform(post("/api/capillary-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(capillaryType)))
            .andExpect(status().isBadRequest());

        List<CapillaryType> capillaryTypeList = capillaryTypeRepository.findAll();
        assertThat(capillaryTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCapillaryTypes() throws Exception {
        // Initialize the database
        capillaryTypeRepository.saveAndFlush(capillaryType);

        // Get all the capillaryTypeList
        restCapillaryTypeMockMvc.perform(get("/api/capillary-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(capillaryType.getId().intValue())))
            .andExpect(jsonPath("$.[*].capillaryName").value(hasItem(DEFAULT_CAPILLARY_NAME.toString())))
            .andExpect(jsonPath("$.[*].capillaryLength").value(hasItem(DEFAULT_CAPILLARY_LENGTH.doubleValue())))
            .andExpect(jsonPath("$.[*].effectiveLength").value(hasItem(DEFAULT_EFFECTIVE_LENGTH.doubleValue())))
            .andExpect(jsonPath("$.[*].innerDiameter").value(hasItem(DEFAULT_INNER_DIAMETER.doubleValue())))
            .andExpect(jsonPath("$.[*].outerDiameter").value(hasItem(DEFAULT_OUTER_DIAMETER.doubleValue())))
            .andExpect(jsonPath("$.[*].electricForce").value(hasItem(DEFAULT_ELECTRIC_FORCE.doubleValue())));
    }
    
    @Test
    @Transactional
    public void getCapillaryType() throws Exception {
        // Initialize the database
        capillaryTypeRepository.saveAndFlush(capillaryType);

        // Get the capillaryType
        restCapillaryTypeMockMvc.perform(get("/api/capillary-types/{id}", capillaryType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(capillaryType.getId().intValue()))
            .andExpect(jsonPath("$.capillaryName").value(DEFAULT_CAPILLARY_NAME.toString()))
            .andExpect(jsonPath("$.capillaryLength").value(DEFAULT_CAPILLARY_LENGTH.doubleValue()))
            .andExpect(jsonPath("$.effectiveLength").value(DEFAULT_EFFECTIVE_LENGTH.doubleValue()))
            .andExpect(jsonPath("$.innerDiameter").value(DEFAULT_INNER_DIAMETER.doubleValue()))
            .andExpect(jsonPath("$.outerDiameter").value(DEFAULT_OUTER_DIAMETER.doubleValue()))
            .andExpect(jsonPath("$.electricForce").value(DEFAULT_ELECTRIC_FORCE.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingCapillaryType() throws Exception {
        // Get the capillaryType
        restCapillaryTypeMockMvc.perform(get("/api/capillary-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCapillaryType() throws Exception {
        // Initialize the database
        capillaryTypeRepository.saveAndFlush(capillaryType);

        int databaseSizeBeforeUpdate = capillaryTypeRepository.findAll().size();

        // Update the capillaryType
        CapillaryType updatedCapillaryType = capillaryTypeRepository.findById(capillaryType.getId()).get();
        // Disconnect from session so that the updates on updatedCapillaryType are not directly saved in db
        em.detach(updatedCapillaryType);
        updatedCapillaryType
            .capillaryName(UPDATED_CAPILLARY_NAME)
            .capillaryLength(UPDATED_CAPILLARY_LENGTH)
            .effectiveLength(UPDATED_EFFECTIVE_LENGTH)
            .innerDiameter(UPDATED_INNER_DIAMETER)
            .outerDiameter(UPDATED_OUTER_DIAMETER)
            .electricForce(UPDATED_ELECTRIC_FORCE);

        restCapillaryTypeMockMvc.perform(put("/api/capillary-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCapillaryType)))
            .andExpect(status().isOk());

        // Validate the CapillaryType in the database
        List<CapillaryType> capillaryTypeList = capillaryTypeRepository.findAll();
        assertThat(capillaryTypeList).hasSize(databaseSizeBeforeUpdate);
        CapillaryType testCapillaryType = capillaryTypeList.get(capillaryTypeList.size() - 1);
        assertThat(testCapillaryType.getCapillaryName()).isEqualTo(UPDATED_CAPILLARY_NAME);
        assertThat(testCapillaryType.getCapillaryLength()).isEqualTo(UPDATED_CAPILLARY_LENGTH);
        assertThat(testCapillaryType.getEffectiveLength()).isEqualTo(UPDATED_EFFECTIVE_LENGTH);
        assertThat(testCapillaryType.getInnerDiameter()).isEqualTo(UPDATED_INNER_DIAMETER);
        assertThat(testCapillaryType.getOuterDiameter()).isEqualTo(UPDATED_OUTER_DIAMETER);
        assertThat(testCapillaryType.getElectricForce()).isEqualTo(UPDATED_ELECTRIC_FORCE);
    }

    @Test
    @Transactional
    public void updateNonExistingCapillaryType() throws Exception {
        int databaseSizeBeforeUpdate = capillaryTypeRepository.findAll().size();

        // Create the CapillaryType

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCapillaryTypeMockMvc.perform(put("/api/capillary-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(capillaryType)))
            .andExpect(status().isBadRequest());

        // Validate the CapillaryType in the database
        List<CapillaryType> capillaryTypeList = capillaryTypeRepository.findAll();
        assertThat(capillaryTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCapillaryType() throws Exception {
        // Initialize the database
        capillaryTypeRepository.saveAndFlush(capillaryType);

        int databaseSizeBeforeDelete = capillaryTypeRepository.findAll().size();

        // Delete the capillaryType
        restCapillaryTypeMockMvc.perform(delete("/api/capillary-types/{id}", capillaryType.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CapillaryType> capillaryTypeList = capillaryTypeRepository.findAll();
        assertThat(capillaryTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CapillaryType.class);
        CapillaryType capillaryType1 = new CapillaryType();
        capillaryType1.setId(1L);
        CapillaryType capillaryType2 = new CapillaryType();
        capillaryType2.setId(capillaryType1.getId());
        assertThat(capillaryType1).isEqualTo(capillaryType2);
        capillaryType2.setId(2L);
        assertThat(capillaryType1).isNotEqualTo(capillaryType2);
        capillaryType1.setId(null);
        assertThat(capillaryType1).isNotEqualTo(capillaryType2);
    }
}
