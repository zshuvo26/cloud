package cloud.web.rest;

import cloud.CloudApp;

import cloud.domain.Upazila;
import cloud.repository.UpazilaRepository;
import cloud.service.UpazilaService;
import cloud.service.dto.UpazilaDTO;
import cloud.service.mapper.UpazilaMapper;
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
 * Test class for the UpazilaResource REST controller.
 *
 * @see UpazilaResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CloudApp.class)
public class UpazilaResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_ESTD_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_ESTD_DATE = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private UpazilaRepository upazilaRepository;

    @Autowired
    private UpazilaMapper upazilaMapper;

    @Autowired
    private UpazilaService upazilaService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restUpazilaMockMvc;

    private Upazila upazila;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final UpazilaResource upazilaResource = new UpazilaResource(upazilaService);
        this.restUpazilaMockMvc = MockMvcBuilders.standaloneSetup(upazilaResource)
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
    public static Upazila createEntity(EntityManager em) {
        Upazila upazila = new Upazila()
            .name(DEFAULT_NAME)
            .estdDate(DEFAULT_ESTD_DATE);
        return upazila;
    }

    @Before
    public void initTest() {
        upazila = createEntity(em);
    }

    @Test
    @Transactional
    public void createUpazila() throws Exception {
        int databaseSizeBeforeCreate = upazilaRepository.findAll().size();

        // Create the Upazila
        UpazilaDTO upazilaDTO = upazilaMapper.toDto(upazila);
        restUpazilaMockMvc.perform(post("/api/upazilas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(upazilaDTO)))
            .andExpect(status().isCreated());

        // Validate the Upazila in the database
        List<Upazila> upazilaList = upazilaRepository.findAll();
        assertThat(upazilaList).hasSize(databaseSizeBeforeCreate + 1);
        Upazila testUpazila = upazilaList.get(upazilaList.size() - 1);
        assertThat(testUpazila.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testUpazila.getEstdDate()).isEqualTo(DEFAULT_ESTD_DATE);
    }

    @Test
    @Transactional
    public void createUpazilaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = upazilaRepository.findAll().size();

        // Create the Upazila with an existing ID
        upazila.setId(1L);
        UpazilaDTO upazilaDTO = upazilaMapper.toDto(upazila);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUpazilaMockMvc.perform(post("/api/upazilas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(upazilaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Upazila in the database
        List<Upazila> upazilaList = upazilaRepository.findAll();
        assertThat(upazilaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = upazilaRepository.findAll().size();
        // set the field null
        upazila.setName(null);

        // Create the Upazila, which fails.
        UpazilaDTO upazilaDTO = upazilaMapper.toDto(upazila);

        restUpazilaMockMvc.perform(post("/api/upazilas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(upazilaDTO)))
            .andExpect(status().isBadRequest());

        List<Upazila> upazilaList = upazilaRepository.findAll();
        assertThat(upazilaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllUpazilas() throws Exception {
        // Initialize the database
        upazilaRepository.saveAndFlush(upazila);

        // Get all the upazilaList
        restUpazilaMockMvc.perform(get("/api/upazilas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(upazila.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].estdDate").value(hasItem(DEFAULT_ESTD_DATE.toString())));
    }

    @Test
    @Transactional
    public void getUpazila() throws Exception {
        // Initialize the database
        upazilaRepository.saveAndFlush(upazila);

        // Get the upazila
        restUpazilaMockMvc.perform(get("/api/upazilas/{id}", upazila.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(upazila.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.estdDate").value(DEFAULT_ESTD_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingUpazila() throws Exception {
        // Get the upazila
        restUpazilaMockMvc.perform(get("/api/upazilas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUpazila() throws Exception {
        // Initialize the database
        upazilaRepository.saveAndFlush(upazila);
        int databaseSizeBeforeUpdate = upazilaRepository.findAll().size();

        // Update the upazila
        Upazila updatedUpazila = upazilaRepository.findOne(upazila.getId());
        // Disconnect from session so that the updates on updatedUpazila are not directly saved in db
        em.detach(updatedUpazila);
        updatedUpazila
            .name(UPDATED_NAME)
            .estdDate(UPDATED_ESTD_DATE);
        UpazilaDTO upazilaDTO = upazilaMapper.toDto(updatedUpazila);

        restUpazilaMockMvc.perform(put("/api/upazilas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(upazilaDTO)))
            .andExpect(status().isOk());

        // Validate the Upazila in the database
        List<Upazila> upazilaList = upazilaRepository.findAll();
        assertThat(upazilaList).hasSize(databaseSizeBeforeUpdate);
        Upazila testUpazila = upazilaList.get(upazilaList.size() - 1);
        assertThat(testUpazila.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testUpazila.getEstdDate()).isEqualTo(UPDATED_ESTD_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingUpazila() throws Exception {
        int databaseSizeBeforeUpdate = upazilaRepository.findAll().size();

        // Create the Upazila
        UpazilaDTO upazilaDTO = upazilaMapper.toDto(upazila);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restUpazilaMockMvc.perform(put("/api/upazilas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(upazilaDTO)))
            .andExpect(status().isCreated());

        // Validate the Upazila in the database
        List<Upazila> upazilaList = upazilaRepository.findAll();
        assertThat(upazilaList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteUpazila() throws Exception {
        // Initialize the database
        upazilaRepository.saveAndFlush(upazila);
        int databaseSizeBeforeDelete = upazilaRepository.findAll().size();

        // Get the upazila
        restUpazilaMockMvc.perform(delete("/api/upazilas/{id}", upazila.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Upazila> upazilaList = upazilaRepository.findAll();
        assertThat(upazilaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Upazila.class);
        Upazila upazila1 = new Upazila();
        upazila1.setId(1L);
        Upazila upazila2 = new Upazila();
        upazila2.setId(upazila1.getId());
        assertThat(upazila1).isEqualTo(upazila2);
        upazila2.setId(2L);
        assertThat(upazila1).isNotEqualTo(upazila2);
        upazila1.setId(null);
        assertThat(upazila1).isNotEqualTo(upazila2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(UpazilaDTO.class);
        UpazilaDTO upazilaDTO1 = new UpazilaDTO();
        upazilaDTO1.setId(1L);
        UpazilaDTO upazilaDTO2 = new UpazilaDTO();
        assertThat(upazilaDTO1).isNotEqualTo(upazilaDTO2);
        upazilaDTO2.setId(upazilaDTO1.getId());
        assertThat(upazilaDTO1).isEqualTo(upazilaDTO2);
        upazilaDTO2.setId(2L);
        assertThat(upazilaDTO1).isNotEqualTo(upazilaDTO2);
        upazilaDTO1.setId(null);
        assertThat(upazilaDTO1).isNotEqualTo(upazilaDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(upazilaMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(upazilaMapper.fromId(null)).isNull();
    }
}
