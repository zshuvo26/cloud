package cloud.web.rest;

import cloud.CloudApp;

import cloud.domain.BookIssue;
import cloud.repository.BookIssueRepository;
import cloud.service.BookIssueService;
import cloud.service.dto.BookIssueDTO;
import cloud.service.mapper.BookIssueMapper;
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
 * Test class for the BookIssueResource REST controller.
 *
 * @see BookIssueResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CloudApp.class)
public class BookIssueResourceIntTest {

    private static final String DEFAULT_NO_OF_COPIES = "AAAAAAAAAA";
    private static final String UPDATED_NO_OF_COPIES = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_RETURN_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_RETURN_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_CREATE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATE_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_UPDATE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_UPDATE_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_CREATE_BY = 1;
    private static final Integer UPDATED_CREATE_BY = 2;

    private static final Integer DEFAULT_UPDATE_BY = 1;
    private static final Integer UPDATED_UPDATE_BY = 2;

    @Autowired
    private BookIssueRepository bookIssueRepository;

    @Autowired
    private BookIssueMapper bookIssueMapper;

    @Autowired
    private BookIssueService bookIssueService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restBookIssueMockMvc;

    private BookIssue bookIssue;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final BookIssueResource bookIssueResource = new BookIssueResource(bookIssueService);
        this.restBookIssueMockMvc = MockMvcBuilders.standaloneSetup(bookIssueResource)
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
    public static BookIssue createEntity(EntityManager em) {
        BookIssue bookIssue = new BookIssue()
            .noOfCopies(DEFAULT_NO_OF_COPIES)
            .returnDate(DEFAULT_RETURN_DATE)
            .createDate(DEFAULT_CREATE_DATE)
            .updateDate(DEFAULT_UPDATE_DATE)
            .createBy(DEFAULT_CREATE_BY)
            .updateBy(DEFAULT_UPDATE_BY);
        return bookIssue;
    }

    @Before
    public void initTest() {
        bookIssue = createEntity(em);
    }

