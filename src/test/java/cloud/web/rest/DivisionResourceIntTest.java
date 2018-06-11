package cloud.web.rest;

import cloud.CloudApp;

import cloud.domain.Division;
import cloud.repository.DivisionRepository;
import cloud.service.DivisionService;
import cloud.service.dto.DivisionDTO;
import cloud.service.mapper.DivisionMapper;
import cloud.web.rest.errors.ExceptionTranslator;

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

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static cloud.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the DivisionResource REST controller.
 *
 * @see DivisionResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CloudApp.class)
public class DivisionResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_ESTD_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_ESTD_DATE = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private DivisionRepository divisionRepository;

    @Autowired
    private DivisionMapper divisionMapper;

    @Autowired
    private DivisionService divisionService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restDivisionMockMvc;

    private Division division;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DivisionResource divisionResource = new DivisionResource(divisionService);
        this.restDivisionMockMvc = MockMvcBuilders.standaloneSetup(divisionResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Division createEntity(EntityManager em) {
        Division division = new Division()
            .name(DEFAULT_NAME)
            .estdDate(DEFAULT_ESTD_DATE);
        return division;
    }

    @Before
    public void initTest() {
        division = createEntity(em);
    }

    @Test
    @Transactional
    public void createDivision() throws Exception {
        int databaseSizeBeforeCreate = divisionRepository.findAll().size();

        // Create the Division
        DivisionDTO divisionDTO = divisionMapper.toDto(division);
        restDivisionMockMvc.perform(post("/api/divisions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(divisionDTO)))
            .andExpect(status().isCreated());

        // Validate the Division in the database
        List<Division> divisionList = divisionRepository.findAll();
        assertThat(divisionList).hasSize(databaseSizeBeforeCreate + 1);
        Division testDivision = divisionList.get(divisionList.size() - 1);
        assertThat(testDivision.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testDivision.getEstdDate()).isEqualTo(DEFAULT_ESTD_DATE);
    }

    @Test
    @Transactional
    public void createDivisionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = divisionRepository.findAll().size();

        // Create the Division with an existing ID
        division.setId(1L);
        DivisionDTO divisionDTO = divisionMapper.toDto(division);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDivisionMockMvc.perform(post("/api/divisions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(divisionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Division in the database
        List<Division> divisionList = divisionRepository.findAll();
        assertThat(divisionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = divisionRepository.findAll().size();
        // set the field null
        division.setName(null);

        // Create the Division, which fails.
        DivisionDTO divisionDTO = divisionMapper.toDto(division);

        restDivisionMockMvc.perform(post("/api/divisions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(divisionDTO)))
            .andExpect(status().isBadRequest());

        List<Division> divisionList = divisionRepository.findAll();
        assertThat(divisionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDivisions() throws Exception {
        // Initialize the database
        divisionRepository.saveAndFlush(division);

        // Get all the divisionList
        restDivisionMockMvc.perform(get("/api/divisions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(division.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].estdDate").value(hasItem(DEFAULT_ESTD_DATE.toString())));
    }

    @Test
    @Transactional
    public void getDivision() throws Exception {
        // Initialize the database
        divisionRepository.saveAndFlush(division);

        // Get the division
        restDivisionMockMvc.perform(get("/api/divisions/{id}", division.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(division.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.estdDate").value(DEFAULT_ESTD_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingDivision() throws Exception {
        // Get the division
        restDivisionMockMvc.perform(get("/api/divisions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDivision() throws Exception {
        // Initialize the database
        divisionRepository.saveAndFlush(division);
        int databaseSizeBeforeUpdate = divisionRepository.findAll().size();

        // Update the division
        Division updatedDivision = divisionRepository.findOne(division.getId());
        // Disconnect from session so that the updates on updatedDivision are not directly saved in db
        em.detach(updatedDivision);
        updatedDivision
            .name(UPDATED_NAME)
            .estdDate(UPDATED_ESTD_DATE);
        DivisionDTO divisionDTO = divisionMapper.toDto(updatedDivision);

        restDivisionMockMvc.perform(put("/api/divisions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(divisionDTO)))
            .andExpect(status().isOk());

        // Validate the Division in the database
        List<Division> divisionList = divisionRepository.findAll();
        assertThat(divisionList).hasSize(databaseSizeBeforeUpdate);
        Division testDivision = divisionList.get(divisionList.size() - 1);
        assertThat(testDivision.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testDivision.getEstdDate()).isEqualTo(UPDATED_ESTD_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingDivision() throws Exception {
        int databaseSizeBeforeUpdate = divisionRepository.findAll().size();

        // Create the Division
        DivisionDTO divisionDTO = divisionMapper.toDto(division);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restDivisionMockMvc.perform(put("/api/divisions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(divisionDTO)))
            .andExpect(status().isCreated());

        // Validate the Division in the database
        List<Division> divisionList = divisionRepository.findAll();
        assertThat(divisionList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteDivision() throws Exception {
        // Initialize the database
        divisionRepository.saveAndFlush(division);
        int databaseSizeBeforeDelete = divisionRepository.findAll().size();

        // Get the division
        restDivisionMockMvc.perform(delete("/api/divisions/{id}", division.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Division> divisionList = divisionRepository.findAll();
        assertThat(divisionList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Division.class);
        Division division1 = new Division();
        division1.setId(1L);
        Division division2 = new Division();
        division2.setId(division1.getId());
        assertThat(division1).isEqualTo(division2);
        division2.setId(2L);
        assertThat(division1).isNotEqualTo(division2);
        division1.setId(null);
        assertThat(division1).isNotEqualTo(division2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DivisionDTO.class);
        DivisionDTO divisionDTO1 = new DivisionDTO();
        divisionDTO1.setId(1L);
        DivisionDTO divisionDTO2 = new DivisionDTO();
        assertThat(divisionDTO1).isNotEqualTo(divisionDTO2);
        divisionDTO2.setId(divisionDTO1.getId());
        assertThat(divisionDTO1).isEqualTo(divisionDTO2);
        divisionDTO2.setId(2L);
        assertThat(divisionDTO1).isNotEqualTo(divisionDTO2);
        divisionDTO1.setId(null);
        assertThat(divisionDTO1).isNotEqualTo(divisionDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(divisionMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(divisionMapper.fromId(null)).isNull();
    }
}
