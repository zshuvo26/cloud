package cloud.web.rest;

import cloud.CloudApp;

import cloud.domain.BookReturn;
import cloud.repository.BookReturnRepository;
import cloud.service.BookReturnService;
import cloud.service.dto.BookReturnDTO;
import cloud.service.mapper.BookReturnMapper;
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
 * Test class for the BookReturnResource REST controller.
 *
 * @see BookReturnResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CloudApp.class)
public class BookReturnResourceIntTest {

    private static final String DEFAULT_RECEIVED_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_RECEIVED_STATUS = "BBBBBBBBBB";

    private static final String DEFAULT_TOTAL_FINE = "AAAAAAAAAA";
    private static final String UPDATED_TOTAL_FINE = "BBBBBBBBBB";

    private static final String DEFAULT_FINE_DEPOSIT = "AAAAAAAAAA";
    private static final String UPDATED_FINE_DEPOSIT = "BBBBBBBBBB";

    private static final Boolean DEFAULT_REMISSION_STATUS = false;
    private static final Boolean UPDATED_REMISSION_STATUS = true;

    private static final String DEFAULT_COMPENSATION = "AAAAAAAAAA";
    private static final String UPDATED_COMPENSATION = "BBBBBBBBBB";

    private static final String DEFAULT_COMPENSATION_DEPOSIT = "AAAAAAAAAA";
    private static final String UPDATED_COMPENSATION_DEPOSIT = "BBBBBBBBBB";

    private static final String DEFAULT_COMPENSATION_FINE_DEPOSIT = "AAAAAAAAAA";
    private static final String UPDATED_COMPENSATION_FINE_DEPOSIT = "BBBBBBBBBB";

    private static final Boolean DEFAULT_REMISSION_COMPENSATION_STATUS = false;
    private static final Boolean UPDATED_REMISSION_COMPENSATION_STATUS = true;

    private static final Boolean DEFAULT_CF_FINE_STATUS = false;
    private static final Boolean UPDATED_CF_FINE_STATUS = true;

    private static final Boolean DEFAULT_CF_COMPENSATION_STATUS = false;
    private static final Boolean UPDATED_CF_COMPENSATION_STATUS = true;

    private static final LocalDate DEFAULT_CREATE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATE_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_UPDATE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_UPDATE_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_CREATE_BY = 1;
    private static final Integer UPDATED_CREATE_BY = 2;

    private static final Integer DEFAULT_UPDATE_BY = 1;
    private static final Integer UPDATED_UPDATE_BY = 2;

    @Autowired
    private BookReturnRepository bookReturnRepository;

    @Autowired
    private BookReturnMapper bookReturnMapper;

    @Autowired
    private BookReturnService bookReturnService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restBookReturnMockMvc;

    private BookReturn bookReturn;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final BookReturnResource bookReturnResource = new BookReturnResource(bookReturnService);
        this.restBookReturnMockMvc = MockMvcBuilders.standaloneSetup(bookReturnResource)
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
    public static BookReturn createEntity(EntityManager em) {
        BookReturn bookReturn = new BookReturn()
            .receivedStatus(DEFAULT_RECEIVED_STATUS)
            .totalFine(DEFAULT_TOTAL_FINE)
            .fineDeposit(DEFAULT_FINE_DEPOSIT)
            .remissionStatus(DEFAULT_REMISSION_STATUS)
            .compensation(DEFAULT_COMPENSATION)
            .compensationDeposit(DEFAULT_COMPENSATION_DEPOSIT)
            .compensationFineDeposit(DEFAULT_COMPENSATION_FINE_DEPOSIT)
            .remissionCompensationStatus(DEFAULT_REMISSION_COMPENSATION_STATUS)
            .cfFineStatus(DEFAULT_CF_FINE_STATUS)
            .cfCompensationStatus(DEFAULT_CF_COMPENSATION_STATUS)
            .createDate(DEFAULT_CREATE_DATE)
            .updateDate(DEFAULT_UPDATE_DATE)
            .createBy(DEFAULT_CREATE_BY)
            .updateBy(DEFAULT_UPDATE_BY);
        return bookReturn;
    }

    @Before
    public void initTest() {
        bookReturn = createEntity(em);
    }

