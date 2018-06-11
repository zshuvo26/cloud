package cloud.web.rest;

import cloud.domain.Department;
import cloud.domain.Institute;
import cloud.repository.DepartmentRepository;
import cloud.repository.InstituteRepository;
import com.codahale.metrics.annotation.Timed;
import cloud.service.DepartmentService;
import cloud.web.rest.errors.BadRequestAlertException;
import cloud.web.rest.util.HeaderUtil;
import cloud.web.rest.util.PaginationUtil;
import cloud.service.dto.DepartmentDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;



/**
 * REST controller for managing Department.
 */
@RestController
@RequestMapping("/api")
public class DepartmentResource {

    private final Logger log = LoggerFactory.getLogger(DepartmentResource.class);

    private static final String ENTITY_NAME = "department";

    private final DepartmentService departmentService;
    private final InstituteRepository instituteRepository;
    private final DepartmentRepository departmentRepository;


    public DepartmentResource(DepartmentService departmentService,DepartmentRepository departmentRepository, InstituteRepository instituteRepository) {
        this.departmentService = departmentService;
        this.instituteRepository = instituteRepository;
        this.departmentRepository = departmentRepository;
    }

    /**
     * POST  /departments : Create a new department.
     *
     * @param departmentDTO the departmentDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new departmentDTO, or with status 400 (Bad Request) if the department has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/departments")
    @Timed
    public ResponseEntity<DepartmentDTO> createDepartment(@Valid @RequestBody DepartmentDTO departmentDTO) throws URISyntaxException {
        log.debug("REST request to save Department : {}", departmentDTO);
        if (departmentDTO.getId() != null) {
            throw new BadRequestAlertException("A new department cannot already have an ID", ENTITY_NAME, "idexists");
        }
        //Institute institute = new Institute();

        Institute institute=instituteRepository.findOneByUserIsCurrentUser();
        departmentDTO.setInstituteId(institute.getId());
        DepartmentDTO result = departmentService.save(departmentDTO);
        return ResponseEntity.created(new URI("/api/departments/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /departments : Updates an existing department.
     *
     * @param departmentDTO the departmentDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated departmentDTO,
     * or with status 400 (Bad Request) if the departmentDTO is not valid,
     * or with status 500 (Internal Server Error) if the departmentDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/departments")
    @Timed
    public ResponseEntity<DepartmentDTO> updateDepartment(@Valid @RequestBody DepartmentDTO departmentDTO) throws URISyntaxException {
        log.debug("REST request to update Department : {}", departmentDTO);
        if (departmentDTO.getId() == null) {
            return createDepartment(departmentDTO);
        }
        DepartmentDTO result = departmentService.save(departmentDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, departmentDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /departments : get all the departments.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of departments in body
     */
    @GetMapping("/departments")
    @Timed
    public ResponseEntity<List<DepartmentDTO>> getAllDepartments(Pageable pageable) {
        log.debug("REST request to get a page of Departments");
        Page<DepartmentDTO> page = departmentService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/departments");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /departments : get institute wise the departments.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of departments in body
     */
    @GetMapping("/departments/institutewisedepartments")
    @Timed
    public ResponseEntity<List<Department>> getAllDepartmentsByInstitute(Pageable pageable) {
        log.debug("REST request to get a page of Departments");
        Institute institute=instituteRepository.findOneByUserIsCurrentUser();


        Page<Department> page = departmentRepository.findDepartmentsByInstitute(pageable,institute.getId());
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/departments");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /departments/:id : get the "id" department.
     *
     * @param id the id of the departmentDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the departmentDTO, or with status 404 (Not Found)
     */
    @GetMapping("/departments/{id}")
    @Timed
    public ResponseEntity<DepartmentDTO> getDepartment(@PathVariable Long id) {
        log.debug("REST request to get Department : {}", id);
        DepartmentDTO departmentDTO = departmentService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(departmentDTO));
    }

    /**
     * DELETE  /departments/:id : delete the "id" department.
     *
     * @param id the id of the departmentDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/departments/{id}")
    @Timed
    public ResponseEntity<Void> deleteDepartment(@PathVariable Long id) {
        log.debug("REST request to delete Department : {}", id);
        departmentService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
