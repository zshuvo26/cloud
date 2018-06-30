package cloud.web.rest;

import cloud.CloudApp;

import cloud.domain.BookCategory;
import cloud.repository.BookCategoryRepository;
import cloud.service.BookCategoryService;
import cloud.service.dto.BookCategoryDTO;
import cloud.service.mapper.BookCategoryMapper;
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
 * Test class for the BookCategoryResource REST controller.
 *
 * @see BookCategoryResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CloudApp.class)
public class BookCategoryResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Boolean DEFAULT_P_STATUS = false;
    private static final Boolean UPDATED_P_STATUS = true;

    @Autowired
    private BookCategoryRepository bookCategoryRepository;

    @Autowired
    private BookCategoryMapper bookCategoryMapper;

    @Autowired
    private BookCategoryService bookCategoryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restBookCategoryMockMvc;

    private BookCategory bookCategory;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final BookCategoryResource bookCategoryResource = new BookCategoryResource(bookCategoryService);
        this.restBookCategoryMockMvc = MockMvcBuilders.standaloneSetup(bookCategoryResource)
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
    public static BookCategory createEntity(EntityManager em) {
        BookCategory bookCategory = new BookCategory()
            .name(DEFAULT_NAME)
            .pStatus(DEFAULT_P_STATUS);
        return bookCategory;
    }

    @Before
    public void initTest() {
        bookCategory = createEntity(em);
    }

    @Test
    @Transactional
    public void createBookCategory() throws Exception {
        int databaseSizeBeforeCreate = bookCategoryRepository.findAll().size();

        // Create the BookCategory
        BookCategoryDTO bookCategoryDTO = bookCategoryMapper.toDto(bookCategory);
        restBookCategoryMockMvc.perform(post("/api/book-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bookCategoryDTO)))
            .andExpect(status().isCreated());

        // Validate the BookCategory in the database
        List<BookCategory> bookCategoryList = bookCategoryRepository.findAll();
        assertThat(bookCategoryList).hasSize(databaseSizeBeforeCreate + 1);
        BookCategory testBookCategory = bookCategoryList.get(bookCategoryList.size() - 1);
        assertThat(testBookCategory.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testBookCategory.ispStatus()).isEqualTo(DEFAULT_P_STATUS);
    }

    @Test
    @Transactional
    public void createBookCategoryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = bookCategoryRepository.findAll().size();

        // Create the BookCategory with an existing ID
        bookCategory.setId(1L);
        BookCategoryDTO bookCategoryDTO = bookCategoryMapper.toDto(bookCategory);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBookCategoryMockMvc.perform(post("/api/book-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bookCategoryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the BookCategory in the database
        List<BookCategory> bookCategoryList = bookCategoryRepository.findAll();
        assertThat(bookCategoryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllBookCategories() throws Exception {
        // Initialize the database
        bookCategoryRepository.saveAndFlush(bookCategory);

        // Get all the bookCategoryList
        restBookCategoryMockMvc.perform(get("/api/book-categories?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bookCategory.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].pStatus").value(hasItem(DEFAULT_P_STATUS.booleanValue())));
    }

    @Test
    @Transactional
    public void getBookCategory() throws Exception {
        // Initialize the database
        bookCategoryRepository.saveAndFlush(bookCategory);

        // Get the bookCategory
        restBookCategoryMockMvc.perform(get("/api/book-categories/{id}", bookCategory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(bookCategory.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.pStatus").value(DEFAULT_P_STATUS.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingBookCategory() throws Exception {
        // Get the bookCategory
        restBookCategoryMockMvc.perform(get("/api/book-categories/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBookCategory() throws Exception {
        // Initialize the database
        bookCategoryRepository.saveAndFlush(bookCategory);
        int databaseSizeBeforeUpdate = bookCategoryRepository.findAll().size();

        // Update the bookCategory
        BookCategory updatedBookCategory = bookCategoryRepository.findOne(bookCategory.getId());
        // Disconnect from session so that the updates on updatedBookCategory are not directly saved in db
        em.detach(updatedBookCategory);
        updatedBookCategory
            .name(UPDATED_NAME)
            .pStatus(UPDATED_P_STATUS);
        BookCategoryDTO bookCategoryDTO = bookCategoryMapper.toDto(updatedBookCategory);

        restBookCategoryMockMvc.perform(put("/api/book-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bookCategoryDTO)))
            .andExpect(status().isOk());

        // Validate the BookCategory in the database
        List<BookCategory> bookCategoryList = bookCategoryRepository.findAll();
        assertThat(bookCategoryList).hasSize(databaseSizeBeforeUpdate);
        BookCategory testBookCategory = bookCategoryList.get(bookCategoryList.size() - 1);
        assertThat(testBookCategory.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testBookCategory.ispStatus()).isEqualTo(UPDATED_P_STATUS);
    }

    @Test
    @Transactional
    public void updateNonExistingBookCategory() throws Exception {
        int databaseSizeBeforeUpdate = bookCategoryRepository.findAll().size();

        // Create the BookCategory
        BookCategoryDTO bookCategoryDTO = bookCategoryMapper.toDto(bookCategory);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restBookCategoryMockMvc.perform(put("/api/book-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bookCategoryDTO)))
            .andExpect(status().isCreated());

        // Validate the BookCategory in the database
        List<BookCategory> bookCategoryList = bookCategoryRepository.findAll();
        assertThat(bookCategoryList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteBookCategory() throws Exception {
        // Initialize the database
        bookCategoryRepository.saveAndFlush(bookCategory);
        int databaseSizeBeforeDelete = bookCategoryRepository.findAll().size();

        // Get the bookCategory
        restBookCategoryMockMvc.perform(delete("/api/book-categories/{id}", bookCategory.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<BookCategory> bookCategoryList = bookCategoryRepository.findAll();
        assertThat(bookCategoryList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BookCategory.class);
        BookCategory bookCategory1 = new BookCategory();
        bookCategory1.setId(1L);
        BookCategory bookCategory2 = new BookCategory();
        bookCategory2.setId(bookCategory1.getId());
        assertThat(bookCategory1).isEqualTo(bookCategory2);
        bookCategory2.setId(2L);
        assertThat(bookCategory1).isNotEqualTo(bookCategory2);
        bookCategory1.setId(null);
        assertThat(bookCategory1).isNotEqualTo(bookCategory2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(BookCategoryDTO.class);
        BookCategoryDTO bookCategoryDTO1 = new BookCategoryDTO();
        bookCategoryDTO1.setId(1L);
        BookCategoryDTO bookCategoryDTO2 = new BookCategoryDTO();
        assertThat(bookCategoryDTO1).isNotEqualTo(bookCategoryDTO2);
        bookCategoryDTO2.setId(bookCategoryDTO1.getId());
        assertThat(bookCategoryDTO1).isEqualTo(bookCategoryDTO2);
        bookCategoryDTO2.setId(2L);
        assertThat(bookCategoryDTO1).isNotEqualTo(bookCategoryDTO2);
        bookCategoryDTO1.setId(null);
        assertThat(bookCategoryDTO1).isNotEqualTo(bookCategoryDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(bookCategoryMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(bookCategoryMapper.fromId(null)).isNull();
    }
}
