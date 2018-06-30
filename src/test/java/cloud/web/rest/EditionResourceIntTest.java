package cloud.web.rest;

import cloud.CloudApp;

import cloud.domain.Edition;
import cloud.repository.EditionRepository;
import cloud.service.EditionService;
import cloud.service.dto.EditionDTO;
import cloud.service.mapper.EditionMapper;
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
 * Test class for the EditionResource REST controller.
 *
 * @see EditionResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CloudApp.class)
public class EditionResourceIntTest {

    private static final String DEFAULT_EDITION_NAME = "AAAAAAAAAA";
    private static final String UPDATED_EDITION_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_TOTAL_COPIES = "AAAAAAAAAA";
    private static final String UPDATED_TOTAL_COPIES = "BBBBBBBBBB";

    private static final String DEFAULT_COMPENSATION = "AAAAAAAAAA";
    private static final String UPDATED_COMPENSATION = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_CREATE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATE_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_UPDATE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_UPDATE_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_CREATE_BY = 1;
    private static final Integer UPDATED_CREATE_BY = 2;

    private static final Integer DEFAULT_UPDATE_BY = 1;
    private static final Integer UPDATED_UPDATE_BY = 2;

    @Autowired
    private EditionRepository editionRepository;

    @Autowired
    private EditionMapper editionMapper;

    @Autowired
    private EditionService editionService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restEditionMockMvc;

    private Edition edition;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EditionResource editionResource = new EditionResource(editionService);
        this.restEditionMockMvc = MockMvcBuilders.standaloneSetup(editionResource)
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
    public static Edition createEntity(EntityManager em) {
        Edition edition = new Edition()
            .editionName(DEFAULT_EDITION_NAME)
            .totalCopies(DEFAULT_TOTAL_COPIES)
            .compensation(DEFAULT_COMPENSATION)
            .createDate(DEFAULT_CREATE_DATE)
            .updateDate(DEFAULT_UPDATE_DATE)
            .createBy(DEFAULT_CREATE_BY)
            .updateBy(DEFAULT_UPDATE_BY);
        return edition;
    }

    @Before
    public void initTest() {
        edition = createEntity(em);
    }

