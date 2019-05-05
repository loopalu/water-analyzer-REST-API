package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.CeApp;

import com.mycompany.myapp.domain.Method;
import com.mycompany.myapp.repository.MethodRepository;
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
 * Test class for the MethodResource REST controller.
 *
 * @see MethodResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CeApp.class)
public class MethodResourceIntTest {

    private static final String DEFAULT_METHOD_NAME = "AAAAAAAAAA";
    private static final String UPDATED_METHOD_NAME = "BBBBBBBBBB";

    private static final Long DEFAULT_FREQUENCY = 1L;
    private static final Long UPDATED_FREQUENCY = 2L;

    private static final String DEFAULT_INJECTION_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_INJECTION_TYPE = "BBBBBBBBBB";

    private static final Long DEFAULT_INJECTION_TIME = 1L;
    private static final Long UPDATED_INJECTION_TIME = 2L;

    private static final Long DEFAULT_MEASURE_VALUE = 1L;
    private static final Long UPDATED_MEASURE_VALUE = 2L;

    private static final String DEFAULT_UNIT_OF_MEASURE = "AAAAAAAAAA";
    private static final String UPDATED_UNIT_OF_MEASURE = "BBBBBBBBBB";

    private static final Float DEFAULT_EXPERIMENT_TIME = 1F;
    private static final Float UPDATED_EXPERIMENT_TIME = 2F;

    private static final Float DEFAULT_CURRENT = 1F;
    private static final Float UPDATED_CURRENT = 2F;

    @Autowired
    private MethodRepository methodRepository;

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

    private MockMvc restMethodMockMvc;

    private Method method;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MethodResource methodResource = new MethodResource(methodRepository);
        this.restMethodMockMvc = MockMvcBuilders.standaloneSetup(methodResource)
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
    public static Method createEntity(EntityManager em) {
        Method method = new Method()
            .methodName(DEFAULT_METHOD_NAME)
            .frequency(DEFAULT_FREQUENCY)
            .injectionType(DEFAULT_INJECTION_TYPE)
            .injectionTime(DEFAULT_INJECTION_TIME)
            .measureValue(DEFAULT_MEASURE_VALUE)
            .unitOfMeasure(DEFAULT_UNIT_OF_MEASURE)
            .experimentTime(DEFAULT_EXPERIMENT_TIME)
            .current(DEFAULT_CURRENT);
        return method;
    }

    @Before
    public void initTest() {
        method = createEntity(em);
    }

    @Test
    @Transactional
    public void createMethod() throws Exception {
        int databaseSizeBeforeCreate = methodRepository.findAll().size();

        // Create the Method
        restMethodMockMvc.perform(post("/api/methods")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(method)))
            .andExpect(status().isCreated());

