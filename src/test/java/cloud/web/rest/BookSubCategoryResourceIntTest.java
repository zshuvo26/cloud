package cloud.web.rest;

import cloud.CloudApp;

import cloud.domain.BookSubCategory;
import cloud.repository.BookSubCategoryRepository;
import cloud.service.BookSubCategoryService;
import cloud.service.dto.BookSubCategoryDTO;
import cloud.service.mapper.BookSubCategoryMapper;
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
 * Test class for the BookSubCategoryResource REST controller.
 *
 * @see BookSubCategoryResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CloudApp.class)
public class BookSubCategoryResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Boolean DEFAULT_P_STATUS = false;
    private static final Boolean UPDATED_P_STATUS = true;

    @Autowired
    private BookSubCategoryRepository bookSubCategoryRepository;

    @Autowired
    private BookSubCategoryMapper bookSubCategoryMapper;

    @Autowired
    private BookSubCategoryService bookSubCategoryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restBookSubCategoryMockMvc;

    private BookSubCategory bookSubCategory;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final BookSubCategoryResource bookSubCategoryResource = new BookSubCategoryResource(bookSubCategoryService);
        this.restBookSubCategoryMockMvc = MockMvcBuilders.standaloneSetup(bookSubCategoryResource)
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
    public static BookSubCategory createEntity(EntityManager em) {
        BookSubCategory bookSubCategory = new BookSubCategory()
            .name(DEFAULT_NAME)
            .pStatus(DEFAULT_P_STATUS);
        return bookSubCategory;
    }

    @Before
    public void initTest() {
        bookSubCategory = createEntity(em);
    }

    @Test
    @Transactional
    public void createBookSubCategory() throws Exception {
        int databaseSizeBeforeCreate = bookSubCategoryRepository.findAll().size();

        // Create the BookSubCategory
        BookSubCategoryDTO bookSubCategoryDTO = bookSubCategoryMapper.toDto(bookSubCategory);
        restBookSubCategoryMockMvc.perform(post("/api/book-sub-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bookSubCategoryDTO)))
            .andExpect(status().isCreated());

        // Validate the BookSubCategory in the database
        List<BookSubCategory> bookSubCategoryList = bookSubCategoryRepository.findAll();
        assertThat(bookSubCategoryList).hasSize(databaseSizeBeforeCreate + 1);
        BookSubCategory testBookSubCategory = bookSubCategoryList.get(bookSubCategoryList.size() - 1);
        assertThat(testBookSubCategory.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testBookSubCategory.ispStatus()).isEqualTo(DEFAULT_P_STATUS);
    }

    @Test
    @Transactional
    public void createBookSubCategoryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = bookSubCategoryRepository.findAll().size();

        // Create the BookSubCategory with an existing ID
        bookSubCategory.setId(1L);
        BookSubCategoryDTO bookSubCategoryDTO = bookSubCategoryMapper.toDto(bookSubCategory);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBookSubCategoryMockMvc.perform(post("/api/book-sub-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bookSubCategoryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the BookSubCategory in the database
        List<BookSubCategory> bookSubCategoryList = bookSubCategoryRepository.findAll();
        assertThat(bookSubCategoryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllBookSubCategories() throws Exception {
        // Initialize the database
        bookSubCategoryRepository.saveAndFlush(bookSubCategory);

        // Get all the bookSubCategoryList
        restBookSubCategoryMockMvc.perform(get("/api/book-sub-categories?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bookSubCategory.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].pStatus").value(hasItem(DEFAULT_P_STATUS.booleanValue())));
    }

    @Test
    @Transactional
    public void getBookSubCategory() throws Exception {
        // Initialize the database
        bookSubCategoryRepository.saveAndFlush(bookSubCategory);

        // Get the bookSubCategory
        restBookSubCategoryMockMvc.perform(get("/api/book-sub-categories/{id}", bookSubCategory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(bookSubCategory.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.pStatus").value(DEFAULT_P_STATUS.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingBookSubCategory() throws Exception {
        // Get the bookSubCategory
        restBookSubCategoryMockMvc.perform(get("/api/book-sub-categories/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBookSubCategory() throws Exception {
        // Initialize the database
        bookSubCategoryRepository.saveAndFlush(bookSubCategory);
        int databaseSizeBeforeUpdate = bookSubCategoryRepository.findAll().size();

        // Update the bookSubCategory
        BookSubCategory updatedBookSubCategory = bookSubCategoryRepository.findOne(bookSubCategory.getId());
        // Disconnect from session so that the updates on updatedBookSubCategory are not directly saved in db
        em.detach(updatedBookSubCategory);
        updatedBookSubCategory
            .name(UPDATED_NAME)
            .pStatus(UPDATED_P_STATUS);
        BookSubCategoryDTO bookSubCategoryDTO = bookSubCategoryMapper.toDto(updatedBookSubCategory);

        restBookSubCategoryMockMvc.perform(put("/api/book-sub-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bookSubCategoryDTO)))
            .andExpect(status().isOk());

        // Validate the BookSubCategory in the database
        List<BookSubCategory> bookSubCategoryList = bookSubCategoryRepository.findAll();
        assertThat(bookSubCategoryList).hasSize(databaseSizeBeforeUpdate);
        BookSubCategory testBookSubCategory = bookSubCategoryList.get(bookSubCategoryList.size() - 1);
        assertThat(testBookSubCategory.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testBookSubCategory.ispStatus()).isEqualTo(UPDATED_P_STATUS);
    }

    @Test
    @Transactional
    public void updateNonExistingBookSubCategory() throws Exception {
        int databaseSizeBeforeUpdate = bookSubCategoryRepository.findAll().size();

        // Create the BookSubCategory
        BookSubCategoryDTO bookSubCategoryDTO = bookSubCategoryMapper.toDto(bookSubCategory);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restBookSubCategoryMockMvc.perform(put("/api/book-sub-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bookSubCategoryDTO)))
            .andExpect(status().isCreated());

        // Validate the BookSubCategory in the database
        List<BookSubCategory> bookSubCategoryList = bookSubCategoryRepository.findAll();
        assertThat(bookSubCategoryList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteBookSubCategory() throws Exception {
        // Initialize the database
        bookSubCategoryRepository.saveAndFlush(bookSubCategory);
        int databaseSizeBeforeDelete = bookSubCategoryRepository.findAll().size();

        // Get the bookSubCategory
        restBookSubCategoryMockMvc.perform(delete("/api/book-sub-categories/{id}", bookSubCategory.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<BookSubCategory> bookSubCategoryList = bookSubCategoryRepository.findAll();
        assertThat(bookSubCategoryList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BookSubCategory.class);
        BookSubCategory bookSubCategory1 = new BookSubCategory();
        bookSubCategory1.setId(1L);
        BookSubCategory bookSubCategory2 = new BookSubCategory();
        bookSubCategory2.setId(bookSubCategory1.getId());
        assertThat(bookSubCategory1).isEqualTo(bookSubCategory2);
        bookSubCategory2.setId(2L);
        assertThat(bookSubCategory1).isNotEqualTo(bookSubCategory2);
        bookSubCategory1.setId(null);
        assertThat(bookSubCategory1).isNotEqualTo(bookSubCategory2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(BookSubCategoryDTO.class);
        BookSubCategoryDTO bookSubCategoryDTO1 = new BookSubCategoryDTO();
        bookSubCategoryDTO1.setId(1L);
        BookSubCategoryDTO bookSubCategoryDTO2 = new BookSubCategoryDTO();
        assertThat(bookSubCategoryDTO1).isNotEqualTo(bookSubCategoryDTO2);
        bookSubCategoryDTO2.setId(bookSubCategoryDTO1.getId());
        assertThat(bookSubCategoryDTO1).isEqualTo(bookSubCategoryDTO2);
        bookSubCategoryDTO2.setId(2L);
        assertThat(bookSubCategoryDTO1).isNotEqualTo(bookSubCategoryDTO2);
        bookSubCategoryDTO1.setId(null);
        assertThat(bookSubCategoryDTO1).isNotEqualTo(bookSubCategoryDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(bookSubCategoryMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(bookSubCategoryMapper.fromId(null)).isNull();
    }
}
