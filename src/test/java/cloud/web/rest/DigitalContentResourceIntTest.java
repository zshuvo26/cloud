package cloud.web.rest;

import cloud.CloudApp;

import cloud.domain.DigitalContent;
import cloud.repository.DigitalContentRepository;
import cloud.service.DigitalContentService;
import cloud.service.dto.DigitalContentDTO;
import cloud.service.mapper.DigitalContentMapper;
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
import org.springframework.util.Base64Utils;

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
 * Test class for the DigitalContentResource REST controller.
 *
 * @see DigitalContentResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CloudApp.class)
public class DigitalContentResourceIntTest {

    private static final String DEFAULT_ACCESSION_NO = "AAAAAAAAAA";
    private static final String UPDATED_ACCESSION_NO = "BBBBBBBBBB";

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_ISBN_NO = "AAAAAAAAAA";
    private static final String UPDATED_ISBN_NO = "BBBBBBBBBB";

    private static final String DEFAULT_AUTHOR_NAME = "AAAAAAAAAA";
    private static final String UPDATED_AUTHOR_NAME = "BBBBBBBBBB";

    private static final byte[] DEFAULT_COVER_PHOTO = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_COVER_PHOTO = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_COVER_PHOTO_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_COVER_PHOTO_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_COVER_PHOTO_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_COVER_PHOTO_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_COVER_PHOTO_NAME = "AAAAAAAAAA";
    private static final String UPDATED_COVER_PHOTO_NAME = "BBBBBBBBBB";

    private static final byte[] DEFAULT_CONTENT = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_CONTENT = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_CONTENT_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_CONTENT_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_CONTENT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_CONTENT_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_CONTENT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_CONTENT_NAME = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_CREATE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATE_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_UPDATE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_UPDATE_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_CREATE_BY = 1;
    private static final Integer UPDATED_CREATE_BY = 2;

    private static final Integer DEFAULT_UPDATE_BY = 1;
    private static final Integer UPDATED_UPDATE_BY = 2;

    @Autowired
    private DigitalContentRepository digitalContentRepository;

    @Autowired
    private DigitalContentMapper digitalContentMapper;

    @Autowired
    private DigitalContentService digitalContentService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restDigitalContentMockMvc;

    private DigitalContent digitalContent;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DigitalContentResource digitalContentResource = new DigitalContentResource(digitalContentService);
        this.restDigitalContentMockMvc = MockMvcBuilders.standaloneSetup(digitalContentResource)
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
    public static DigitalContent createEntity(EntityManager em) {
        DigitalContent digitalContent = new DigitalContent()
            .accessionNo(DEFAULT_ACCESSION_NO)
            .title(DEFAULT_TITLE)
            .isbnNo(DEFAULT_ISBN_NO)
            .authorName(DEFAULT_AUTHOR_NAME)
            .coverPhoto(DEFAULT_COVER_PHOTO)
            .coverPhotoContentType(DEFAULT_COVER_PHOTO_CONTENT_TYPE)
            .coverPhotoType(DEFAULT_COVER_PHOTO_TYPE)
            .coverPhotoName(DEFAULT_COVER_PHOTO_NAME)
            .content(DEFAULT_CONTENT)
            .contentContentType(DEFAULT_CONTENT_CONTENT_TYPE)
            .contentType(DEFAULT_CONTENT_TYPE)
            .contentName(DEFAULT_CONTENT_NAME)
            .createDate(DEFAULT_CREATE_DATE)
            .updateDate(DEFAULT_UPDATE_DATE)
            .createBy(DEFAULT_CREATE_BY)
            .updateBy(DEFAULT_UPDATE_BY);
        return digitalContent;
    }

    @Before
    public void initTest() {
        digitalContent = createEntity(em);
    }

