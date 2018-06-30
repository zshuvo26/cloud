package cloud.web.rest;

import cloud.CloudApp;

import cloud.domain.BookFineSetting;
import cloud.repository.BookFineSettingRepository;
import cloud.service.BookFineSettingService;
import cloud.service.dto.BookFineSettingDTO;
import cloud.service.mapper.BookFineSettingMapper;
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
import java.util.List;

import static cloud.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the BookFineSettingResource REST controller.
 *
 * @see BookFineSettingResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CloudApp.class)
public class BookFineSettingResourceIntTest {

    private static final String DEFAULT_MAX_DAY_FOR_STAFF = "AAAAAAAAAA";
    private static final String UPDATED_MAX_DAY_FOR_STAFF = "BBBBBBBBBB";

    private static final String DEFAULT_MAX_DAY_FOR_STUDENT = "AAAAAAAAAA";
    private static final String UPDATED_MAX_DAY_FOR_STUDENT = "BBBBBBBBBB";

    private static final String DEFAULT_FINE_PER_DAY_FOR_SATFF = "AAAAAAAAAA";
    private static final String UPDATED_FINE_PER_DAY_FOR_SATFF = "BBBBBBBBBB";

    private static final String DEFAULT_FINE_PER_DAY_FOR_STUDENT = "AAAAAAAAAA";
    private static final String UPDATED_FINE_PER_DAY_FOR_STUDENT = "BBBBBBBBBB";

    private static final String DEFAULT_MAX_BOOKS_FOR_STAFF = "AAAAAAAAAA";
    private static final String UPDATED_MAX_BOOKS_FOR_STAFF = "BBBBBBBBBB";

    private static final String DEFAULT_MAX_BOOKS_FOR_STUDNT = "AAAAAAAAAA";
    private static final String UPDATED_MAX_BOOKS_FOR_STUDNT = "BBBBBBBBBB";

    @Autowired
    private BookFineSettingRepository bookFineSettingRepository;

    @Autowired
    private BookFineSettingMapper bookFineSettingMapper;

    @Autowired
    private BookFineSettingService bookFineSettingService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restBookFineSettingMockMvc;

    private BookFineSetting bookFineSetting;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final BookFineSettingResource bookFineSettingResource = new BookFineSettingResource(bookFineSettingService);
        this.restBookFineSettingMockMvc = MockMvcBuilders.standaloneSetup(bookFineSettingResource)
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
    public static BookFineSetting createEntity(EntityManager em) {
        BookFineSetting bookFineSetting = new BookFineSetting()
            .maxDayForStaff(DEFAULT_MAX_DAY_FOR_STAFF)
            .maxDayForStudent(DEFAULT_MAX_DAY_FOR_STUDENT)
            .finePerDayForSatff(DEFAULT_FINE_PER_DAY_FOR_SATFF)
            .finePerDayForStudent(DEFAULT_FINE_PER_DAY_FOR_STUDENT)
            .maxBooksForStaff(DEFAULT_MAX_BOOKS_FOR_STAFF)
            .maxBooksForStudnt(DEFAULT_MAX_BOOKS_FOR_STUDNT);
        return bookFineSetting;
    }

    @Before
    public void initTest() {
        bookFineSetting = createEntity(em);
    }

