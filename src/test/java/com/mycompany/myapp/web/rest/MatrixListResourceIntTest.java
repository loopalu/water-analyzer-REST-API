package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.CeApp;

import com.mycompany.myapp.domain.MatrixList;
import com.mycompany.myapp.repository.MatrixListRepository;
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
 * Test class for the MatrixListResource REST controller.
 *
 * @see MatrixListResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CeApp.class)
public class MatrixListResourceIntTest {

    private static final String DEFAULT_MATRIX_NAME = "AAAAAAAAAA";
    private static final String UPDATED_MATRIX_NAME = "BBBBBBBBBB";

    @Autowired
    private MatrixListRepository matrixListRepository;

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

    private MockMvc restMatrixListMockMvc;

    private MatrixList matrixList;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MatrixListResource matrixListResource = new MatrixListResource(matrixListRepository);
        this.restMatrixListMockMvc = MockMvcBuilders.standaloneSetup(matrixListResource)
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
    public static MatrixList createEntity(EntityManager em) {
        MatrixList matrixList = new MatrixList()
            .matrixName(DEFAULT_MATRIX_NAME);
        return matrixList;
    }

    @Before
    public void initTest() {
        matrixList = createEntity(em);
    }

    @Test
    @Transactional
    public void createMatrixList() throws Exception {
        int databaseSizeBeforeCreate = matrixListRepository.findAll().size();

        // Create the MatrixList
        restMatrixListMockMvc.perform(post("/api/matrix-lists")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(matrixList)))
            .andExpect(status().isCreated());

        // Validate the MatrixList in the database
        List<MatrixList> matrixListList = matrixListRepository.findAll();
        assertThat(matrixListList).hasSize(databaseSizeBeforeCreate + 1);
        MatrixList testMatrixList = matrixListList.get(matrixListList.size() - 1);
        assertThat(testMatrixList.getMatrixName()).isEqualTo(DEFAULT_MATRIX_NAME);
    }

    @Test
    @Transactional
    public void createMatrixListWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = matrixListRepository.findAll().size();

        // Create the MatrixList with an existing ID
        matrixList.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMatrixListMockMvc.perform(post("/api/matrix-lists")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(matrixList)))
            .andExpect(status().isBadRequest());

        // Validate the MatrixList in the database
        List<MatrixList> matrixListList = matrixListRepository.findAll();
        assertThat(matrixListList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllMatrixLists() throws Exception {
        // Initialize the database
        matrixListRepository.saveAndFlush(matrixList);

        // Get all the matrixListList
        restMatrixListMockMvc.perform(get("/api/matrix-lists?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(matrixList.getId().intValue())))
            .andExpect(jsonPath("$.[*].matrixName").value(hasItem(DEFAULT_MATRIX_NAME.toString())));
    }
    
    @Test
    @Transactional
    public void getMatrixList() throws Exception {
        // Initialize the database
        matrixListRepository.saveAndFlush(matrixList);

        // Get the matrixList
        restMatrixListMockMvc.perform(get("/api/matrix-lists/{id}", matrixList.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(matrixList.getId().intValue()))
            .andExpect(jsonPath("$.matrixName").value(DEFAULT_MATRIX_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingMatrixList() throws Exception {
        // Get the matrixList
        restMatrixListMockMvc.perform(get("/api/matrix-lists/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMatrixList() throws Exception {
        // Initialize the database
        matrixListRepository.saveAndFlush(matrixList);

        int databaseSizeBeforeUpdate = matrixListRepository.findAll().size();

        // Update the matrixList
        MatrixList updatedMatrixList = matrixListRepository.findById(matrixList.getId()).get();
        // Disconnect from session so that the updates on updatedMatrixList are not directly saved in db
        em.detach(updatedMatrixList);
        updatedMatrixList
            .matrixName(UPDATED_MATRIX_NAME);

        restMatrixListMockMvc.perform(put("/api/matrix-lists")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedMatrixList)))
            .andExpect(status().isOk());

        // Validate the MatrixList in the database
        List<MatrixList> matrixListList = matrixListRepository.findAll();
        assertThat(matrixListList).hasSize(databaseSizeBeforeUpdate);
        MatrixList testMatrixList = matrixListList.get(matrixListList.size() - 1);
        assertThat(testMatrixList.getMatrixName()).isEqualTo(UPDATED_MATRIX_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingMatrixList() throws Exception {
        int databaseSizeBeforeUpdate = matrixListRepository.findAll().size();

        // Create the MatrixList

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMatrixListMockMvc.perform(put("/api/matrix-lists")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(matrixList)))
            .andExpect(status().isBadRequest());

        // Validate the MatrixList in the database
        List<MatrixList> matrixListList = matrixListRepository.findAll();
        assertThat(matrixListList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMatrixList() throws Exception {
        // Initialize the database
        matrixListRepository.saveAndFlush(matrixList);

        int databaseSizeBeforeDelete = matrixListRepository.findAll().size();

        // Delete the matrixList
        restMatrixListMockMvc.perform(delete("/api/matrix-lists/{id}", matrixList.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<MatrixList> matrixListList = matrixListRepository.findAll();
        assertThat(matrixListList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MatrixList.class);
        MatrixList matrixList1 = new MatrixList();
        matrixList1.setId(1L);
        MatrixList matrixList2 = new MatrixList();
        matrixList2.setId(matrixList1.getId());
        assertThat(matrixList1).isEqualTo(matrixList2);
        matrixList2.setId(2L);
        assertThat(matrixList1).isNotEqualTo(matrixList2);
        matrixList1.setId(null);
        assertThat(matrixList1).isNotEqualTo(matrixList2);
    }
}