    @Test
    @Transactional
    public void createDigitalContent() throws Exception {
        int databaseSizeBeforeCreate = digitalContentRepository.findAll().size();

        // Create the DigitalContent
        DigitalContentDTO digitalContentDTO = digitalContentMapper.toDto(digitalContent);
        restDigitalContentMockMvc.perform(post("/api/digital-contents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(digitalContentDTO)))
            .andExpect(status().isCreated());

        // Validate the DigitalContent in the database
        List<DigitalContent> digitalContentList = digitalContentRepository.findAll();
        assertThat(digitalContentList).hasSize(databaseSizeBeforeCreate + 1);
        DigitalContent testDigitalContent = digitalContentList.get(digitalContentList.size() - 1);
        assertThat(testDigitalContent.getAccessionNo()).isEqualTo(DEFAULT_ACCESSION_NO);
        assertThat(testDigitalContent.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testDigitalContent.getIsbnNo()).isEqualTo(DEFAULT_ISBN_NO);
        assertThat(testDigitalContent.getAuthorName()).isEqualTo(DEFAULT_AUTHOR_NAME);
        assertThat(testDigitalContent.getCoverPhoto()).isEqualTo(DEFAULT_COVER_PHOTO);
        assertThat(testDigitalContent.getCoverPhotoContentType()).isEqualTo(DEFAULT_COVER_PHOTO_CONTENT_TYPE);
        assertThat(testDigitalContent.getCoverPhotoType()).isEqualTo(DEFAULT_COVER_PHOTO_TYPE);
        assertThat(testDigitalContent.getCoverPhotoName()).isEqualTo(DEFAULT_COVER_PHOTO_NAME);
        assertThat(testDigitalContent.getContent()).isEqualTo(DEFAULT_CONTENT);
        assertThat(testDigitalContent.getContentContentType()).isEqualTo(DEFAULT_CONTENT_CONTENT_TYPE);
        assertThat(testDigitalContent.getContentType()).isEqualTo(DEFAULT_CONTENT_TYPE);
        assertThat(testDigitalContent.getContentName()).isEqualTo(DEFAULT_CONTENT_NAME);
        assertThat(testDigitalContent.getCreateDate()).isEqualTo(DEFAULT_CREATE_DATE);
        assertThat(testDigitalContent.getUpdateDate()).isEqualTo(DEFAULT_UPDATE_DATE);
        assertThat(testDigitalContent.getCreateBy()).isEqualTo(DEFAULT_CREATE_BY);
        assertThat(testDigitalContent.getUpdateBy()).isEqualTo(DEFAULT_UPDATE_BY);
    }

    @Test
    @Transactional
    public void createDigitalContentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = digitalContentRepository.findAll().size();

        // Create the DigitalContent with an existing ID
        digitalContent.setId(1L);
        DigitalContentDTO digitalContentDTO = digitalContentMapper.toDto(digitalContent);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDigitalContentMockMvc.perform(post("/api/digital-contents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(digitalContentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DigitalContent in the database
        List<DigitalContent> digitalContentList = digitalContentRepository.findAll();
        assertThat(digitalContentList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkAccessionNoIsRequired() throws Exception {
        int databaseSizeBeforeTest = digitalContentRepository.findAll().size();
        // set the field null
        digitalContent.setAccessionNo(null);

        // Create the DigitalContent, which fails.
        DigitalContentDTO digitalContentDTO = digitalContentMapper.toDto(digitalContent);

        restDigitalContentMockMvc.perform(post("/api/digital-contents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(digitalContentDTO)))
            .andExpect(status().isBadRequest());

        List<DigitalContent> digitalContentList = digitalContentRepository.findAll();
        assertThat(digitalContentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = digitalContentRepository.findAll().size();
        // set the field null
        digitalContent.setTitle(null);

        // Create the DigitalContent, which fails.
        DigitalContentDTO digitalContentDTO = digitalContentMapper.toDto(digitalContent);

        restDigitalContentMockMvc.perform(post("/api/digital-contents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(digitalContentDTO)))
            .andExpect(status().isBadRequest());

        List<DigitalContent> digitalContentList = digitalContentRepository.findAll();
        assertThat(digitalContentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDigitalContents() throws Exception {
        // Initialize the database
        digitalContentRepository.saveAndFlush(digitalContent);

        // Get all the digitalContentList
        restDigitalContentMockMvc.perform(get("/api/digital-contents?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(digitalContent.getId().intValue())))
            .andExpect(jsonPath("$.[*].accessionNo").value(hasItem(DEFAULT_ACCESSION_NO.toString())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].isbnNo").value(hasItem(DEFAULT_ISBN_NO.toString())))
            .andExpect(jsonPath("$.[*].authorName").value(hasItem(DEFAULT_AUTHOR_NAME.toString())))
            .andExpect(jsonPath("$.[*].coverPhotoContentType").value(hasItem(DEFAULT_COVER_PHOTO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].coverPhoto").value(hasItem(Base64Utils.encodeToString(DEFAULT_COVER_PHOTO))))
            .andExpect(jsonPath("$.[*].coverPhotoType").value(hasItem(DEFAULT_COVER_PHOTO_TYPE.toString())))
            .andExpect(jsonPath("$.[*].coverPhotoName").value(hasItem(DEFAULT_COVER_PHOTO_NAME.toString())))
            .andExpect(jsonPath("$.[*].contentContentType").value(hasItem(DEFAULT_CONTENT_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].content").value(hasItem(Base64Utils.encodeToString(DEFAULT_CONTENT))))
            .andExpect(jsonPath("$.[*].contentType").value(hasItem(DEFAULT_CONTENT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].contentName").value(hasItem(DEFAULT_CONTENT_NAME.toString())))
            .andExpect(jsonPath("$.[*].createDate").value(hasItem(DEFAULT_CREATE_DATE.toString())))
            .andExpect(jsonPath("$.[*].updateDate").value(hasItem(DEFAULT_UPDATE_DATE.toString())))
            .andExpect(jsonPath("$.[*].createBy").value(hasItem(DEFAULT_CREATE_BY)))
            .andExpect(jsonPath("$.[*].updateBy").value(hasItem(DEFAULT_UPDATE_BY)));
    }

    @Test
    @Transactional
    public void getDigitalContent() throws Exception {
        // Initialize the database
        digitalContentRepository.saveAndFlush(digitalContent);

        // Get the digitalContent
        restDigitalContentMockMvc.perform(get("/api/digital-contents/{id}", digitalContent.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(digitalContent.getId().intValue()))
            .andExpect(jsonPath("$.accessionNo").value(DEFAULT_ACCESSION_NO.toString()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
            .andExpect(jsonPath("$.isbnNo").value(DEFAULT_ISBN_NO.toString()))
            .andExpect(jsonPath("$.authorName").value(DEFAULT_AUTHOR_NAME.toString()))
            .andExpect(jsonPath("$.coverPhotoContentType").value(DEFAULT_COVER_PHOTO_CONTENT_TYPE))
            .andExpect(jsonPath("$.coverPhoto").value(Base64Utils.encodeToString(DEFAULT_COVER_PHOTO)))
            .andExpect(jsonPath("$.coverPhotoType").value(DEFAULT_COVER_PHOTO_TYPE.toString()))
            .andExpect(jsonPath("$.coverPhotoName").value(DEFAULT_COVER_PHOTO_NAME.toString()))
            .andExpect(jsonPath("$.contentContentType").value(DEFAULT_CONTENT_CONTENT_TYPE))
            .andExpect(jsonPath("$.content").value(Base64Utils.encodeToString(DEFAULT_CONTENT)))
            .andExpect(jsonPath("$.contentType").value(DEFAULT_CONTENT_TYPE.toString()))
            .andExpect(jsonPath("$.contentName").value(DEFAULT_CONTENT_NAME.toString()))
            .andExpect(jsonPath("$.createDate").value(DEFAULT_CREATE_DATE.toString()))
            .andExpect(jsonPath("$.updateDate").value(DEFAULT_UPDATE_DATE.toString()))
            .andExpect(jsonPath("$.createBy").value(DEFAULT_CREATE_BY))
            .andExpect(jsonPath("$.updateBy").value(DEFAULT_UPDATE_BY));
    }

    @Test
    @Transactional
    public void getNonExistingDigitalContent() throws Exception {
        // Get the digitalContent
        restDigitalContentMockMvc.perform(get("/api/digital-contents/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDigitalContent() throws Exception {
        // Initialize the database
        digitalContentRepository.saveAndFlush(digitalContent);
        int databaseSizeBeforeUpdate = digitalContentRepository.findAll().size();

        // Update the digitalContent
        DigitalContent updatedDigitalContent = digitalContentRepository.findOne(digitalContent.getId());
        // Disconnect from session so that the updates on updatedDigitalContent are not directly saved in db
        em.detach(updatedDigitalContent);
        updatedDigitalContent
            .accessionNo(UPDATED_ACCESSION_NO)
            .title(UPDATED_TITLE)
            .isbnNo(UPDATED_ISBN_NO)
            .authorName(UPDATED_AUTHOR_NAME)
            .coverPhoto(UPDATED_COVER_PHOTO)
            .coverPhotoContentType(UPDATED_COVER_PHOTO_CONTENT_TYPE)
            .coverPhotoType(UPDATED_COVER_PHOTO_TYPE)
            .coverPhotoName(UPDATED_COVER_PHOTO_NAME)
            .content(UPDATED_CONTENT)
            .contentContentType(UPDATED_CONTENT_CONTENT_TYPE)
            .contentType(UPDATED_CONTENT_TYPE)
            .contentName(UPDATED_CONTENT_NAME)
            .createDate(UPDATED_CREATE_DATE)
            .updateDate(UPDATED_UPDATE_DATE)
            .createBy(UPDATED_CREATE_BY)
            .updateBy(UPDATED_UPDATE_BY);
        DigitalContentDTO digitalContentDTO = digitalContentMapper.toDto(updatedDigitalContent);

        restDigitalContentMockMvc.perform(put("/api/digital-contents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(digitalContentDTO)))
            .andExpect(status().isOk());

        // Validate the DigitalContent in the database
        List<DigitalContent> digitalContentList = digitalContentRepository.findAll();
        assertThat(digitalContentList).hasSize(databaseSizeBeforeUpdate);
        DigitalContent testDigitalContent = digitalContentList.get(digitalContentList.size() - 1);
        assertThat(testDigitalContent.getAccessionNo()).isEqualTo(UPDATED_ACCESSION_NO);
        assertThat(testDigitalContent.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testDigitalContent.getIsbnNo()).isEqualTo(UPDATED_ISBN_NO);
        assertThat(testDigitalContent.getAuthorName()).isEqualTo(UPDATED_AUTHOR_NAME);
        assertThat(testDigitalContent.getCoverPhoto()).isEqualTo(UPDATED_COVER_PHOTO);
        assertThat(testDigitalContent.getCoverPhotoContentType()).isEqualTo(UPDATED_COVER_PHOTO_CONTENT_TYPE);
        assertThat(testDigitalContent.getCoverPhotoType()).isEqualTo(UPDATED_COVER_PHOTO_TYPE);
        assertThat(testDigitalContent.getCoverPhotoName()).isEqualTo(UPDATED_COVER_PHOTO_NAME);
        assertThat(testDigitalContent.getContent()).isEqualTo(UPDATED_CONTENT);
        assertThat(testDigitalContent.getContentContentType()).isEqualTo(UPDATED_CONTENT_CONTENT_TYPE);
        assertThat(testDigitalContent.getContentType()).isEqualTo(UPDATED_CONTENT_TYPE);
        assertThat(testDigitalContent.getContentName()).isEqualTo(UPDATED_CONTENT_NAME);
        assertThat(testDigitalContent.getCreateDate()).isEqualTo(UPDATED_CREATE_DATE);
        assertThat(testDigitalContent.getUpdateDate()).isEqualTo(UPDATED_UPDATE_DATE);
        assertThat(testDigitalContent.getCreateBy()).isEqualTo(UPDATED_CREATE_BY);
        assertThat(testDigitalContent.getUpdateBy()).isEqualTo(UPDATED_UPDATE_BY);
    }

    @Test
    @Transactional
    public void updateNonExistingDigitalContent() throws Exception {
        int databaseSizeBeforeUpdate = digitalContentRepository.findAll().size();

        // Create the DigitalContent
        DigitalContentDTO digitalContentDTO = digitalContentMapper.toDto(digitalContent);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restDigitalContentMockMvc.perform(put("/api/digital-contents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(digitalContentDTO)))
            .andExpect(status().isCreated());

        // Validate the DigitalContent in the database
        List<DigitalContent> digitalContentList = digitalContentRepository.findAll();
        assertThat(digitalContentList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteDigitalContent() throws Exception {
        // Initialize the database
        digitalContentRepository.saveAndFlush(digitalContent);
        int databaseSizeBeforeDelete = digitalContentRepository.findAll().size();

        // Get the digitalContent
        restDigitalContentMockMvc.perform(delete("/api/digital-contents/{id}", digitalContent.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<DigitalContent> digitalContentList = digitalContentRepository.findAll();
        assertThat(digitalContentList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DigitalContent.class);
        DigitalContent digitalContent1 = new DigitalContent();
        digitalContent1.setId(1L);
        DigitalContent digitalContent2 = new DigitalContent();
        digitalContent2.setId(digitalContent1.getId());
        assertThat(digitalContent1).isEqualTo(digitalContent2);
        digitalContent2.setId(2L);
        assertThat(digitalContent1).isNotEqualTo(digitalContent2);
        digitalContent1.setId(null);
        assertThat(digitalContent1).isNotEqualTo(digitalContent2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DigitalContentDTO.class);
        DigitalContentDTO digitalContentDTO1 = new DigitalContentDTO();
        digitalContentDTO1.setId(1L);
        DigitalContentDTO digitalContentDTO2 = new DigitalContentDTO();
        assertThat(digitalContentDTO1).isNotEqualTo(digitalContentDTO2);
        digitalContentDTO2.setId(digitalContentDTO1.getId());
        assertThat(digitalContentDTO1).isEqualTo(digitalContentDTO2);
        digitalContentDTO2.setId(2L);
        assertThat(digitalContentDTO1).isNotEqualTo(digitalContentDTO2);
        digitalContentDTO1.setId(null);
        assertThat(digitalContentDTO1).isNotEqualTo(digitalContentDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(digitalContentMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(digitalContentMapper.fromId(null)).isNull();
    }
}