    @Test
    @Transactional
    public void createBookFineSetting() throws Exception {
        int databaseSizeBeforeCreate = bookFineSettingRepository.findAll().size();

        // Create the BookFineSetting
        BookFineSettingDTO bookFineSettingDTO = bookFineSettingMapper.toDto(bookFineSetting);
        restBookFineSettingMockMvc.perform(post("/api/book-fine-settings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bookFineSettingDTO)))
            .andExpect(status().isCreated());

        // Validate the BookFineSetting in the database
        List<BookFineSetting> bookFineSettingList = bookFineSettingRepository.findAll();
        assertThat(bookFineSettingList).hasSize(databaseSizeBeforeCreate + 1);
        BookFineSetting testBookFineSetting = bookFineSettingList.get(bookFineSettingList.size() - 1);
        assertThat(testBookFineSetting.getMaxDayForStaff()).isEqualTo(DEFAULT_MAX_DAY_FOR_STAFF);
        assertThat(testBookFineSetting.getMaxDayForStudent()).isEqualTo(DEFAULT_MAX_DAY_FOR_STUDENT);
        assertThat(testBookFineSetting.getFinePerDayForSatff()).isEqualTo(DEFAULT_FINE_PER_DAY_FOR_SATFF);
        assertThat(testBookFineSetting.getFinePerDayForStudent()).isEqualTo(DEFAULT_FINE_PER_DAY_FOR_STUDENT);
        assertThat(testBookFineSetting.getMaxBooksForStaff()).isEqualTo(DEFAULT_MAX_BOOKS_FOR_STAFF);
        assertThat(testBookFineSetting.getMaxBooksForStudnt()).isEqualTo(DEFAULT_MAX_BOOKS_FOR_STUDNT);
    }

    @Test
    @Transactional
    public void createBookFineSettingWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = bookFineSettingRepository.findAll().size();

        // Create the BookFineSetting with an existing ID
        bookFineSetting.setId(1L);
        BookFineSettingDTO bookFineSettingDTO = bookFineSettingMapper.toDto(bookFineSetting);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBookFineSettingMockMvc.perform(post("/api/book-fine-settings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bookFineSettingDTO)))
            .andExpect(status().isBadRequest());

        // Validate the BookFineSetting in the database
        List<BookFineSetting> bookFineSettingList = bookFineSettingRepository.findAll();
        assertThat(bookFineSettingList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllBookFineSettings() throws Exception {
        // Initialize the database
        bookFineSettingRepository.saveAndFlush(bookFineSetting);

        // Get all the bookFineSettingList
        restBookFineSettingMockMvc.perform(get("/api/book-fine-settings?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bookFineSetting.getId().intValue())))
            .andExpect(jsonPath("$.[*].maxDayForStaff").value(hasItem(DEFAULT_MAX_DAY_FOR_STAFF.toString())))
            .andExpect(jsonPath("$.[*].maxDayForStudent").value(hasItem(DEFAULT_MAX_DAY_FOR_STUDENT.toString())))
            .andExpect(jsonPath("$.[*].finePerDayForSatff").value(hasItem(DEFAULT_FINE_PER_DAY_FOR_SATFF.toString())))
            .andExpect(jsonPath("$.[*].finePerDayForStudent").value(hasItem(DEFAULT_FINE_PER_DAY_FOR_STUDENT.toString())))
            .andExpect(jsonPath("$.[*].maxBooksForStaff").value(hasItem(DEFAULT_MAX_BOOKS_FOR_STAFF.toString())))
            .andExpect(jsonPath("$.[*].maxBooksForStudnt").value(hasItem(DEFAULT_MAX_BOOKS_FOR_STUDNT.toString())));
    }

    @Test
    @Transactional
    public void getBookFineSetting() throws Exception {
        // Initialize the database
        bookFineSettingRepository.saveAndFlush(bookFineSetting);

        // Get the bookFineSetting
        restBookFineSettingMockMvc.perform(get("/api/book-fine-settings/{id}", bookFineSetting.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(bookFineSetting.getId().intValue()))
            .andExpect(jsonPath("$.maxDayForStaff").value(DEFAULT_MAX_DAY_FOR_STAFF.toString()))
            .andExpect(jsonPath("$.maxDayForStudent").value(DEFAULT_MAX_DAY_FOR_STUDENT.toString()))
            .andExpect(jsonPath("$.finePerDayForSatff").value(DEFAULT_FINE_PER_DAY_FOR_SATFF.toString()))
            .andExpect(jsonPath("$.finePerDayForStudent").value(DEFAULT_FINE_PER_DAY_FOR_STUDENT.toString()))
            .andExpect(jsonPath("$.maxBooksForStaff").value(DEFAULT_MAX_BOOKS_FOR_STAFF.toString()))
            .andExpect(jsonPath("$.maxBooksForStudnt").value(DEFAULT_MAX_BOOKS_FOR_STUDNT.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingBookFineSetting() throws Exception {
        // Get the bookFineSetting
        restBookFineSettingMockMvc.perform(get("/api/book-fine-settings/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBookFineSetting() throws Exception {
        // Initialize the database
        bookFineSettingRepository.saveAndFlush(bookFineSetting);
        int databaseSizeBeforeUpdate = bookFineSettingRepository.findAll().size();

        // Update the bookFineSetting
        BookFineSetting updatedBookFineSetting = bookFineSettingRepository.findOne(bookFineSetting.getId());
        // Disconnect from session so that the updates on updatedBookFineSetting are not directly saved in db
        em.detach(updatedBookFineSetting);
        updatedBookFineSetting
            .maxDayForStaff(UPDATED_MAX_DAY_FOR_STAFF)
            .maxDayForStudent(UPDATED_MAX_DAY_FOR_STUDENT)
            .finePerDayForSatff(UPDATED_FINE_PER_DAY_FOR_SATFF)
            .finePerDayForStudent(UPDATED_FINE_PER_DAY_FOR_STUDENT)
            .maxBooksForStaff(UPDATED_MAX_BOOKS_FOR_STAFF)
            .maxBooksForStudnt(UPDATED_MAX_BOOKS_FOR_STUDNT);
        BookFineSettingDTO bookFineSettingDTO = bookFineSettingMapper.toDto(updatedBookFineSetting);

        restBookFineSettingMockMvc.perform(put("/api/book-fine-settings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bookFineSettingDTO)))
            .andExpect(status().isOk());

        // Validate the BookFineSetting in the database
        List<BookFineSetting> bookFineSettingList = bookFineSettingRepository.findAll();
        assertThat(bookFineSettingList).hasSize(databaseSizeBeforeUpdate);
        BookFineSetting testBookFineSetting = bookFineSettingList.get(bookFineSettingList.size() - 1);
        assertThat(testBookFineSetting.getMaxDayForStaff()).isEqualTo(UPDATED_MAX_DAY_FOR_STAFF);
        assertThat(testBookFineSetting.getMaxDayForStudent()).isEqualTo(UPDATED_MAX_DAY_FOR_STUDENT);
        assertThat(testBookFineSetting.getFinePerDayForSatff()).isEqualTo(UPDATED_FINE_PER_DAY_FOR_SATFF);
        assertThat(testBookFineSetting.getFinePerDayForStudent()).isEqualTo(UPDATED_FINE_PER_DAY_FOR_STUDENT);
        assertThat(testBookFineSetting.getMaxBooksForStaff()).isEqualTo(UPDATED_MAX_BOOKS_FOR_STAFF);
        assertThat(testBookFineSetting.getMaxBooksForStudnt()).isEqualTo(UPDATED_MAX_BOOKS_FOR_STUDNT);
    }

    @Test
    @Transactional
    public void updateNonExistingBookFineSetting() throws Exception {
        int databaseSizeBeforeUpdate = bookFineSettingRepository.findAll().size();

        // Create the BookFineSetting
        BookFineSettingDTO bookFineSettingDTO = bookFineSettingMapper.toDto(bookFineSetting);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restBookFineSettingMockMvc.perform(put("/api/book-fine-settings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bookFineSettingDTO)))
            .andExpect(status().isCreated());

        // Validate the BookFineSetting in the database
        List<BookFineSetting> bookFineSettingList = bookFineSettingRepository.findAll();
        assertThat(bookFineSettingList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteBookFineSetting() throws Exception {
        // Initialize the database
        bookFineSettingRepository.saveAndFlush(bookFineSetting);
        int databaseSizeBeforeDelete = bookFineSettingRepository.findAll().size();

        // Get the bookFineSetting
        restBookFineSettingMockMvc.perform(delete("/api/book-fine-settings/{id}", bookFineSetting.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<BookFineSetting> bookFineSettingList = bookFineSettingRepository.findAll();
        assertThat(bookFineSettingList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BookFineSetting.class);
        BookFineSetting bookFineSetting1 = new BookFineSetting();
        bookFineSetting1.setId(1L);
        BookFineSetting bookFineSetting2 = new BookFineSetting();
        bookFineSetting2.setId(bookFineSetting1.getId());
        assertThat(bookFineSetting1).isEqualTo(bookFineSetting2);
        bookFineSetting2.setId(2L);
        assertThat(bookFineSetting1).isNotEqualTo(bookFineSetting2);
        bookFineSetting1.setId(null);
        assertThat(bookFineSetting1).isNotEqualTo(bookFineSetting2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(BookFineSettingDTO.class);
        BookFineSettingDTO bookFineSettingDTO1 = new BookFineSettingDTO();
        bookFineSettingDTO1.setId(1L);
        BookFineSettingDTO bookFineSettingDTO2 = new BookFineSettingDTO();
        assertThat(bookFineSettingDTO1).isNotEqualTo(bookFineSettingDTO2);
        bookFineSettingDTO2.setId(bookFineSettingDTO1.getId());
        assertThat(bookFineSettingDTO1).isEqualTo(bookFineSettingDTO2);
        bookFineSettingDTO2.setId(2L);
        assertThat(bookFineSettingDTO1).isNotEqualTo(bookFineSettingDTO2);
        bookFineSettingDTO1.setId(null);
        assertThat(bookFineSettingDTO1).isNotEqualTo(bookFineSettingDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(bookFineSettingMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(bookFineSettingMapper.fromId(null)).isNull();
    }
}