        // Validate the Method in the database
        List<Method> methodList = methodRepository.findAll();
        assertThat(methodList).hasSize(databaseSizeBeforeCreate + 1);
        Method testMethod = methodList.get(methodList.size() - 1);
        assertThat(testMethod.getMethodName()).isEqualTo(DEFAULT_METHOD_NAME);
        assertThat(testMethod.getFrequency()).isEqualTo(DEFAULT_FREQUENCY);
        assertThat(testMethod.getInjectionType()).isEqualTo(DEFAULT_INJECTION_TYPE);
        assertThat(testMethod.getInjectionTime()).isEqualTo(DEFAULT_INJECTION_TIME);
        assertThat(testMethod.getMeasureValue()).isEqualTo(DEFAULT_MEASURE_VALUE);
        assertThat(testMethod.getUnitOfMeasure()).isEqualTo(DEFAULT_UNIT_OF_MEASURE);
        assertThat(testMethod.getExperimentTime()).isEqualTo(DEFAULT_EXPERIMENT_TIME);
        assertThat(testMethod.getCurrent()).isEqualTo(DEFAULT_CURRENT);
    }

    @Test
    @Transactional
    public void createMethodWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = methodRepository.findAll().size();

        // Create the Method with an existing ID
        method.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMethodMockMvc.perform(post("/api/methods")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(method)))
            .andExpect(status().isBadRequest());

        // Validate the Method in the database
        List<Method> methodList = methodRepository.findAll();
        assertThat(methodList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkMethodNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = methodRepository.findAll().size();
        // set the field null
        method.setMethodName(null);

        // Create the Method, which fails.

        restMethodMockMvc.perform(post("/api/methods")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(method)))
            .andExpect(status().isBadRequest());

        List<Method> methodList = methodRepository.findAll();
        assertThat(methodList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFrequencyIsRequired() throws Exception {
        int databaseSizeBeforeTest = methodRepository.findAll().size();
        // set the field null
        method.setFrequency(null);

        // Create the Method, which fails.

        restMethodMockMvc.perform(post("/api/methods")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(method)))
            .andExpect(status().isBadRequest());

        List<Method> methodList = methodRepository.findAll();
        assertThat(methodList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMeasureValueIsRequired() throws Exception {
        int databaseSizeBeforeTest = methodRepository.findAll().size();
        // set the field null
        method.setMeasureValue(null);

        // Create the Method, which fails.

        restMethodMockMvc.perform(post("/api/methods")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(method)))
            .andExpect(status().isBadRequest());

        List<Method> methodList = methodRepository.findAll();
        assertThat(methodList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUnitOfMeasureIsRequired() throws Exception {
        int databaseSizeBeforeTest = methodRepository.findAll().size();
        // set the field null
        method.setUnitOfMeasure(null);

        // Create the Method, which fails.

        restMethodMockMvc.perform(post("/api/methods")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(method)))
            .andExpect(status().isBadRequest());

        List<Method> methodList = methodRepository.findAll();
        assertThat(methodList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkExperimentTimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = methodRepository.findAll().size();
        // set the field null
        method.setExperimentTime(null);

        // Create the Method, which fails.

        restMethodMockMvc.perform(post("/api/methods")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(method)))
            .andExpect(status().isBadRequest());

        List<Method> methodList = methodRepository.findAll();
        assertThat(methodList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCurrentIsRequired() throws Exception {
        int databaseSizeBeforeTest = methodRepository.findAll().size();
        // set the field null
        method.setCurrent(null);

        // Create the Method, which fails.

        restMethodMockMvc.perform(post("/api/methods")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(method)))
            .andExpect(status().isBadRequest());

        List<Method> methodList = methodRepository.findAll();
        assertThat(methodList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMethods() throws Exception {
        // Initialize the database
        methodRepository.saveAndFlush(method);

        // Get all the methodList
        restMethodMockMvc.perform(get("/api/methods?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(method.getId().intValue())))
            .andExpect(jsonPath("$.[*].methodName").value(hasItem(DEFAULT_METHOD_NAME.toString())))
            .andExpect(jsonPath("$.[*].frequency").value(hasItem(DEFAULT_FREQUENCY.intValue())))
            .andExpect(jsonPath("$.[*].injectionType").value(hasItem(DEFAULT_INJECTION_TYPE.toString())))
            .andExpect(jsonPath("$.[*].injectionTime").value(hasItem(DEFAULT_INJECTION_TIME.intValue())))
            .andExpect(jsonPath("$.[*].measureValue").value(hasItem(DEFAULT_MEASURE_VALUE.intValue())))
            .andExpect(jsonPath("$.[*].unitOfMeasure").value(hasItem(DEFAULT_UNIT_OF_MEASURE.toString())))
            .andExpect(jsonPath("$.[*].experimentTime").value(hasItem(DEFAULT_EXPERIMENT_TIME.doubleValue())))
            .andExpect(jsonPath("$.[*].current").value(hasItem(DEFAULT_CURRENT.doubleValue())));
    }
    
    @Test
    @Transactional
    public void getMethod() throws Exception {
        // Initialize the database
        methodRepository.saveAndFlush(method);

        // Get the method
        restMethodMockMvc.perform(get("/api/methods/{id}", method.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(method.getId().intValue()))
            .andExpect(jsonPath("$.methodName").value(DEFAULT_METHOD_NAME.toString()))
            .andExpect(jsonPath("$.frequency").value(DEFAULT_FREQUENCY.intValue()))
            .andExpect(jsonPath("$.injectionType").value(DEFAULT_INJECTION_TYPE.toString()))
            .andExpect(jsonPath("$.injectionTime").value(DEFAULT_INJECTION_TIME.intValue()))
            .andExpect(jsonPath("$.measureValue").value(DEFAULT_MEASURE_VALUE.intValue()))
            .andExpect(jsonPath("$.unitOfMeasure").value(DEFAULT_UNIT_OF_MEASURE.toString()))
            .andExpect(jsonPath("$.experimentTime").value(DEFAULT_EXPERIMENT_TIME.doubleValue()))
            .andExpect(jsonPath("$.current").value(DEFAULT_CURRENT.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingMethod() throws Exception {
        // Get the method
        restMethodMockMvc.perform(get("/api/methods/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMethod() throws Exception {
        // Initialize the database
        methodRepository.saveAndFlush(method);

        int databaseSizeBeforeUpdate = methodRepository.findAll().size();

        // Update the method
        Method updatedMethod = methodRepository.findById(method.getId()).get();
        // Disconnect from session so that the updates on updatedMethod are not directly saved in db
        em.detach(updatedMethod);
        updatedMethod
            .methodName(UPDATED_METHOD_NAME)
            .frequency(UPDATED_FREQUENCY)
            .injectionType(UPDATED_INJECTION_TYPE)
            .injectionTime(UPDATED_INJECTION_TIME)
            .measureValue(UPDATED_MEASURE_VALUE)
            .unitOfMeasure(UPDATED_UNIT_OF_MEASURE)
            .experimentTime(UPDATED_EXPERIMENT_TIME)
            .current(UPDATED_CURRENT);

        restMethodMockMvc.perform(put("/api/methods")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedMethod)))
            .andExpect(status().isOk());

        // Validate the Method in the database
        List<Method> methodList = methodRepository.findAll();
        assertThat(methodList).hasSize(databaseSizeBeforeUpdate);
        Method testMethod = methodList.get(methodList.size() - 1);
        assertThat(testMethod.getMethodName()).isEqualTo(UPDATED_METHOD_NAME);
        assertThat(testMethod.getFrequency()).isEqualTo(UPDATED_FREQUENCY);
        assertThat(testMethod.getInjectionType()).isEqualTo(UPDATED_INJECTION_TYPE);
        assertThat(testMethod.getInjectionTime()).isEqualTo(UPDATED_INJECTION_TIME);
        assertThat(testMethod.getMeasureValue()).isEqualTo(UPDATED_MEASURE_VALUE);
        assertThat(testMethod.getUnitOfMeasure()).isEqualTo(UPDATED_UNIT_OF_MEASURE);
        assertThat(testMethod.getExperimentTime()).isEqualTo(UPDATED_EXPERIMENT_TIME);
        assertThat(testMethod.getCurrent()).isEqualTo(UPDATED_CURRENT);
    }

    @Test
    @Transactional
    public void updateNonExistingMethod() throws Exception {
        int databaseSizeBeforeUpdate = methodRepository.findAll().size();

        // Create the Method

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMethodMockMvc.perform(put("/api/methods")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(method)))
            .andExpect(status().isBadRequest());

        // Validate the Method in the database
        List<Method> methodList = methodRepository.findAll();
        assertThat(methodList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMethod() throws Exception {
        // Initialize the database
        methodRepository.saveAndFlush(method);

        int databaseSizeBeforeDelete = methodRepository.findAll().size();

        // Delete the method
        restMethodMockMvc.perform(delete("/api/methods/{id}", method.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Method> methodList = methodRepository.findAll();
        assertThat(methodList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Method.class);
        Method method1 = new Method();
        method1.setId(1L);
        Method method2 = new Method();
        method2.setId(method1.getId());
        assertThat(method1).isEqualTo(method2);
        method2.setId(2L);
        assertThat(method1).isNotEqualTo(method2);
        method1.setId(null);
        assertThat(method1).isNotEqualTo(method2);
    }
}