    @Test
    @Transactional
    public void createEdition() throws Exception {
        int databaseSizeBeforeCreate = editionRepository.findAll().size();

        // Create the Edition
        EditionDTO editionDTO = editionMapper.toDto(edition);
        restEditionMockMvc.perform(post("/api/editions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(editionDTO)))
            .andExpect(status().isCreated());

        // Validate the Edition in the database
        List<Edition> editionList = editionRepository.findAll();
        assertThat(editionList).hasSize(databaseSizeBeforeCreate + 1);
        Edition testEdition = editionList.get(editionList.size() - 1);
        assertThat(testEdition.getEditionName()).isEqualTo(DEFAULT_EDITION_NAME);
        assertThat(testEdition.getTotalCopies()).isEqualTo(DEFAULT_TOTAL_COPIES);
        assertThat(testEdition.getCompensation()).isEqualTo(DEFAULT_COMPENSATION);
        assertThat(testEdition.getCreateDate()).isEqualTo(DEFAULT_CREATE_DATE);
        assertThat(testEdition.getUpdateDate()).isEqualTo(DEFAULT_UPDATE_DATE);
        assertThat(testEdition.getCreateBy()).isEqualTo(DEFAULT_CREATE_BY);
        assertThat(testEdition.getUpdateBy()).isEqualTo(DEFAULT_UPDATE_BY);
    }

    @Test
    @Transactional
    public void createEditionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = editionRepository.findAll().size();

        // Create the Edition with an existing ID
        edition.setId(1L);
        EditionDTO editionDTO = editionMapper.toDto(edition);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEditionMockMvc.perform(post("/api/editions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(editionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Edition in the database
        List<Edition> editionList = editionRepository.findAll();
        assertThat(editionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkEditionNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = editionRepository.findAll().size();
        // set the field null
        edition.setEditionName(null);

        // Create the Edition, which fails.
        EditionDTO editionDTO = editionMapper.toDto(edition);

        restEditionMockMvc.perform(post("/api/editions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(editionDTO)))
            .andExpect(status().isBadRequest());

        List<Edition> editionList = editionRepository.findAll();
        assertThat(editionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllEditions() throws Exception {
        // Initialize the database
        editionRepository.saveAndFlush(edition);

        // Get all the editionList
        restEditionMockMvc.perform(get("/api/editions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(edition.getId().intValue())))
            .andExpect(jsonPath("$.[*].editionName").value(hasItem(DEFAULT_EDITION_NAME.toString())))
            .andExpect(jsonPath("$.[*].totalCopies").value(hasItem(DEFAULT_TOTAL_COPIES.toString())))
            .andExpect(jsonPath("$.[*].compensation").value(hasItem(DEFAULT_COMPENSATION.toString())))
            .andExpect(jsonPath("$.[*].createDate").value(hasItem(DEFAULT_CREATE_DATE.toString())))
            .andExpect(jsonPath("$.[*].updateDate").value(hasItem(DEFAULT_UPDATE_DATE.toString())))
            .andExpect(jsonPath("$.[*].createBy").value(hasItem(DEFAULT_CREATE_BY)))
            .andExpect(jsonPath("$.[*].updateBy").value(hasItem(DEFAULT_UPDATE_BY)));
    }

    @Test
    @Transactional
    public void getEdition() throws Exception {
        // Initialize the database
        editionRepository.saveAndFlush(edition);

        // Get the edition
        restEditionMockMvc.perform(get("/api/editions/{id}", edition.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(edition.getId().intValue()))
            .andExpect(jsonPath("$.editionName").value(DEFAULT_EDITION_NAME.toString()))
            .andExpect(jsonPath("$.totalCopies").value(DEFAULT_TOTAL_COPIES.toString()))
            .andExpect(jsonPath("$.compensation").value(DEFAULT_COMPENSATION.toString()))
            .andExpect(jsonPath("$.createDate").value(DEFAULT_CREATE_DATE.toString()))
            .andExpect(jsonPath("$.updateDate").value(DEFAULT_UPDATE_DATE.toString()))
            .andExpect(jsonPath("$.createBy").value(DEFAULT_CREATE_BY))
            .andExpect(jsonPath("$.updateBy").value(DEFAULT_UPDATE_BY));
    }

    @Test
    @Transactional
    public void getNonExistingEdition() throws Exception {
        // Get the edition
        restEditionMockMvc.perform(get("/api/editions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEdition() throws Exception {
        // Initialize the database
        editionRepository.saveAndFlush(edition);
        int databaseSizeBeforeUpdate = editionRepository.findAll().size();

        // Update the edition
        Edition updatedEdition = editionRepository.findOne(edition.getId());
        // Disconnect from session so that the updates on updatedEdition are not directly saved in db
        em.detach(updatedEdition);
        updatedEdition
            .editionName(UPDATED_EDITION_NAME)
            .totalCopies(UPDATED_TOTAL_COPIES)
            .compensation(UPDATED_COMPENSATION)
            .createDate(UPDATED_CREATE_DATE)
            .updateDate(UPDATED_UPDATE_DATE)
            .createBy(UPDATED_CREATE_BY)
            .updateBy(UPDATED_UPDATE_BY);
        EditionDTO editionDTO = editionMapper.toDto(updatedEdition);

        restEditionMockMvc.perform(put("/api/editions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(editionDTO)))
            .andExpect(status().isOk());

        // Validate the Edition in the database
        List<Edition> editionList = editionRepository.findAll();
        assertThat(editionList).hasSize(databaseSizeBeforeUpdate);
        Edition testEdition = editionList.get(editionList.size() - 1);
        assertThat(testEdition.getEditionName()).isEqualTo(UPDATED_EDITION_NAME);
        assertThat(testEdition.getTotalCopies()).isEqualTo(UPDATED_TOTAL_COPIES);
        assertThat(testEdition.getCompensation()).isEqualTo(UPDATED_COMPENSATION);
        assertThat(testEdition.getCreateDate()).isEqualTo(UPDATED_CREATE_DATE);
        assertThat(testEdition.getUpdateDate()).isEqualTo(UPDATED_UPDATE_DATE);
        assertThat(testEdition.getCreateBy()).isEqualTo(UPDATED_CREATE_BY);
        assertThat(testEdition.getUpdateBy()).isEqualTo(UPDATED_UPDATE_BY);
    }

    @Test
    @Transactional
    public void updateNonExistingEdition() throws Exception {
        int databaseSizeBeforeUpdate = editionRepository.findAll().size();

        // Create the Edition
        EditionDTO editionDTO = editionMapper.toDto(edition);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restEditionMockMvc.perform(put("/api/editions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(editionDTO)))
            .andExpect(status().isCreated());

        // Validate the Edition in the database
        List<Edition> editionList = editionRepository.findAll();
        assertThat(editionList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteEdition() throws Exception {
        // Initialize the database
        editionRepository.saveAndFlush(edition);
        int databaseSizeBeforeDelete = editionRepository.findAll().size();

        // Get the edition
        restEditionMockMvc.perform(delete("/api/editions/{id}", edition.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Edition> editionList = editionRepository.findAll();
        assertThat(editionList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Edition.class);
        Edition edition1 = new Edition();
        edition1.setId(1L);
        Edition edition2 = new Edition();
        edition2.setId(edition1.getId());
        assertThat(edition1).isEqualTo(edition2);
        edition2.setId(2L);
        assertThat(edition1).isNotEqualTo(edition2);
        edition1.setId(null);
        assertThat(edition1).isNotEqualTo(edition2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EditionDTO.class);
        EditionDTO editionDTO1 = new EditionDTO();
        editionDTO1.setId(1L);
        EditionDTO editionDTO2 = new EditionDTO();
        assertThat(editionDTO1).isNotEqualTo(editionDTO2);
        editionDTO2.setId(editionDTO1.getId());
        assertThat(editionDTO1).isEqualTo(editionDTO2);
        editionDTO2.setId(2L);
        assertThat(editionDTO1).isNotEqualTo(editionDTO2);
        editionDTO1.setId(null);
        assertThat(editionDTO1).isNotEqualTo(editionDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(editionMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(editionMapper.fromId(null)).isNull();
    }
}
