package cloud.web.rest;

import cloud.CloudApp;

import cloud.domain.BookType;
import cloud.repository.BookTypeRepository;
import cloud.service.BookTypeService;
import cloud.service.dto.BookTypeDTO;
import cloud.service.mapper.BookTypeMapper;
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
 * Test class for the BookTypeResource REST controller.
 *
 * @see BookTypeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CloudApp.class)
public class BookTypeResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Boolean DEFAULT_P_STATUS = false;
    private static final Boolean UPDATED_P_STATUS = true;

    @Autowired
    private BookTypeRepository bookTypeRepository;

    @Autowired
    private BookTypeMapper bookTypeMapper;

    @Autowired
    private BookTypeService bookTypeService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restBookTypeMockMvc;

    private BookType bookType;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final BookTypeResource bookTypeResource = new BookTypeResource(bookTypeService);
        this.restBookTypeMockMvc = MockMvcBuilders.standaloneSetup(bookTypeResource)
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
    public static BookType createEntity(EntityManager em) {
        BookType bookType = new BookType()
            .name(DEFAULT_NAME)
            .pStatus(DEFAULT_P_STATUS);
        return bookType;
    }

    @Before
    public void initTest() {
        bookType = createEntity(em);
    }

    @Test
    @Transactional
    public void createBookType() throws Exception {
        int databaseSizeBeforeCreate = bookTypeRepository.findAll().size();

        // Create the BookType
        BookTypeDTO bookTypeDTO = bookTypeMapper.toDto(bookType);
        restBookTypeMockMvc.perform(post("/api/book-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bookTypeDTO)))
            .andExpect(status().isCreated());

        // Validate the BookType in the database
        List<BookType> bookTypeList = bookTypeRepository.findAll();
        assertThat(bookTypeList).hasSize(databaseSizeBeforeCreate + 1);
        BookType testBookType = bookTypeList.get(bookTypeList.size() - 1);
        assertThat(testBookType.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testBookType.ispStatus()).isEqualTo(DEFAULT_P_STATUS);
    }

    @Test
    @Transactional
    public void createBookTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = bookTypeRepository.findAll().size();

        // Create the BookType with an existing ID
        bookType.setId(1L);
        BookTypeDTO bookTypeDTO = bookTypeMapper.toDto(bookType);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBookTypeMockMvc.perform(post("/api/book-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bookTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the BookType in the database
        List<BookType> bookTypeList = bookTypeRepository.findAll();
        assertThat(bookTypeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllBookTypes() throws Exception {
        // Initialize the database
        bookTypeRepository.saveAndFlush(bookType);

        // Get all the bookTypeList
        restBookTypeMockMvc.perform(get("/api/book-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bookType.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].pStatus").value(hasItem(DEFAULT_P_STATUS.booleanValue())));
    }

    @Test
    @Transactional
    public void getBookType() throws Exception {
        // Initialize the database
        bookTypeRepository.saveAndFlush(bookType);

        // Get the bookType
        restBookTypeMockMvc.perform(get("/api/book-types/{id}", bookType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(bookType.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.pStatus").value(DEFAULT_P_STATUS.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingBookType() throws Exception {
        // Get the bookType
        restBookTypeMockMvc.perform(get("/api/book-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBookType() throws Exception {
        // Initialize the database
        bookTypeRepository.saveAndFlush(bookType);
        int databaseSizeBeforeUpdate = bookTypeRepository.findAll().size();

        // Update the bookType
        BookType updatedBookType = bookTypeRepository.findOne(bookType.getId());
        // Disconnect from session so that the updates on updatedBookType are not directly saved in db
        em.detach(updatedBookType);
        updatedBookType
            .name(UPDATED_NAME)
            .pStatus(UPDATED_P_STATUS);
        BookTypeDTO bookTypeDTO = bookTypeMapper.toDto(updatedBookType);

        restBookTypeMockMvc.perform(put("/api/book-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bookTypeDTO)))
            .andExpect(status().isOk());

        // Validate the BookType in the database
        List<BookType> bookTypeList = bookTypeRepository.findAll();
        assertThat(bookTypeList).hasSize(databaseSizeBeforeUpdate);
        BookType testBookType = bookTypeList.get(bookTypeList.size() - 1);
        assertThat(testBookType.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testBookType.ispStatus()).isEqualTo(UPDATED_P_STATUS);
    }

    @Test
    @Transactional
    public void updateNonExistingBookType() throws Exception {
        int databaseSizeBeforeUpdate = bookTypeRepository.findAll().size();

        // Create the BookType
        BookTypeDTO bookTypeDTO = bookTypeMapper.toDto(bookType);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restBookTypeMockMvc.perform(put("/api/book-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bookTypeDTO)))
            .andExpect(status().isCreated());

        // Validate the BookType in the database
        List<BookType> bookTypeList = bookTypeRepository.findAll();
        assertThat(bookTypeList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteBookType() throws Exception {
        // Initialize the database
        bookTypeRepository.saveAndFlush(bookType);
        int databaseSizeBeforeDelete = bookTypeRepository.findAll().size();

        // Get the bookType
        restBookTypeMockMvc.perform(delete("/api/book-types/{id}", bookType.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<BookType> bookTypeList = bookTypeRepository.findAll();
        assertThat(bookTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BookType.class);
        BookType bookType1 = new BookType();
        bookType1.setId(1L);
        BookType bookType2 = new BookType();
        bookType2.setId(bookType1.getId());
        assertThat(bookType1).isEqualTo(bookType2);
        bookType2.setId(2L);
        assertThat(bookType1).isNotEqualTo(bookType2);
        bookType1.setId(null);
        assertThat(bookType1).isNotEqualTo(bookType2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(BookTypeDTO.class);
        BookTypeDTO bookTypeDTO1 = new BookTypeDTO();
        bookTypeDTO1.setId(1L);
        BookTypeDTO bookTypeDTO2 = new BookTypeDTO();
        assertThat(bookTypeDTO1).isNotEqualTo(bookTypeDTO2);
        bookTypeDTO2.setId(bookTypeDTO1.getId());
        assertThat(bookTypeDTO1).isEqualTo(bookTypeDTO2);
        bookTypeDTO2.setId(2L);
        assertThat(bookTypeDTO1).isNotEqualTo(bookTypeDTO2);
        bookTypeDTO1.setId(null);
        assertThat(bookTypeDTO1).isNotEqualTo(bookTypeDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(bookTypeMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(bookTypeMapper.fromId(null)).isNull();
    }
}
