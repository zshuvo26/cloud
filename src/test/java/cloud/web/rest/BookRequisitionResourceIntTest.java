package cloud.web.rest;

import cloud.CloudApp;

import cloud.domain.BookRequisition;
import cloud.repository.BookRequisitionRepository;
import cloud.service.BookRequisitionService;
import cloud.service.dto.BookRequisitionDTO;
import cloud.service.mapper.BookRequisitionMapper;
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
 * Test class for the BookRequisitionResource REST controller.
 *
 * @see BookRequisitionResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CloudApp.class)
public class BookRequisitionResourceIntTest {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_EDITION = "AAAAAAAAAA";
    private static final String UPDATED_EDITION = "BBBBBBBBBB";

    private static final String DEFAULT_AUTHOR_NAME = "AAAAAAAAAA";
    private static final String UPDATED_AUTHOR_NAME = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_CREATE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATE_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_UPDATE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_UPDATE_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_CREATE_BY = 1;
    private static final Integer UPDATED_CREATE_BY = 2;

    private static final Integer DEFAULT_UPDATE_BY = 1;
    private static final Integer UPDATED_UPDATE_BY = 2;

    @Autowired
    private BookRequisitionRepository bookRequisitionRepository;

    @Autowired
    private BookRequisitionMapper bookRequisitionMapper;

    @Autowired
    private BookRequisitionService bookRequisitionService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restBookRequisitionMockMvc;

    private BookRequisition bookRequisition;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final BookRequisitionResource bookRequisitionResource = new BookRequisitionResource(bookRequisitionService);
        this.restBookRequisitionMockMvc = MockMvcBuilders.standaloneSetup(bookRequisitionResource)
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
    public static BookRequisition createEntity(EntityManager em) {
        BookRequisition bookRequisition = new BookRequisition()
            .title(DEFAULT_TITLE)
            .edition(DEFAULT_EDITION)
            .authorName(DEFAULT_AUTHOR_NAME)
            .createDate(DEFAULT_CREATE_DATE)
            .updateDate(DEFAULT_UPDATE_DATE)
            .createBy(DEFAULT_CREATE_BY)
            .updateBy(DEFAULT_UPDATE_BY);
        return bookRequisition;
    }

    @Before
    public void initTest() {
        bookRequisition = createEntity(em);
    }

