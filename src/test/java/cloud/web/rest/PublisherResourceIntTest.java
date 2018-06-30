package cloud.web.rest;

import cloud.CloudApp;

import cloud.domain.Publisher;
import cloud.repository.PublisherRepository;
import cloud.service.PublisherService;
import cloud.service.dto.PublisherDTO;
import cloud.service.mapper.PublisherMapper;
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
 * Test class for the PublisherResource REST controller.
 *
 * @see PublisherResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CloudApp.class)
public class PublisherResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_PLACE = "AAAAAAAAAA";
    private static final String UPDATED_PLACE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_CREATE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATE_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_UPDATE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_UPDATE_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_CREATE_BY = 1;
    private static final Integer UPDATED_CREATE_BY = 2;

    private static final Integer DEFAULT_UPDATE_BY = 1;
    private static final Integer UPDATED_UPDATE_BY = 2;

    @Autowired
    private PublisherRepository publisherRepository;

    @Autowired
    private PublisherMapper publisherMapper;

    @Autowired
    private PublisherService publisherService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPublisherMockMvc;

    private Publisher publisher;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PublisherResource publisherResource = new PublisherResource(publisherService);
        this.restPublisherMockMvc = MockMvcBuilders.standaloneSetup(publisherResource)
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
    public static Publisher createEntity(EntityManager em) {
        Publisher publisher = new Publisher()
            .name(DEFAULT_NAME)
            .place(DEFAULT_PLACE)
            .createDate(DEFAULT_CREATE_DATE)
            .updateDate(DEFAULT_UPDATE_DATE)
            .createBy(DEFAULT_CREATE_BY)
            .updateBy(DEFAULT_UPDATE_BY);
        return publisher;
    }

    @Before
    public void initTest() {
        publisher = createEntity(em);
    }

    @Test
    @Transactional
    public void createPublisher() throws Exception {
        int databaseSizeBeforeCreate = publisherRepository.findAll().size();

        // Create the Publisher
        PublisherDTO publisherDTO = publisherMapper.toDto(publisher);
        restPublisherMockMvc.perform(post("/api/publishers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(publisherDTO)))
            .andExpect(status().isCreated());

        // Validate the Publisher in the database
        List<Publisher> publisherList = publisherRepository.findAll();
        assertThat(publisherList).hasSize(databaseSizeBeforeCreate + 1);
        Publisher testPublisher = publisherList.get(publisherList.size() - 1);
        assertThat(testPublisher.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testPublisher.getPlace()).isEqualTo(DEFAULT_PLACE);
        assertThat(testPublisher.getCreateDate()).isEqualTo(DEFAULT_CREATE_DATE);
        assertThat(testPublisher.getUpdateDate()).isEqualTo(DEFAULT_UPDATE_DATE);
        assertThat(testPublisher.getCreateBy()).isEqualTo(DEFAULT_CREATE_BY);
        assertThat(testPublisher.getUpdateBy()).isEqualTo(DEFAULT_UPDATE_BY);
    }

    @Test
    @Transactional
    public void createPublisherWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = publisherRepository.findAll().size();

        // Create the Publisher with an existing ID
        publisher.setId(1L);
        PublisherDTO publisherDTO = publisherMapper.toDto(publisher);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPublisherMockMvc.perform(post("/api/publishers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(publisherDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Publisher in the database
        List<Publisher> publisherList = publisherRepository.findAll();
        assertThat(publisherList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = publisherRepository.findAll().size();
        // set the field null
        publisher.setName(null);

        // Create the Publisher, which fails.
        PublisherDTO publisherDTO = publisherMapper.toDto(publisher);

        restPublisherMockMvc.perform(post("/api/publishers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(publisherDTO)))
            .andExpect(status().isBadRequest());

        List<Publisher> publisherList = publisherRepository.findAll();
        assertThat(publisherList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPublishers() throws Exception {
        // Initialize the database
        publisherRepository.saveAndFlush(publisher);

        // Get all the publisherList
        restPublisherMockMvc.perform(get("/api/publishers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(publisher.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].place").value(hasItem(DEFAULT_PLACE.toString())))
            .andExpect(jsonPath("$.[*].createDate").value(hasItem(DEFAULT_CREATE_DATE.toString())))
            .andExpect(jsonPath("$.[*].updateDate").value(hasItem(DEFAULT_UPDATE_DATE.toString())))
            .andExpect(jsonPath("$.[*].createBy").value(hasItem(DEFAULT_CREATE_BY)))
            .andExpect(jsonPath("$.[*].updateBy").value(hasItem(DEFAULT_UPDATE_BY)));
    }

    @Test
    @Transactional
    public void getPublisher() throws Exception {
        // Initialize the database
        publisherRepository.saveAndFlush(publisher);

        // Get the publisher
        restPublisherMockMvc.perform(get("/api/publishers/{id}", publisher.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(publisher.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.place").value(DEFAULT_PLACE.toString()))
            .andExpect(jsonPath("$.createDate").value(DEFAULT_CREATE_DATE.toString()))
            .andExpect(jsonPath("$.updateDate").value(DEFAULT_UPDATE_DATE.toString()))
            .andExpect(jsonPath("$.createBy").value(DEFAULT_CREATE_BY))
            .andExpect(jsonPath("$.updateBy").value(DEFAULT_UPDATE_BY));
    }

    @Test
    @Transactional
    public void getNonExistingPublisher() throws Exception {
        // Get the publisher
        restPublisherMockMvc.perform(get("/api/publishers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePublisher() throws Exception {
        // Initialize the database
        publisherRepository.saveAndFlush(publisher);
        int databaseSizeBeforeUpdate = publisherRepository.findAll().size();

        // Update the publisher
        Publisher updatedPublisher = publisherRepository.findOne(publisher.getId());
        // Disconnect from session so that the updates on updatedPublisher are not directly saved in db
        em.detach(updatedPublisher);
        updatedPublisher
            .name(UPDATED_NAME)
            .place(UPDATED_PLACE)
            .createDate(UPDATED_CREATE_DATE)
            .updateDate(UPDATED_UPDATE_DATE)
            .createBy(UPDATED_CREATE_BY)
            .updateBy(UPDATED_UPDATE_BY);
        PublisherDTO publisherDTO = publisherMapper.toDto(updatedPublisher);

        restPublisherMockMvc.perform(put("/api/publishers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(publisherDTO)))
            .andExpect(status().isOk());

        // Validate the Publisher in the database
        List<Publisher> publisherList = publisherRepository.findAll();
        assertThat(publisherList).hasSize(databaseSizeBeforeUpdate);
        Publisher testPublisher = publisherList.get(publisherList.size() - 1);
        assertThat(testPublisher.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPublisher.getPlace()).isEqualTo(UPDATED_PLACE);
        assertThat(testPublisher.getCreateDate()).isEqualTo(UPDATED_CREATE_DATE);
        assertThat(testPublisher.getUpdateDate()).isEqualTo(UPDATED_UPDATE_DATE);
        assertThat(testPublisher.getCreateBy()).isEqualTo(UPDATED_CREATE_BY);
        assertThat(testPublisher.getUpdateBy()).isEqualTo(UPDATED_UPDATE_BY);
    }

    @Test
    @Transactional
    public void updateNonExistingPublisher() throws Exception {
        int databaseSizeBeforeUpdate = publisherRepository.findAll().size();

        // Create the Publisher
        PublisherDTO publisherDTO = publisherMapper.toDto(publisher);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPublisherMockMvc.perform(put("/api/publishers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(publisherDTO)))
            .andExpect(status().isCreated());

        // Validate the Publisher in the database
        List<Publisher> publisherList = publisherRepository.findAll();
        assertThat(publisherList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePublisher() throws Exception {
        // Initialize the database
        publisherRepository.saveAndFlush(publisher);
        int databaseSizeBeforeDelete = publisherRepository.findAll().size();

        // Get the publisher
        restPublisherMockMvc.perform(delete("/api/publishers/{id}", publisher.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Publisher> publisherList = publisherRepository.findAll();
        assertThat(publisherList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Publisher.class);
        Publisher publisher1 = new Publisher();
        publisher1.setId(1L);
        Publisher publisher2 = new Publisher();
        publisher2.setId(publisher1.getId());
        assertThat(publisher1).isEqualTo(publisher2);
        publisher2.setId(2L);
        assertThat(publisher1).isNotEqualTo(publisher2);
        publisher1.setId(null);
        assertThat(publisher1).isNotEqualTo(publisher2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PublisherDTO.class);
        PublisherDTO publisherDTO1 = new PublisherDTO();
        publisherDTO1.setId(1L);
        PublisherDTO publisherDTO2 = new PublisherDTO();
        assertThat(publisherDTO1).isNotEqualTo(publisherDTO2);
        publisherDTO2.setId(publisherDTO1.getId());
        assertThat(publisherDTO1).isEqualTo(publisherDTO2);
        publisherDTO2.setId(2L);
        assertThat(publisherDTO1).isNotEqualTo(publisherDTO2);
        publisherDTO1.setId(null);
        assertThat(publisherDTO1).isNotEqualTo(publisherDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(publisherMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(publisherMapper.fromId(null)).isNull();
    }
}
