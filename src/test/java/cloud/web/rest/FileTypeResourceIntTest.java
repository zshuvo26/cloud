package cloud.web.rest;

import cloud.CloudApp;

import cloud.domain.FileType;
import cloud.repository.FileTypeRepository;
import cloud.service.FileTypeService;
import cloud.service.dto.FileTypeDTO;
import cloud.service.mapper.FileTypeMapper;
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
 * Test class for the FileTypeResource REST controller.
 *
 * @see FileTypeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CloudApp.class)
public class FileTypeResourceIntTest {

    private static final String DEFAULT_FILE_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_FILE_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_SIZE_LIMIT = "AAAAAAAAAA";
    private static final String UPDATED_SIZE_LIMIT = "BBBBBBBBBB";

    @Autowired
    private FileTypeRepository fileTypeRepository;

    @Autowired
    private FileTypeMapper fileTypeMapper;

    @Autowired
    private FileTypeService fileTypeService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restFileTypeMockMvc;

    private FileType fileType;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final FileTypeResource fileTypeResource = new FileTypeResource(fileTypeService);
        this.restFileTypeMockMvc = MockMvcBuilders.standaloneSetup(fileTypeResource)
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
    public static FileType createEntity(EntityManager em) {
        FileType fileType = new FileType()
            .fileType(DEFAULT_FILE_TYPE)
            .sizeLimit(DEFAULT_SIZE_LIMIT);
        return fileType;
    }

    @Before
    public void initTest() {
        fileType = createEntity(em);
    }

    @Test
    @Transactional
    public void createFileType() throws Exception {
        int databaseSizeBeforeCreate = fileTypeRepository.findAll().size();

        // Create the FileType
        FileTypeDTO fileTypeDTO = fileTypeMapper.toDto(fileType);
        restFileTypeMockMvc.perform(post("/api/file-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fileTypeDTO)))
            .andExpect(status().isCreated());

        // Validate the FileType in the database
        List<FileType> fileTypeList = fileTypeRepository.findAll();
        assertThat(fileTypeList).hasSize(databaseSizeBeforeCreate + 1);
        FileType testFileType = fileTypeList.get(fileTypeList.size() - 1);
        assertThat(testFileType.getFileType()).isEqualTo(DEFAULT_FILE_TYPE);
        assertThat(testFileType.getSizeLimit()).isEqualTo(DEFAULT_SIZE_LIMIT);
    }

    @Test
    @Transactional
    public void createFileTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = fileTypeRepository.findAll().size();

        // Create the FileType with an existing ID
        fileType.setId(1L);
        FileTypeDTO fileTypeDTO = fileTypeMapper.toDto(fileType);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFileTypeMockMvc.perform(post("/api/file-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fileTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the FileType in the database
        List<FileType> fileTypeList = fileTypeRepository.findAll();
        assertThat(fileTypeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllFileTypes() throws Exception {
        // Initialize the database
        fileTypeRepository.saveAndFlush(fileType);

        // Get all the fileTypeList
        restFileTypeMockMvc.perform(get("/api/file-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fileType.getId().intValue())))
            .andExpect(jsonPath("$.[*].fileType").value(hasItem(DEFAULT_FILE_TYPE.toString())))
            .andExpect(jsonPath("$.[*].sizeLimit").value(hasItem(DEFAULT_SIZE_LIMIT.toString())));
    }

    @Test
    @Transactional
    public void getFileType() throws Exception {
        // Initialize the database
        fileTypeRepository.saveAndFlush(fileType);

        // Get the fileType
        restFileTypeMockMvc.perform(get("/api/file-types/{id}", fileType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(fileType.getId().intValue()))
            .andExpect(jsonPath("$.fileType").value(DEFAULT_FILE_TYPE.toString()))
            .andExpect(jsonPath("$.sizeLimit").value(DEFAULT_SIZE_LIMIT.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingFileType() throws Exception {
        // Get the fileType
        restFileTypeMockMvc.perform(get("/api/file-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFileType() throws Exception {
        // Initialize the database
        fileTypeRepository.saveAndFlush(fileType);
        int databaseSizeBeforeUpdate = fileTypeRepository.findAll().size();

        // Update the fileType
        FileType updatedFileType = fileTypeRepository.findOne(fileType.getId());
        // Disconnect from session so that the updates on updatedFileType are not directly saved in db
        em.detach(updatedFileType);
        updatedFileType
            .fileType(UPDATED_FILE_TYPE)
            .sizeLimit(UPDATED_SIZE_LIMIT);
        FileTypeDTO fileTypeDTO = fileTypeMapper.toDto(updatedFileType);

        restFileTypeMockMvc.perform(put("/api/file-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fileTypeDTO)))
            .andExpect(status().isOk());

        // Validate the FileType in the database
        List<FileType> fileTypeList = fileTypeRepository.findAll();
        assertThat(fileTypeList).hasSize(databaseSizeBeforeUpdate);
        FileType testFileType = fileTypeList.get(fileTypeList.size() - 1);
        assertThat(testFileType.getFileType()).isEqualTo(UPDATED_FILE_TYPE);
        assertThat(testFileType.getSizeLimit()).isEqualTo(UPDATED_SIZE_LIMIT);
    }

    @Test
    @Transactional
    public void updateNonExistingFileType() throws Exception {
        int databaseSizeBeforeUpdate = fileTypeRepository.findAll().size();

        // Create the FileType
        FileTypeDTO fileTypeDTO = fileTypeMapper.toDto(fileType);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restFileTypeMockMvc.perform(put("/api/file-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fileTypeDTO)))
            .andExpect(status().isCreated());

        // Validate the FileType in the database
        List<FileType> fileTypeList = fileTypeRepository.findAll();
        assertThat(fileTypeList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteFileType() throws Exception {
        // Initialize the database
        fileTypeRepository.saveAndFlush(fileType);
        int databaseSizeBeforeDelete = fileTypeRepository.findAll().size();

        // Get the fileType
        restFileTypeMockMvc.perform(delete("/api/file-types/{id}", fileType.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<FileType> fileTypeList = fileTypeRepository.findAll();
        assertThat(fileTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FileType.class);
        FileType fileType1 = new FileType();
        fileType1.setId(1L);
        FileType fileType2 = new FileType();
        fileType2.setId(fileType1.getId());
        assertThat(fileType1).isEqualTo(fileType2);
        fileType2.setId(2L);
        assertThat(fileType1).isNotEqualTo(fileType2);
        fileType1.setId(null);
        assertThat(fileType1).isNotEqualTo(fileType2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(FileTypeDTO.class);
        FileTypeDTO fileTypeDTO1 = new FileTypeDTO();
        fileTypeDTO1.setId(1L);
        FileTypeDTO fileTypeDTO2 = new FileTypeDTO();
        assertThat(fileTypeDTO1).isNotEqualTo(fileTypeDTO2);
        fileTypeDTO2.setId(fileTypeDTO1.getId());
        assertThat(fileTypeDTO1).isEqualTo(fileTypeDTO2);
        fileTypeDTO2.setId(2L);
        assertThat(fileTypeDTO1).isNotEqualTo(fileTypeDTO2);
        fileTypeDTO1.setId(null);
        assertThat(fileTypeDTO1).isNotEqualTo(fileTypeDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(fileTypeMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(fileTypeMapper.fromId(null)).isNull();
    }
}