    @Test
    @Transactional
    public void createBookRequisition() throws Exception {
        int databaseSizeBeforeCreate = bookRequisitionRepository.findAll().size();

        // Create the BookRequisition
        BookRequisitionDTO bookRequisitionDTO = bookRequisitionMapper.toDto(bookRequisition);
        restBookRequisitionMockMvc.perform(post("/api/book-requisitions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bookRequisitionDTO)))
            .andExpect(status().isCreated());

        // Validate the BookRequisition in the database
        List<BookRequisition> bookRequisitionList = bookRequisitionRepository.findAll();
        assertThat(bookRequisitionList).hasSize(databaseSizeBeforeCreate + 1);
        BookRequisition testBookRequisition = bookRequisitionList.get(bookRequisitionList.size() - 1);
        assertThat(testBookRequisition.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testBookRequisition.getEdition()).isEqualTo(DEFAULT_EDITION);
        assertThat(testBookRequisition.getAuthorName()).isEqualTo(DEFAULT_AUTHOR_NAME);
        assertThat(testBookRequisition.getCreateDate()).isEqualTo(DEFAULT_CREATE_DATE);
        assertThat(testBookRequisition.getUpdateDate()).isEqualTo(DEFAULT_UPDATE_DATE);
        assertThat(testBookRequisition.getCreateBy()).isEqualTo(DEFAULT_CREATE_BY);
        assertThat(testBookRequisition.getUpdateBy()).isEqualTo(DEFAULT_UPDATE_BY);
    }

    @Test
    @Transactional
    public void createBookRequisitionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = bookRequisitionRepository.findAll().size();

        // Create the BookRequisition with an existing ID
        bookRequisition.setId(1L);
        BookRequisitionDTO bookRequisitionDTO = bookRequisitionMapper.toDto(bookRequisition);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBookRequisitionMockMvc.perform(post("/api/book-requisitions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bookRequisitionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the BookRequisition in the database
        List<BookRequisition> bookRequisitionList = bookRequisitionRepository.findAll();
        assertThat(bookRequisitionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkTitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = bookRequisitionRepository.findAll().size();
        // set the field null
        bookRequisition.setTitle(null);

        // Create the BookRequisition, which fails.
        BookRequisitionDTO bookRequisitionDTO = bookRequisitionMapper.toDto(bookRequisition);

        restBookRequisitionMockMvc.perform(post("/api/book-requisitions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bookRequisitionDTO)))
            .andExpect(status().isBadRequest());

        List<BookRequisition> bookRequisitionList = bookRequisitionRepository.findAll();
        assertThat(bookRequisitionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllBookRequisitions() throws Exception {
        // Initialize the database
        bookRequisitionRepository.saveAndFlush(bookRequisition);

        // Get all the bookRequisitionList
        restBookRequisitionMockMvc.perform(get("/api/book-requisitions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bookRequisition.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].edition").value(hasItem(DEFAULT_EDITION.toString())))
            .andExpect(jsonPath("$.[*].authorName").value(hasItem(DEFAULT_AUTHOR_NAME.toString())))
            .andExpect(jsonPath("$.[*].createDate").value(hasItem(DEFAULT_CREATE_DATE.toString())))
            .andExpect(jsonPath("$.[*].updateDate").value(hasItem(DEFAULT_UPDATE_DATE.toString())))
            .andExpect(jsonPath("$.[*].createBy").value(hasItem(DEFAULT_CREATE_BY)))
            .andExpect(jsonPath("$.[*].updateBy").value(hasItem(DEFAULT_UPDATE_BY)));
    }

    @Test
    @Transactional
    public void getBookRequisition() throws Exception {
        // Initialize the database
        bookRequisitionRepository.saveAndFlush(bookRequisition);

        // Get the bookRequisition
        restBookRequisitionMockMvc.perform(get("/api/book-requisitions/{id}", bookRequisition.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(bookRequisition.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
            .andExpect(jsonPath("$.edition").value(DEFAULT_EDITION.toString()))
            .andExpect(jsonPath("$.authorName").value(DEFAULT_AUTHOR_NAME.toString()))
            .andExpect(jsonPath("$.createDate").value(DEFAULT_CREATE_DATE.toString()))
            .andExpect(jsonPath("$.updateDate").value(DEFAULT_UPDATE_DATE.toString()))
            .andExpect(jsonPath("$.createBy").value(DEFAULT_CREATE_BY))
            .andExpect(jsonPath("$.updateBy").value(DEFAULT_UPDATE_BY));
    }

    @Test
    @Transactional
    public void getNonExistingBookRequisition() throws Exception {
        // Get the bookRequisition
        restBookRequisitionMockMvc.perform(get("/api/book-requisitions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBookRequisition() throws Exception {
        // Initialize the database
        bookRequisitionRepository.saveAndFlush(bookRequisition);
        int databaseSizeBeforeUpdate = bookRequisitionRepository.findAll().size();

        // Update the bookRequisition
        BookRequisition updatedBookRequisition = bookRequisitionRepository.findOne(bookRequisition.getId());
        // Disconnect from session so that the updates on updatedBookRequisition are not directly saved in db
        em.detach(updatedBookRequisition);
        updatedBookRequisition
            .title(UPDATED_TITLE)
            .edition(UPDATED_EDITION)
            .authorName(UPDATED_AUTHOR_NAME)
            .createDate(UPDATED_CREATE_DATE)
            .updateDate(UPDATED_UPDATE_DATE)
            .createBy(UPDATED_CREATE_BY)
            .updateBy(UPDATED_UPDATE_BY);
        BookRequisitionDTO bookRequisitionDTO = bookRequisitionMapper.toDto(updatedBookRequisition);

        restBookRequisitionMockMvc.perform(put("/api/book-requisitions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bookRequisitionDTO)))
            .andExpect(status().isOk());

        // Validate the BookRequisition in the database
        List<BookRequisition> bookRequisitionList = bookRequisitionRepository.findAll();
        assertThat(bookRequisitionList).hasSize(databaseSizeBeforeUpdate);
        BookRequisition testBookRequisition = bookRequisitionList.get(bookRequisitionList.size() - 1);
        assertThat(testBookRequisition.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testBookRequisition.getEdition()).isEqualTo(UPDATED_EDITION);
        assertThat(testBookRequisition.getAuthorName()).isEqualTo(UPDATED_AUTHOR_NAME);
        assertThat(testBookRequisition.getCreateDate()).isEqualTo(UPDATED_CREATE_DATE);
        assertThat(testBookRequisition.getUpdateDate()).isEqualTo(UPDATED_UPDATE_DATE);
        assertThat(testBookRequisition.getCreateBy()).isEqualTo(UPDATED_CREATE_BY);
        assertThat(testBookRequisition.getUpdateBy()).isEqualTo(UPDATED_UPDATE_BY);
    }

    @Test
    @Transactional
    public void updateNonExistingBookRequisition() throws Exception {
        int databaseSizeBeforeUpdate = bookRequisitionRepository.findAll().size();

        // Create the BookRequisition
        BookRequisitionDTO bookRequisitionDTO = bookRequisitionMapper.toDto(bookRequisition);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restBookRequisitionMockMvc.perform(put("/api/book-requisitions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bookRequisitionDTO)))
            .andExpect(status().isCreated());

        // Validate the BookRequisition in the database
        List<BookRequisition> bookRequisitionList = bookRequisitionRepository.findAll();
        assertThat(bookRequisitionList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteBookRequisition() throws Exception {
        // Initialize the database
        bookRequisitionRepository.saveAndFlush(bookRequisition);
        int databaseSizeBeforeDelete = bookRequisitionRepository.findAll().size();

        // Get the bookRequisition
        restBookRequisitionMockMvc.perform(delete("/api/book-requisitions/{id}", bookRequisition.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<BookRequisition> bookRequisitionList = bookRequisitionRepository.findAll();
        assertThat(bookRequisitionList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BookRequisition.class);
        BookRequisition bookRequisition1 = new BookRequisition();
        bookRequisition1.setId(1L);
        BookRequisition bookRequisition2 = new BookRequisition();
        bookRequisition2.setId(bookRequisition1.getId());
        assertThat(bookRequisition1).isEqualTo(bookRequisition2);
        bookRequisition2.setId(2L);
        assertThat(bookRequisition1).isNotEqualTo(bookRequisition2);
        bookRequisition1.setId(null);
        assertThat(bookRequisition1).isNotEqualTo(bookRequisition2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(BookRequisitionDTO.class);
        BookRequisitionDTO bookRequisitionDTO1 = new BookRequisitionDTO();
        bookRequisitionDTO1.setId(1L);
        BookRequisitionDTO bookRequisitionDTO2 = new BookRequisitionDTO();
        assertThat(bookRequisitionDTO1).isNotEqualTo(bookRequisitionDTO2);
        bookRequisitionDTO2.setId(bookRequisitionDTO1.getId());
        assertThat(bookRequisitionDTO1).isEqualTo(bookRequisitionDTO2);
        bookRequisitionDTO2.setId(2L);
        assertThat(bookRequisitionDTO1).isNotEqualTo(bookRequisitionDTO2);
        bookRequisitionDTO1.setId(null);
        assertThat(bookRequisitionDTO1).isNotEqualTo(bookRequisitionDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(bookRequisitionMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(bookRequisitionMapper.fromId(null)).isNull();
    }
}
