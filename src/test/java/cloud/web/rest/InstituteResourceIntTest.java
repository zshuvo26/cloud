package cloud.web.rest;

import cloud.CloudApp;

import cloud.domain.Institute;
import cloud.repository.InstituteRepository;
import cloud.service.InstituteService;
import cloud.service.dto.InstituteDTO;
import cloud.service.mapper.InstituteMapper;
import cloud.service.UserService;
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

import cloud.domain.enumeration.InstituteType;
/**
 * Test class for the InstituteResource REST controller.
 *
 * @see InstituteResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CloudApp.class)
public class InstituteResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_ESTD_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_ESTD_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_WEBSITE = "AAAAAAAAAA";
    private static final String UPDATED_WEBSITE = "BBBBBBBBBB";

    private static final String DEFAULT_CONTACT_NO = "AAAAAAAAAA";
    private static final String UPDATED_CONTACT_NO = "BBBBBBBBBB";

    private static final InstituteType DEFAULT_INSTITUTE_TYPE = InstituteType.UNIVERSITY;
    private static final InstituteType UPDATED_INSTITUTE_TYPE = InstituteType.ENGINEERING_COLLEGE;

    @Autowired
    private InstituteRepository instituteRepository;

    @Autowired
    private InstituteMapper instituteMapper;

    @Autowired
    private InstituteService instituteService;
    @Autowired
    private UserService userService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restInstituteMockMvc;

    private Institute institute;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final InstituteResource instituteResource = new InstituteResource(instituteService,userService);
        this.restInstituteMockMvc = MockMvcBuilders.standaloneSetup(instituteResource)
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
    public static Institute createEntity(EntityManager em) {
        Institute institute = new Institute()
            .name(DEFAULT_NAME)
            .estdDate(DEFAULT_ESTD_DATE)
            .email(DEFAULT_EMAIL)
            .website(DEFAULT_WEBSITE)
            .contactNo(DEFAULT_CONTACT_NO)
            .instituteType(DEFAULT_INSTITUTE_TYPE);
        return institute;
    }

    @Before
    public void initTest() {
        institute = createEntity(em);
    }

    @Test
    @Transactional
    public void createInstitute() throws Exception {
        int databaseSizeBeforeCreate = instituteRepository.findAll().size();

        // Create the Institute
        InstituteDTO instituteDTO = instituteMapper.toDto(institute);
        restInstituteMockMvc.perform(post("/api/institutes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(instituteDTO)))
            .andExpect(status().isCreated());

        // Validate the Institute in the database
        List<Institute> instituteList = instituteRepository.findAll();
        assertThat(instituteList).hasSize(databaseSizeBeforeCreate + 1);
        Institute testInstitute = instituteList.get(instituteList.size() - 1);
        assertThat(testInstitute.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testInstitute.getEstdDate()).isEqualTo(DEFAULT_ESTD_DATE);
        assertThat(testInstitute.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testInstitute.getWebsite()).isEqualTo(DEFAULT_WEBSITE);
        assertThat(testInstitute.getContactNo()).isEqualTo(DEFAULT_CONTACT_NO);
        assertThat(testInstitute.getInstituteType()).isEqualTo(DEFAULT_INSTITUTE_TYPE);
    }

    @Test
    @Transactional
    public void createInstituteWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = instituteRepository.findAll().size();

        // Create the Institute with an existing ID
        institute.setId(1L);
        InstituteDTO instituteDTO = instituteMapper.toDto(institute);

        // An entity with an existing ID cannot be created, so this API call must fail
        restInstituteMockMvc.perform(post("/api/institutes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(instituteDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Institute in the database
        List<Institute> instituteList = instituteRepository.findAll();
        assertThat(instituteList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = instituteRepository.findAll().size();
        // set the field null
        institute.setName(null);

        // Create the Institute, which fails.
        InstituteDTO instituteDTO = instituteMapper.toDto(institute);

        restInstituteMockMvc.perform(post("/api/institutes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(instituteDTO)))
            .andExpect(status().isBadRequest());

        List<Institute> instituteList = instituteRepository.findAll();
        assertThat(instituteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllInstitutes() throws Exception {
        // Initialize the database
        instituteRepository.saveAndFlush(institute);

        // Get all the instituteList
        restInstituteMockMvc.perform(get("/api/institutes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(institute.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].estdDate").value(hasItem(DEFAULT_ESTD_DATE.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].website").value(hasItem(DEFAULT_WEBSITE.toString())))
            .andExpect(jsonPath("$.[*].contactNo").value(hasItem(DEFAULT_CONTACT_NO.toString())))
            .andExpect(jsonPath("$.[*].instituteType").value(hasItem(DEFAULT_INSTITUTE_TYPE.toString())));
    }

    @Test
    @Transactional
    public void getInstitute() throws Exception {
        // Initialize the database
        instituteRepository.saveAndFlush(institute);

        // Get the institute
        restInstituteMockMvc.perform(get("/api/institutes/{id}", institute.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(institute.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.estdDate").value(DEFAULT_ESTD_DATE.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.website").value(DEFAULT_WEBSITE.toString()))
            .andExpect(jsonPath("$.contactNo").value(DEFAULT_CONTACT_NO.toString()))
            .andExpect(jsonPath("$.instituteType").value(DEFAULT_INSTITUTE_TYPE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingInstitute() throws Exception {
        // Get the institute
        restInstituteMockMvc.perform(get("/api/institutes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInstitute() throws Exception {
        // Initialize the database
        instituteRepository.saveAndFlush(institute);
        int databaseSizeBeforeUpdate = instituteRepository.findAll().size();

        // Update the institute
        Institute updatedInstitute = instituteRepository.findOne(institute.getId());
        // Disconnect from session so that the updates on updatedInstitute are not directly saved in db
        em.detach(updatedInstitute);
        updatedInstitute
            .name(UPDATED_NAME)
            .estdDate(UPDATED_ESTD_DATE)
            .email(UPDATED_EMAIL)
            .website(UPDATED_WEBSITE)
            .contactNo(UPDATED_CONTACT_NO)
            .instituteType(UPDATED_INSTITUTE_TYPE);
        InstituteDTO instituteDTO = instituteMapper.toDto(updatedInstitute);

        restInstituteMockMvc.perform(put("/api/institutes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(instituteDTO)))
            .andExpect(status().isOk());

        // Validate the Institute in the database
        List<Institute> instituteList = instituteRepository.findAll();
        assertThat(instituteList).hasSize(databaseSizeBeforeUpdate);
        Institute testInstitute = instituteList.get(instituteList.size() - 1);
        assertThat(testInstitute.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testInstitute.getEstdDate()).isEqualTo(UPDATED_ESTD_DATE);
        assertThat(testInstitute.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testInstitute.getWebsite()).isEqualTo(UPDATED_WEBSITE);
        assertThat(testInstitute.getContactNo()).isEqualTo(UPDATED_CONTACT_NO);
        assertThat(testInstitute.getInstituteType()).isEqualTo(UPDATED_INSTITUTE_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingInstitute() throws Exception {
        int databaseSizeBeforeUpdate = instituteRepository.findAll().size();

        // Create the Institute
        InstituteDTO instituteDTO = instituteMapper.toDto(institute);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restInstituteMockMvc.perform(put("/api/institutes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(instituteDTO)))
            .andExpect(status().isCreated());

        // Validate the Institute in the database
        List<Institute> instituteList = instituteRepository.findAll();
        assertThat(instituteList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteInstitute() throws Exception {
        // Initialize the database
        instituteRepository.saveAndFlush(institute);
        int databaseSizeBeforeDelete = instituteRepository.findAll().size();

        // Get the institute
        restInstituteMockMvc.perform(delete("/api/institutes/{id}", institute.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Institute> instituteList = instituteRepository.findAll();
        assertThat(instituteList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Institute.class);
        Institute institute1 = new Institute();
        institute1.setId(1L);
        Institute institute2 = new Institute();
        institute2.setId(institute1.getId());
        assertThat(institute1).isEqualTo(institute2);
        institute2.setId(2L);
        assertThat(institute1).isNotEqualTo(institute2);
        institute1.setId(null);
        assertThat(institute1).isNotEqualTo(institute2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(InstituteDTO.class);
        InstituteDTO instituteDTO1 = new InstituteDTO();
        instituteDTO1.setId(1L);
        InstituteDTO instituteDTO2 = new InstituteDTO();
        assertThat(instituteDTO1).isNotEqualTo(instituteDTO2);
        instituteDTO2.setId(instituteDTO1.getId());
        assertThat(instituteDTO1).isEqualTo(instituteDTO2);
        instituteDTO2.setId(2L);
        assertThat(instituteDTO1).isNotEqualTo(instituteDTO2);
        instituteDTO1.setId(null);
        assertThat(instituteDTO1).isNotEqualTo(instituteDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(instituteMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(instituteMapper.fromId(null)).isNull();
    }
}
