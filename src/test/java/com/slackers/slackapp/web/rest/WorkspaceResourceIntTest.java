package com.slackers.slackapp.web.rest;

import com.slackers.slackapp.SlackJhipApp;

import com.slackers.slackapp.domain.Workspace;
import com.slackers.slackapp.repository.WorkspaceRepository;
import com.slackers.slackapp.service.WorkspaceService;
import com.slackers.slackapp.web.rest.errors.ExceptionTranslator;

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


import static com.slackers.slackapp.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the WorkspaceResource REST controller.
 *
 * @see WorkspaceResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SlackJhipApp.class)
public class WorkspaceResourceIntTest {

    private static final String DEFAULT_WORKSPACE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_WORKSPACE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ADMIN = "AAAAAAAAAA";
    private static final String UPDATED_ADMIN = "BBBBBBBBBB";

    private static final Long DEFAULT_WORKSPACE_ID = 1L;
    private static final Long UPDATED_WORKSPACE_ID = 2L;

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private WorkspaceRepository workspaceRepository;

    @Autowired
    private WorkspaceService workspaceService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restWorkspaceMockMvc;

    private Workspace workspace;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final WorkspaceResource workspaceResource = new WorkspaceResource(workspaceService);
        this.restWorkspaceMockMvc = MockMvcBuilders.standaloneSetup(workspaceResource)
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
    public static Workspace createEntity(EntityManager em) {
        Workspace workspace = new Workspace()
            .workspaceName(DEFAULT_WORKSPACE_NAME)
            .admin(DEFAULT_ADMIN)
            .workspaceID(DEFAULT_WORKSPACE_ID)
            .description(DEFAULT_DESCRIPTION);
        return workspace;
    }

    @Before
    public void initTest() {
        workspace = createEntity(em);
    }

    @Test
    @Transactional
    public void createWorkspace() throws Exception {
        int databaseSizeBeforeCreate = workspaceRepository.findAll().size();

        // Create the Workspace
        restWorkspaceMockMvc.perform(post("/api/workspaces")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(workspace)))
            .andExpect(status().isCreated());

        // Validate the Workspace in the database
        List<Workspace> workspaceList = workspaceRepository.findAll();
        assertThat(workspaceList).hasSize(databaseSizeBeforeCreate + 1);
        Workspace testWorkspace = workspaceList.get(workspaceList.size() - 1);
        assertThat(testWorkspace.getWorkspaceName()).isEqualTo(DEFAULT_WORKSPACE_NAME);
        assertThat(testWorkspace.getAdmin()).isEqualTo(DEFAULT_ADMIN);
        assertThat(testWorkspace.getWorkspaceID()).isEqualTo(DEFAULT_WORKSPACE_ID);
        assertThat(testWorkspace.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createWorkspaceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = workspaceRepository.findAll().size();

        // Create the Workspace with an existing ID
        workspace.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restWorkspaceMockMvc.perform(post("/api/workspaces")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(workspace)))
            .andExpect(status().isBadRequest());

        // Validate the Workspace in the database
        List<Workspace> workspaceList = workspaceRepository.findAll();
        assertThat(workspaceList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllWorkspaces() throws Exception {
        // Initialize the database
        workspaceRepository.saveAndFlush(workspace);

        // Get all the workspaceList
        restWorkspaceMockMvc.perform(get("/api/workspaces?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(workspace.getId().intValue())))
            .andExpect(jsonPath("$.[*].workspaceName").value(hasItem(DEFAULT_WORKSPACE_NAME.toString())))
            .andExpect(jsonPath("$.[*].admin").value(hasItem(DEFAULT_ADMIN.toString())))
            .andExpect(jsonPath("$.[*].workspaceID").value(hasItem(DEFAULT_WORKSPACE_ID.intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }
    
    @Test
    @Transactional
    public void getWorkspace() throws Exception {
        // Initialize the database
        workspaceRepository.saveAndFlush(workspace);

        // Get the workspace
        restWorkspaceMockMvc.perform(get("/api/workspaces/{id}", workspace.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(workspace.getId().intValue()))
            .andExpect(jsonPath("$.workspaceName").value(DEFAULT_WORKSPACE_NAME.toString()))
            .andExpect(jsonPath("$.admin").value(DEFAULT_ADMIN.toString()))
            .andExpect(jsonPath("$.workspaceID").value(DEFAULT_WORKSPACE_ID.intValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingWorkspace() throws Exception {
        // Get the workspace
        restWorkspaceMockMvc.perform(get("/api/workspaces/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateWorkspace() throws Exception {
        // Initialize the database
        workspaceService.save(workspace);

        int databaseSizeBeforeUpdate = workspaceRepository.findAll().size();

        // Update the workspace
        Workspace updatedWorkspace = workspaceRepository.findById(workspace.getId()).get();
        // Disconnect from session so that the updates on updatedWorkspace are not directly saved in db
        em.detach(updatedWorkspace);
        updatedWorkspace
            .workspaceName(UPDATED_WORKSPACE_NAME)
            .admin(UPDATED_ADMIN)
            .workspaceID(UPDATED_WORKSPACE_ID)
            .description(UPDATED_DESCRIPTION);

        restWorkspaceMockMvc.perform(put("/api/workspaces")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedWorkspace)))
            .andExpect(status().isOk());

        // Validate the Workspace in the database
        List<Workspace> workspaceList = workspaceRepository.findAll();
        assertThat(workspaceList).hasSize(databaseSizeBeforeUpdate);
        Workspace testWorkspace = workspaceList.get(workspaceList.size() - 1);
        assertThat(testWorkspace.getWorkspaceName()).isEqualTo(UPDATED_WORKSPACE_NAME);
        assertThat(testWorkspace.getAdmin()).isEqualTo(UPDATED_ADMIN);
        assertThat(testWorkspace.getWorkspaceID()).isEqualTo(UPDATED_WORKSPACE_ID);
        assertThat(testWorkspace.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingWorkspace() throws Exception {
        int databaseSizeBeforeUpdate = workspaceRepository.findAll().size();

        // Create the Workspace

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWorkspaceMockMvc.perform(put("/api/workspaces")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(workspace)))
            .andExpect(status().isBadRequest());

        // Validate the Workspace in the database
        List<Workspace> workspaceList = workspaceRepository.findAll();
        assertThat(workspaceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteWorkspace() throws Exception {
        // Initialize the database
        workspaceService.save(workspace);

        int databaseSizeBeforeDelete = workspaceRepository.findAll().size();

        // Get the workspace
        restWorkspaceMockMvc.perform(delete("/api/workspaces/{id}", workspace.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Workspace> workspaceList = workspaceRepository.findAll();
        assertThat(workspaceList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Workspace.class);
        Workspace workspace1 = new Workspace();
        workspace1.setId(1L);
        Workspace workspace2 = new Workspace();
        workspace2.setId(workspace1.getId());
        assertThat(workspace1).isEqualTo(workspace2);
        workspace2.setId(2L);
        assertThat(workspace1).isNotEqualTo(workspace2);
        workspace1.setId(null);
        assertThat(workspace1).isNotEqualTo(workspace2);
    }
}