    @Test
    @Transactional
    public void createBookIssue() throws Exception {
        int databaseSizeBeforeCreate = bookIssueRepository.findAll().size();

        // Create the BookIssue
        BookIssueDTO bookIssueDTO = bookIssueMapper.toDto(bookIssue);
        restBookIssueMockMvc.perform(post("/api/book-issues")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bookIssueDTO)))
            .andExpect(status().isCreated());

        // Validate the BookIssue in the database
        List<BookIssue> bookIssueList = bookIssueRepository.findAll();
        assertThat(bookIssueList).hasSize(databaseSizeBeforeCreate + 1);
        BookIssue testBookIssue = bookIssueList.get(bookIssueList.size() - 1);
        assertThat(testBookIssue.getNoOfCopies()).isEqualTo(DEFAULT_NO_OF_COPIES);
        assertThat(testBookIssue.getReturnDate()).isEqualTo(DEFAULT_RETURN_DATE);
        assertThat(testBookIssue.getCreateDate()).isEqualTo(DEFAULT_CREATE_DATE);
        assertThat(testBookIssue.getUpdateDate()).isEqualTo(DEFAULT_UPDATE_DATE);
        assertThat(testBookIssue.getCreateBy()).isEqualTo(DEFAULT_CREATE_BY);
        assertThat(testBookIssue.getUpdateBy()).isEqualTo(DEFAULT_UPDATE_BY);
    }

    @Test
    @Transactional
    public void createBookIssueWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = bookIssueRepository.findAll().size();

        // Create the BookIssue with an existing ID
        bookIssue.setId(1L);
        BookIssueDTO bookIssueDTO = bookIssueMapper.toDto(bookIssue);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBookIssueMockMvc.perform(post("/api/book-issues")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bookIssueDTO)))
            .andExpect(status().isBadRequest());

        // Validate the BookIssue in the database
        List<BookIssue> bookIssueList = bookIssueRepository.findAll();
        assertThat(bookIssueList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNoOfCopiesIsRequired() throws Exception {
        int databaseSizeBeforeTest = bookIssueRepository.findAll().size();
        // set the field null
        bookIssue.setNoOfCopies(null);

        // Create the BookIssue, which fails.
        BookIssueDTO bookIssueDTO = bookIssueMapper.toDto(bookIssue);

        restBookIssueMockMvc.perform(post("/api/book-issues")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bookIssueDTO)))
            .andExpect(status().isBadRequest());

        List<BookIssue> bookIssueList = bookIssueRepository.findAll();
        assertThat(bookIssueList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllBookIssues() throws Exception {
        // Initialize the database
        bookIssueRepository.saveAndFlush(bookIssue);

        // Get all the bookIssueList
        restBookIssueMockMvc.perform(get("/api/book-issues?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bookIssue.getId().intValue())))
            .andExpect(jsonPath("$.[*].noOfCopies").value(hasItem(DEFAULT_NO_OF_COPIES.toString())))
            .andExpect(jsonPath("$.[*].returnDate").value(hasItem(DEFAULT_RETURN_DATE.toString())))
            .andExpect(jsonPath("$.[*].createDate").value(hasItem(DEFAULT_CREATE_DATE.toString())))
            .andExpect(jsonPath("$.[*].updateDate").value(hasItem(DEFAULT_UPDATE_DATE.toString())))
            .andExpect(jsonPath("$.[*].createBy").value(hasItem(DEFAULT_CREATE_BY)))
            .andExpect(jsonPath("$.[*].updateBy").value(hasItem(DEFAULT_UPDATE_BY)));
    }

    @Test
    @Transactional
    public void getBookIssue() throws Exception {
        // Initialize the database
        bookIssueRepository.saveAndFlush(bookIssue);

        // Get the bookIssue
        restBookIssueMockMvc.perform(get("/api/book-issues/{id}", bookIssue.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(bookIssue.getId().intValue()))
            .andExpect(jsonPath("$.noOfCopies").value(DEFAULT_NO_OF_COPIES.toString()))
            .andExpect(jsonPath("$.returnDate").value(DEFAULT_RETURN_DATE.toString()))
            .andExpect(jsonPath("$.createDate").value(DEFAULT_CREATE_DATE.toString()))
            .andExpect(jsonPath("$.updateDate").value(DEFAULT_UPDATE_DATE.toString()))
            .andExpect(jsonPath("$.createBy").value(DEFAULT_CREATE_BY))
            .andExpect(jsonPath("$.updateBy").value(DEFAULT_UPDATE_BY));
    }

    @Test
    @Transactional
    public void getNonExistingBookIssue() throws Exception {
        // Get the bookIssue
        restBookIssueMockMvc.perform(get("/api/book-issues/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBookIssue() throws Exception {
        // Initialize the database
        bookIssueRepository.saveAndFlush(bookIssue);
        int databaseSizeBeforeUpdate = bookIssueRepository.findAll().size();

        // Update the bookIssue
        BookIssue updatedBookIssue = bookIssueRepository.findOne(bookIssue.getId());
        // Disconnect from session so that the updates on updatedBookIssue are not directly saved in db
        em.detach(updatedBookIssue);
        updatedBookIssue
            .noOfCopies(UPDATED_NO_OF_COPIES)
            .returnDate(UPDATED_RETURN_DATE)
            .createDate(UPDATED_CREATE_DATE)
            .updateDate(UPDATED_UPDATE_DATE)
            .createBy(UPDATED_CREATE_BY)
            .updateBy(UPDATED_UPDATE_BY);
        BookIssueDTO bookIssueDTO = bookIssueMapper.toDto(updatedBookIssue);

        restBookIssueMockMvc.perform(put("/api/book-issues")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bookIssueDTO)))
            .andExpect(status().isOk());

        // Validate the BookIssue in the database
        List<BookIssue> bookIssueList = bookIssueRepository.findAll();
        assertThat(bookIssueList).hasSize(databaseSizeBeforeUpdate);
        BookIssue testBookIssue = bookIssueList.get(bookIssueList.size() - 1);
        assertThat(testBookIssue.getNoOfCopies()).isEqualTo(UPDATED_NO_OF_COPIES);
        assertThat(testBookIssue.getReturnDate()).isEqualTo(UPDATED_RETURN_DATE);
        assertThat(testBookIssue.getCreateDate()).isEqualTo(UPDATED_CREATE_DATE);
        assertThat(testBookIssue.getUpdateDate()).isEqualTo(UPDATED_UPDATE_DATE);
        assertThat(testBookIssue.getCreateBy()).isEqualTo(UPDATED_CREATE_BY);
        assertThat(testBookIssue.getUpdateBy()).isEqualTo(UPDATED_UPDATE_BY);
    }

    @Test
    @Transactional
    public void updateNonExistingBookIssue() throws Exception {
        int databaseSizeBeforeUpdate = bookIssueRepository.findAll().size();

        // Create the BookIssue
        BookIssueDTO bookIssueDTO = bookIssueMapper.toDto(bookIssue);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restBookIssueMockMvc.perform(put("/api/book-issues")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bookIssueDTO)))
            .andExpect(status().isCreated());

        // Validate the BookIssue in the database
        List<BookIssue> bookIssueList = bookIssueRepository.findAll();
        assertThat(bookIssueList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteBookIssue() throws Exception {
        // Initialize the database
        bookIssueRepository.saveAndFlush(bookIssue);
        int databaseSizeBeforeDelete = bookIssueRepository.findAll().size();

        // Get the bookIssue
        restBookIssueMockMvc.perform(delete("/api/book-issues/{id}", bookIssue.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<BookIssue> bookIssueList = bookIssueRepository.findAll();
        assertThat(bookIssueList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BookIssue.class);
        BookIssue bookIssue1 = new BookIssue();
        bookIssue1.setId(1L);
        BookIssue bookIssue2 = new BookIssue();
        bookIssue2.setId(bookIssue1.getId());
        assertThat(bookIssue1).isEqualTo(bookIssue2);
        bookIssue2.setId(2L);
        assertThat(bookIssue1).isNotEqualTo(bookIssue2);
        bookIssue1.setId(null);
        assertThat(bookIssue1).isNotEqualTo(bookIssue2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(BookIssueDTO.class);
        BookIssueDTO bookIssueDTO1 = new BookIssueDTO();
        bookIssueDTO1.setId(1L);
        BookIssueDTO bookIssueDTO2 = new BookIssueDTO();
        assertThat(bookIssueDTO1).isNotEqualTo(bookIssueDTO2);
        bookIssueDTO2.setId(bookIssueDTO1.getId());
        assertThat(bookIssueDTO1).isEqualTo(bookIssueDTO2);
        bookIssueDTO2.setId(2L);
        assertThat(bookIssueDTO1).isNotEqualTo(bookIssueDTO2);
        bookIssueDTO1.setId(null);
        assertThat(bookIssueDTO1).isNotEqualTo(bookIssueDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(bookIssueMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(bookIssueMapper.fromId(null)).isNull();
    }
}