    @Test
    @Transactional
    public void createBookReturn() throws Exception {
        int databaseSizeBeforeCreate = bookReturnRepository.findAll().size();

        // Create the BookReturn
        BookReturnDTO bookReturnDTO = bookReturnMapper.toDto(bookReturn);
        restBookReturnMockMvc.perform(post("/api/book-returns")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bookReturnDTO)))
            .andExpect(status().isCreated());

        // Validate the BookReturn in the database
        List<BookReturn> bookReturnList = bookReturnRepository.findAll();
        assertThat(bookReturnList).hasSize(databaseSizeBeforeCreate + 1);
        BookReturn testBookReturn = bookReturnList.get(bookReturnList.size() - 1);
        assertThat(testBookReturn.getReceivedStatus()).isEqualTo(DEFAULT_RECEIVED_STATUS);
        assertThat(testBookReturn.getTotalFine()).isEqualTo(DEFAULT_TOTAL_FINE);
        assertThat(testBookReturn.getFineDeposit()).isEqualTo(DEFAULT_FINE_DEPOSIT);
        assertThat(testBookReturn.isRemissionStatus()).isEqualTo(DEFAULT_REMISSION_STATUS);
        assertThat(testBookReturn.getCompensation()).isEqualTo(DEFAULT_COMPENSATION);
        assertThat(testBookReturn.getCompensationDeposit()).isEqualTo(DEFAULT_COMPENSATION_DEPOSIT);
        assertThat(testBookReturn.getCompensationFineDeposit()).isEqualTo(DEFAULT_COMPENSATION_FINE_DEPOSIT);
        assertThat(testBookReturn.isRemissionCompensationStatus()).isEqualTo(DEFAULT_REMISSION_COMPENSATION_STATUS);
        assertThat(testBookReturn.isCfFineStatus()).isEqualTo(DEFAULT_CF_FINE_STATUS);
        assertThat(testBookReturn.isCfCompensationStatus()).isEqualTo(DEFAULT_CF_COMPENSATION_STATUS);
        assertThat(testBookReturn.getCreateDate()).isEqualTo(DEFAULT_CREATE_DATE);
        assertThat(testBookReturn.getUpdateDate()).isEqualTo(DEFAULT_UPDATE_DATE);
        assertThat(testBookReturn.getCreateBy()).isEqualTo(DEFAULT_CREATE_BY);
        assertThat(testBookReturn.getUpdateBy()).isEqualTo(DEFAULT_UPDATE_BY);
    }

    @Test
    @Transactional
    public void createBookReturnWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = bookReturnRepository.findAll().size();

        // Create the BookReturn with an existing ID
        bookReturn.setId(1L);
        BookReturnDTO bookReturnDTO = bookReturnMapper.toDto(bookReturn);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBookReturnMockMvc.perform(post("/api/book-returns")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bookReturnDTO)))
            .andExpect(status().isBadRequest());

        // Validate the BookReturn in the database
        List<BookReturn> bookReturnList = bookReturnRepository.findAll();
        assertThat(bookReturnList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllBookReturns() throws Exception {
        // Initialize the database
        bookReturnRepository.saveAndFlush(bookReturn);

        // Get all the bookReturnList
        restBookReturnMockMvc.perform(get("/api/book-returns?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bookReturn.getId().intValue())))
            .andExpect(jsonPath("$.[*].receivedStatus").value(hasItem(DEFAULT_RECEIVED_STATUS.toString())))
            .andExpect(jsonPath("$.[*].totalFine").value(hasItem(DEFAULT_TOTAL_FINE.toString())))
            .andExpect(jsonPath("$.[*].fineDeposit").value(hasItem(DEFAULT_FINE_DEPOSIT.toString())))
            .andExpect(jsonPath("$.[*].remissionStatus").value(hasItem(DEFAULT_REMISSION_STATUS.booleanValue())))
            .andExpect(jsonPath("$.[*].compensation").value(hasItem(DEFAULT_COMPENSATION.toString())))
            .andExpect(jsonPath("$.[*].compensationDeposit").value(hasItem(DEFAULT_COMPENSATION_DEPOSIT.toString())))
            .andExpect(jsonPath("$.[*].compensationFineDeposit").value(hasItem(DEFAULT_COMPENSATION_FINE_DEPOSIT.toString())))
            .andExpect(jsonPath("$.[*].remissionCompensationStatus").value(hasItem(DEFAULT_REMISSION_COMPENSATION_STATUS.booleanValue())))
            .andExpect(jsonPath("$.[*].cfFineStatus").value(hasItem(DEFAULT_CF_FINE_STATUS.booleanValue())))
            .andExpect(jsonPath("$.[*].cfCompensationStatus").value(hasItem(DEFAULT_CF_COMPENSATION_STATUS.booleanValue())))
            .andExpect(jsonPath("$.[*].createDate").value(hasItem(DEFAULT_CREATE_DATE.toString())))
            .andExpect(jsonPath("$.[*].updateDate").value(hasItem(DEFAULT_UPDATE_DATE.toString())))
            .andExpect(jsonPath("$.[*].createBy").value(hasItem(DEFAULT_CREATE_BY)))
            .andExpect(jsonPath("$.[*].updateBy").value(hasItem(DEFAULT_UPDATE_BY)));
    }

    @Test
    @Transactional
    public void getBookReturn() throws Exception {
        // Initialize the database
        bookReturnRepository.saveAndFlush(bookReturn);

        // Get the bookReturn
        restBookReturnMockMvc.perform(get("/api/book-returns/{id}", bookReturn.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(bookReturn.getId().intValue()))
            .andExpect(jsonPath("$.receivedStatus").value(DEFAULT_RECEIVED_STATUS.toString()))
            .andExpect(jsonPath("$.totalFine").value(DEFAULT_TOTAL_FINE.toString()))
            .andExpect(jsonPath("$.fineDeposit").value(DEFAULT_FINE_DEPOSIT.toString()))
            .andExpect(jsonPath("$.remissionStatus").value(DEFAULT_REMISSION_STATUS.booleanValue()))
            .andExpect(jsonPath("$.compensation").value(DEFAULT_COMPENSATION.toString()))
            .andExpect(jsonPath("$.compensationDeposit").value(DEFAULT_COMPENSATION_DEPOSIT.toString()))
            .andExpect(jsonPath("$.compensationFineDeposit").value(DEFAULT_COMPENSATION_FINE_DEPOSIT.toString()))
            .andExpect(jsonPath("$.remissionCompensationStatus").value(DEFAULT_REMISSION_COMPENSATION_STATUS.booleanValue()))
            .andExpect(jsonPath("$.cfFineStatus").value(DEFAULT_CF_FINE_STATUS.booleanValue()))
            .andExpect(jsonPath("$.cfCompensationStatus").value(DEFAULT_CF_COMPENSATION_STATUS.booleanValue()))
            .andExpect(jsonPath("$.createDate").value(DEFAULT_CREATE_DATE.toString()))
            .andExpect(jsonPath("$.updateDate").value(DEFAULT_UPDATE_DATE.toString()))
            .andExpect(jsonPath("$.createBy").value(DEFAULT_CREATE_BY))
            .andExpect(jsonPath("$.updateBy").value(DEFAULT_UPDATE_BY));
    }

    @Test
    @Transactional
    public void getNonExistingBookReturn() throws Exception {
        // Get the bookReturn
        restBookReturnMockMvc.perform(get("/api/book-returns/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBookReturn() throws Exception {
        // Initialize the database
        bookReturnRepository.saveAndFlush(bookReturn);
        int databaseSizeBeforeUpdate = bookReturnRepository.findAll().size();

        // Update the bookReturn
        BookReturn updatedBookReturn = bookReturnRepository.findOne(bookReturn.getId());
        // Disconnect from session so that the updates on updatedBookReturn are not directly saved in db
        em.detach(updatedBookReturn);
        updatedBookReturn
            .receivedStatus(UPDATED_RECEIVED_STATUS)
            .totalFine(UPDATED_TOTAL_FINE)
            .fineDeposit(UPDATED_FINE_DEPOSIT)
            .remissionStatus(UPDATED_REMISSION_STATUS)
            .compensation(UPDATED_COMPENSATION)
            .compensationDeposit(UPDATED_COMPENSATION_DEPOSIT)
            .compensationFineDeposit(UPDATED_COMPENSATION_FINE_DEPOSIT)
            .remissionCompensationStatus(UPDATED_REMISSION_COMPENSATION_STATUS)
            .cfFineStatus(UPDATED_CF_FINE_STATUS)
            .cfCompensationStatus(UPDATED_CF_COMPENSATION_STATUS)
            .createDate(UPDATED_CREATE_DATE)
            .updateDate(UPDATED_UPDATE_DATE)
            .createBy(UPDATED_CREATE_BY)
            .updateBy(UPDATED_UPDATE_BY);
        BookReturnDTO bookReturnDTO = bookReturnMapper.toDto(updatedBookReturn);

        restBookReturnMockMvc.perform(put("/api/book-returns")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bookReturnDTO)))
            .andExpect(status().isOk());

        // Validate the BookReturn in the database
        List<BookReturn> bookReturnList = bookReturnRepository.findAll();
        assertThat(bookReturnList).hasSize(databaseSizeBeforeUpdate);
        BookReturn testBookReturn = bookReturnList.get(bookReturnList.size() - 1);
        assertThat(testBookReturn.getReceivedStatus()).isEqualTo(UPDATED_RECEIVED_STATUS);
        assertThat(testBookReturn.getTotalFine()).isEqualTo(UPDATED_TOTAL_FINE);
        assertThat(testBookReturn.getFineDeposit()).isEqualTo(UPDATED_FINE_DEPOSIT);
        assertThat(testBookReturn.isRemissionStatus()).isEqualTo(UPDATED_REMISSION_STATUS);
        assertThat(testBookReturn.getCompensation()).isEqualTo(UPDATED_COMPENSATION);
        assertThat(testBookReturn.getCompensationDeposit()).isEqualTo(UPDATED_COMPENSATION_DEPOSIT);
        assertThat(testBookReturn.getCompensationFineDeposit()).isEqualTo(UPDATED_COMPENSATION_FINE_DEPOSIT);
        assertThat(testBookReturn.isRemissionCompensationStatus()).isEqualTo(UPDATED_REMISSION_COMPENSATION_STATUS);
        assertThat(testBookReturn.isCfFineStatus()).isEqualTo(UPDATED_CF_FINE_STATUS);
        assertThat(testBookReturn.isCfCompensationStatus()).isEqualTo(UPDATED_CF_COMPENSATION_STATUS);
        assertThat(testBookReturn.getCreateDate()).isEqualTo(UPDATED_CREATE_DATE);
        assertThat(testBookReturn.getUpdateDate()).isEqualTo(UPDATED_UPDATE_DATE);
        assertThat(testBookReturn.getCreateBy()).isEqualTo(UPDATED_CREATE_BY);
        assertThat(testBookReturn.getUpdateBy()).isEqualTo(UPDATED_UPDATE_BY);
    }

    @Test
    @Transactional
    public void updateNonExistingBookReturn() throws Exception {
        int databaseSizeBeforeUpdate = bookReturnRepository.findAll().size();

        // Create the BookReturn
        BookReturnDTO bookReturnDTO = bookReturnMapper.toDto(bookReturn);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restBookReturnMockMvc.perform(put("/api/book-returns")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bookReturnDTO)))
            .andExpect(status().isCreated());

        // Validate the BookReturn in the database
        List<BookReturn> bookReturnList = bookReturnRepository.findAll();
        assertThat(bookReturnList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteBookReturn() throws Exception {
        // Initialize the database
        bookReturnRepository.saveAndFlush(bookReturn);
        int databaseSizeBeforeDelete = bookReturnRepository.findAll().size();

        // Get the bookReturn
        restBookReturnMockMvc.perform(delete("/api/book-returns/{id}", bookReturn.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<BookReturn> bookReturnList = bookReturnRepository.findAll();
        assertThat(bookReturnList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BookReturn.class);
        BookReturn bookReturn1 = new BookReturn();
        bookReturn1.setId(1L);
        BookReturn bookReturn2 = new BookReturn();
        bookReturn2.setId(bookReturn1.getId());
        assertThat(bookReturn1).isEqualTo(bookReturn2);
        bookReturn2.setId(2L);
        assertThat(bookReturn1).isNotEqualTo(bookReturn2);
        bookReturn1.setId(null);
        assertThat(bookReturn1).isNotEqualTo(bookReturn2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(BookReturnDTO.class);
        BookReturnDTO bookReturnDTO1 = new BookReturnDTO();
        bookReturnDTO1.setId(1L);
        BookReturnDTO bookReturnDTO2 = new BookReturnDTO();
        assertThat(bookReturnDTO1).isNotEqualTo(bookReturnDTO2);
        bookReturnDTO2.setId(bookReturnDTO1.getId());
        assertThat(bookReturnDTO1).isEqualTo(bookReturnDTO2);
        bookReturnDTO2.setId(2L);
        assertThat(bookReturnDTO1).isNotEqualTo(bookReturnDTO2);
        bookReturnDTO1.setId(null);
        assertThat(bookReturnDTO1).isNotEqualTo(bookReturnDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(bookReturnMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(bookReturnMapper.fromId(null)).isNull();
    }
}
