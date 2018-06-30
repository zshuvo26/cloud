package cloud.web.rest;

import cloud.CloudApp;

import cloud.domain.BookInfo;
import cloud.repository.BookInfoRepository;
import cloud.service.BookInfoService;
import cloud.service.dto.BookInfoDTO;
import cloud.service.mapper.BookInfoMapper;
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

import cloud.domain.enumeration.BookCondition;
import cloud.domain.enumeration.ContBookLang;
/**
 * Test class for the BookInfoResource REST controller.
 *
 * @see BookInfoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CloudApp.class)
public class BookInfoResourceIntTest {

    private static final String DEFAULT_ACCESSION_NO = "AAAAAAAAAA";
    private static final String UPDATED_ACCESSION_NO = "BBBBBBBBBB";

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_ISBN_NO = "AAAAAAAAAA";
    private static final String UPDATED_ISBN_NO = "BBBBBBBBBB";

    private static final String DEFAULT_AUTHOR_NAME = "AAAAAAAAAA";
    private static final String UPDATED_AUTHOR_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_BILL_NO = "AAAAAAAAAA";
    private static final String UPDATED_BILL_NO = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_BILL_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_BILL_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final byte[] DEFAULT_COVER_PHOTO = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_COVER_PHOTO = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_COVER_PHOTO_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_COVER_PHOTO_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_COVER_PHOTO_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_COVER_PHOTO_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_COVER_PHOTO_NAME = "AAAAAAAAAA";
    private static final String UPDATED_COVER_PHOTO_NAME = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_CREATE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATE_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_UPDATE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_UPDATE_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_CREATE_BY = 1;
    private static final Integer UPDATED_CREATE_BY = 2;

    private static final BookCondition DEFAULT_BOOK_CONDITION = BookCondition.FRESH;
    private static final BookCondition UPDATED_BOOK_CONDITION = BookCondition.USED;

    private static final ContBookLang DEFAULT_CONT_BOOK_LANG = ContBookLang.ENGLISH;
    private static final ContBookLang UPDATED_CONT_BOOK_LANG = ContBookLang.BANGLA;

    private static final Integer DEFAULT_UPDATE_BY = 1;
    private static final Integer UPDATED_UPDATE_BY = 2;

    @Autowired
    private BookInfoRepository bookInfoRepository;

    @Autowired
    private BookInfoMapper bookInfoMapper;

    @Autowired
    private BookInfoService bookInfoService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restBookInfoMockMvc;

    private BookInfo bookInfo;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final BookInfoResource bookInfoResource = new BookInfoResource(bookInfoService);
        this.restBookInfoMockMvc = MockMvcBuilders.standaloneSetup(bookInfoResource)
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
    public static BookInfo createEntity(EntityManager em) {
        BookInfo bookInfo = new BookInfo()
            .accessionNo(DEFAULT_ACCESSION_NO)
            .title(DEFAULT_TITLE)
            .isbnNo(DEFAULT_ISBN_NO)
            .authorName(DEFAULT_AUTHOR_NAME)
            .billNo(DEFAULT_BILL_NO)
            .billDate(DEFAULT_BILL_DATE)
            .coverPhoto(DEFAULT_COVER_PHOTO)
            .coverPhotoContentType(DEFAULT_COVER_PHOTO_CONTENT_TYPE)
            .coverPhotoType(DEFAULT_COVER_PHOTO_TYPE)
            .coverPhotoName(DEFAULT_COVER_PHOTO_NAME)
            .createDate(DEFAULT_CREATE_DATE)
            .updateDate(DEFAULT_UPDATE_DATE)
            .createBy(DEFAULT_CREATE_BY)
            .bookCondition(DEFAULT_BOOK_CONDITION)
            .contBookLang(DEFAULT_CONT_BOOK_LANG)
            .updateBy(DEFAULT_UPDATE_BY);
        return bookInfo;
    }

    @Before
    public void initTest() {
        bookInfo = createEntity(em);
    }

    @Test
    @Transactional
    public void createBookInfo() throws Exception {
        int databaseSizeBeforeCreate = bookInfoRepository.findAll().size();

        // Create the BookInfo
        BookInfoDTO bookInfoDTO = bookInfoMapper.toDto(bookInfo);
        restBookInfoMockMvc.perform(post("/api/book-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bookInfoDTO)))
            .andExpect(status().isCreated());

        // Validate the BookInfo in the database
        List<BookInfo> bookInfoList = bookInfoRepository.findAll();
        assertThat(bookInfoList).hasSize(databaseSizeBeforeCreate + 1);
        BookInfo testBookInfo = bookInfoList.get(bookInfoList.size() - 1);
        assertThat(testBookInfo.getAccessionNo()).isEqualTo(DEFAULT_ACCESSION_NO);
        assertThat(testBookInfo.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testBookInfo.getIsbnNo()).isEqualTo(DEFAULT_ISBN_NO);
        assertThat(testBookInfo.getAuthorName()).isEqualTo(DEFAULT_AUTHOR_NAME);
        assertThat(testBookInfo.getBillNo()).isEqualTo(DEFAULT_BILL_NO);
        assertThat(testBookInfo.getBillDate()).isEqualTo(DEFAULT_BILL_DATE);
        assertThat(testBookInfo.getCoverPhoto()).isEqualTo(DEFAULT_COVER_PHOTO);
        assertThat(testBookInfo.getCoverPhotoContentType()).isEqualTo(DEFAULT_COVER_PHOTO_CONTENT_TYPE);
        assertThat(testBookInfo.getCoverPhotoType()).isEqualTo(DEFAULT_COVER_PHOTO_TYPE);
        assertThat(testBookInfo.getCoverPhotoName()).isEqualTo(DEFAULT_COVER_PHOTO_NAME);
        assertThat(testBookInfo.getCreateDate()).isEqualTo(DEFAULT_CREATE_DATE);
        assertThat(testBookInfo.getUpdateDate()).isEqualTo(DEFAULT_UPDATE_DATE);
        assertThat(testBookInfo.getCreateBy()).isEqualTo(DEFAULT_CREATE_BY);
        assertThat(testBookInfo.getBookCondition()).isEqualTo(DEFAULT_BOOK_CONDITION);
        assertThat(testBookInfo.getContBookLang()).isEqualTo(DEFAULT_CONT_BOOK_LANG);
        assertThat(testBookInfo.getUpdateBy()).isEqualTo(DEFAULT_UPDATE_BY);
    }

    @Test
    @Transactional
    public void createBookInfoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = bookInfoRepository.findAll().size();

        // Create the BookInfo with an existing ID
        bookInfo.setId(1L);
        BookInfoDTO bookInfoDTO = bookInfoMapper.toDto(bookInfo);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBookInfoMockMvc.perform(post("/api/book-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bookInfoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the BookInfo in the database
        List<BookInfo> bookInfoList = bookInfoRepository.findAll();
        assertThat(bookInfoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkAccessionNoIsRequired() throws Exception {
        int databaseSizeBeforeTest = bookInfoRepository.findAll().size();
        // set the field null
        bookInfo.setAccessionNo(null);

        // Create the BookInfo, which fails.
        BookInfoDTO bookInfoDTO = bookInfoMapper.toDto(bookInfo);

        restBookInfoMockMvc.perform(post("/api/book-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bookInfoDTO)))
            .andExpect(status().isBadRequest());

        List<BookInfo> bookInfoList = bookInfoRepository.findAll();
        assertThat(bookInfoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = bookInfoRepository.findAll().size();
        // set the field null
        bookInfo.setTitle(null);

        // Create the BookInfo, which fails.
        BookInfoDTO bookInfoDTO = bookInfoMapper.toDto(bookInfo);

        restBookInfoMockMvc.perform(post("/api/book-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bookInfoDTO)))
            .andExpect(status().isBadRequest());

        List<BookInfo> bookInfoList = bookInfoRepository.findAll();
        assertThat(bookInfoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllBookInfos() throws Exception {
        // Initialize the database
        bookInfoRepository.saveAndFlush(bookInfo);

        // Get all the bookInfoList
        restBookInfoMockMvc.perform(get("/api/book-infos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bookInfo.getId().intValue())))
            .andExpect(jsonPath("$.[*].accessionNo").value(hasItem(DEFAULT_ACCESSION_NO.toString())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].isbnNo").value(hasItem(DEFAULT_ISBN_NO.toString())))
            .andExpect(jsonPath("$.[*].authorName").value(hasItem(DEFAULT_AUTHOR_NAME.toString())))
            .andExpect(jsonPath("$.[*].billNo").value(hasItem(DEFAULT_BILL_NO.toString())))
            .andExpect(jsonPath("$.[*].billDate").value(hasItem(DEFAULT_BILL_DATE.toString())))
            .andExpect(jsonPath("$.[*].coverPhotoContentType").value(hasItem(DEFAULT_COVER_PHOTO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].coverPhoto").value(hasItem(Base64Utils.encodeToString(DEFAULT_COVER_PHOTO))))
            .andExpect(jsonPath("$.[*].coverPhotoType").value(hasItem(DEFAULT_COVER_PHOTO_TYPE.toString())))
            .andExpect(jsonPath("$.[*].coverPhotoName").value(hasItem(DEFAULT_COVER_PHOTO_NAME.toString())))
            .andExpect(jsonPath("$.[*].createDate").value(hasItem(DEFAULT_CREATE_DATE.toString())))
            .andExpect(jsonPath("$.[*].updateDate").value(hasItem(DEFAULT_UPDATE_DATE.toString())))
            .andExpect(jsonPath("$.[*].createBy").value(hasItem(DEFAULT_CREATE_BY)))
            .andExpect(jsonPath("$.[*].bookCondition").value(hasItem(DEFAULT_BOOK_CONDITION.toString())))
            .andExpect(jsonPath("$.[*].contBookLang").value(hasItem(DEFAULT_CONT_BOOK_LANG.toString())))
            .andExpect(jsonPath("$.[*].updateBy").value(hasItem(DEFAULT_UPDATE_BY)));
    }

    @Test
    @Transactional
    public void getBookInfo() throws Exception {
        // Initialize the database
        bookInfoRepository.saveAndFlush(bookInfo);

        // Get the bookInfo
        restBookInfoMockMvc.perform(get("/api/book-infos/{id}", bookInfo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(bookInfo.getId().intValue()))
            .andExpect(jsonPath("$.accessionNo").value(DEFAULT_ACCESSION_NO.toString()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
            .andExpect(jsonPath("$.isbnNo").value(DEFAULT_ISBN_NO.toString()))
            .andExpect(jsonPath("$.authorName").value(DEFAULT_AUTHOR_NAME.toString()))
            .andExpect(jsonPath("$.billNo").value(DEFAULT_BILL_NO.toString()))
            .andExpect(jsonPath("$.billDate").value(DEFAULT_BILL_DATE.toString()))
            .andExpect(jsonPath("$.coverPhotoContentType").value(DEFAULT_COVER_PHOTO_CONTENT_TYPE))
            .andExpect(jsonPath("$.coverPhoto").value(Base64Utils.encodeToString(DEFAULT_COVER_PHOTO)))
            .andExpect(jsonPath("$.coverPhotoType").value(DEFAULT_COVER_PHOTO_TYPE.toString()))
            .andExpect(jsonPath("$.coverPhotoName").value(DEFAULT_COVER_PHOTO_NAME.toString()))
            .andExpect(jsonPath("$.createDate").value(DEFAULT_CREATE_DATE.toString()))
            .andExpect(jsonPath("$.updateDate").value(DEFAULT_UPDATE_DATE.toString()))
            .andExpect(jsonPath("$.createBy").value(DEFAULT_CREATE_BY))
            .andExpect(jsonPath("$.bookCondition").value(DEFAULT_BOOK_CONDITION.toString()))
            .andExpect(jsonPath("$.contBookLang").value(DEFAULT_CONT_BOOK_LANG.toString()))
            .andExpect(jsonPath("$.updateBy").value(DEFAULT_UPDATE_BY));
    }

    @Test
    @Transactional
    public void getNonExistingBookInfo() throws Exception {
        // Get the bookInfo
        restBookInfoMockMvc.perform(get("/api/book-infos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBookInfo() throws Exception {
        // Initialize the database
        bookInfoRepository.saveAndFlush(bookInfo);
        int databaseSizeBeforeUpdate = bookInfoRepository.findAll().size();

        // Update the bookInfo
        BookInfo updatedBookInfo = bookInfoRepository.findOne(bookInfo.getId());
        // Disconnect from session so that the updates on updatedBookInfo are not directly saved in db
        em.detach(updatedBookInfo);
        updatedBookInfo
            .accessionNo(UPDATED_ACCESSION_NO)
            .title(UPDATED_TITLE)
            .isbnNo(UPDATED_ISBN_NO)
            .authorName(UPDATED_AUTHOR_NAME)
            .billNo(UPDATED_BILL_NO)
            .billDate(UPDATED_BILL_DATE)
            .coverPhoto(UPDATED_COVER_PHOTO)
            .coverPhotoContentType(UPDATED_COVER_PHOTO_CONTENT_TYPE)
            .coverPhotoType(UPDATED_COVER_PHOTO_TYPE)
            .coverPhotoName(UPDATED_COVER_PHOTO_NAME)
            .createDate(UPDATED_CREATE_DATE)
            .updateDate(UPDATED_UPDATE_DATE)
            .createBy(UPDATED_CREATE_BY)
            .bookCondition(UPDATED_BOOK_CONDITION)
            .contBookLang(UPDATED_CONT_BOOK_LANG)
            .updateBy(UPDATED_UPDATE_BY);
        BookInfoDTO bookInfoDTO = bookInfoMapper.toDto(updatedBookInfo);

        restBookInfoMockMvc.perform(put("/api/book-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bookInfoDTO)))
            .andExpect(status().isOk());

        // Validate the BookInfo in the database
        List<BookInfo> bookInfoList = bookInfoRepository.findAll();
        assertThat(bookInfoList).hasSize(databaseSizeBeforeUpdate);
        BookInfo testBookInfo = bookInfoList.get(bookInfoList.size() - 1);
        assertThat(testBookInfo.getAccessionNo()).isEqualTo(UPDATED_ACCESSION_NO);
        assertThat(testBookInfo.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testBookInfo.getIsbnNo()).isEqualTo(UPDATED_ISBN_NO);
        assertThat(testBookInfo.getAuthorName()).isEqualTo(UPDATED_AUTHOR_NAME);
        assertThat(testBookInfo.getBillNo()).isEqualTo(UPDATED_BILL_NO);
        assertThat(testBookInfo.getBillDate()).isEqualTo(UPDATED_BILL_DATE);
        assertThat(testBookInfo.getCoverPhoto()).isEqualTo(UPDATED_COVER_PHOTO);
        assertThat(testBookInfo.getCoverPhotoContentType()).isEqualTo(UPDATED_COVER_PHOTO_CONTENT_TYPE);
        assertThat(testBookInfo.getCoverPhotoType()).isEqualTo(UPDATED_COVER_PHOTO_TYPE);
        assertThat(testBookInfo.getCoverPhotoName()).isEqualTo(UPDATED_COVER_PHOTO_NAME);
        assertThat(testBookInfo.getCreateDate()).isEqualTo(UPDATED_CREATE_DATE);
        assertThat(testBookInfo.getUpdateDate()).isEqualTo(UPDATED_UPDATE_DATE);
        assertThat(testBookInfo.getCreateBy()).isEqualTo(UPDATED_CREATE_BY);
        assertThat(testBookInfo.getBookCondition()).isEqualTo(UPDATED_BOOK_CONDITION);
        assertThat(testBookInfo.getContBookLang()).isEqualTo(UPDATED_CONT_BOOK_LANG);
        assertThat(testBookInfo.getUpdateBy()).isEqualTo(UPDATED_UPDATE_BY);
    }

    @Test
    @Transactional
    public void updateNonExistingBookInfo() throws Exception {
        int databaseSizeBeforeUpdate = bookInfoRepository.findAll().size();

        // Create the BookInfo
        BookInfoDTO bookInfoDTO = bookInfoMapper.toDto(bookInfo);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restBookInfoMockMvc.perform(put("/api/book-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bookInfoDTO)))
            .andExpect(status().isCreated());

        // Validate the BookInfo in the database
        List<BookInfo> bookInfoList = bookInfoRepository.findAll();
        assertThat(bookInfoList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteBookInfo() throws Exception {
        // Initialize the database
        bookInfoRepository.saveAndFlush(bookInfo);
        int databaseSizeBeforeDelete = bookInfoRepository.findAll().size();

        // Get the bookInfo
        restBookInfoMockMvc.perform(delete("/api/book-infos/{id}", bookInfo.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<BookInfo> bookInfoList = bookInfoRepository.findAll();
        assertThat(bookInfoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BookInfo.class);
        BookInfo bookInfo1 = new BookInfo();
        bookInfo1.setId(1L);
        BookInfo bookInfo2 = new BookInfo();
        bookInfo2.setId(bookInfo1.getId());
        assertThat(bookInfo1).isEqualTo(bookInfo2);
        bookInfo2.setId(2L);
        assertThat(bookInfo1).isNotEqualTo(bookInfo2);
        bookInfo1.setId(null);
        assertThat(bookInfo1).isNotEqualTo(bookInfo2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(BookInfoDTO.class);
        BookInfoDTO bookInfoDTO1 = new BookInfoDTO();
        bookInfoDTO1.setId(1L);
        BookInfoDTO bookInfoDTO2 = new BookInfoDTO();
        assertThat(bookInfoDTO1).isNotEqualTo(bookInfoDTO2);
        bookInfoDTO2.setId(bookInfoDTO1.getId());
        assertThat(bookInfoDTO1).isEqualTo(bookInfoDTO2);
        bookInfoDTO2.setId(2L);
        assertThat(bookInfoDTO1).isNotEqualTo(bookInfoDTO2);
        bookInfoDTO1.setId(null);
        assertThat(bookInfoDTO1).isNotEqualTo(bookInfoDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(bookInfoMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(bookInfoMapper.fromId(null)).isNull();
    }
}
