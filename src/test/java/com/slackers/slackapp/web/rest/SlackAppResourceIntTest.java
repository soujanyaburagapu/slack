package com.slackers.slackapp.web.rest;

import com.slackers.slackapp.SlackJhipApp;

import com.slackers.slackapp.domain.SlackApp;
import com.slackers.slackapp.repository.SlackAppRepository;
import com.slackers.slackapp.service.SlackAppService;
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
 * Test class for the SlackAppResource REST controller.
 *
 * @see SlackAppResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SlackJhipApp.class)
public class SlackAppResourceIntTest {

    @Autowired
    private SlackAppRepository slackAppRepository;

    @Autowired
    private SlackAppService slackAppService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restSlackAppMockMvc;

    private SlackApp slackApp;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SlackAppResource slackAppResource = new SlackAppResource(slackAppService);
        this.restSlackAppMockMvc = MockMvcBuilders.standaloneSetup(slackAppResource)
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
    public static SlackApp createEntity(EntityManager em) {
        SlackApp slackApp = new SlackApp();
        return slackApp;
    }

    @Before
    public void initTest() {
        slackApp = createEntity(em);
    }

    @Test
    @Transactional
    public void createSlackApp() throws Exception {
        int databaseSizeBeforeCreate = slackAppRepository.findAll().size();

        // Create the SlackApp
        restSlackAppMockMvc.perform(post("/api/slack-apps")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(slackApp)))
            .andExpect(status().isCreated());

        // Validate the SlackApp in the database
        List<SlackApp> slackAppList = slackAppRepository.findAll();
        assertThat(slackAppList).hasSize(databaseSizeBeforeCreate + 1);
        SlackApp testSlackApp = slackAppList.get(slackAppList.size() - 1);
    }

    @Test
    @Transactional
    public void createSlackAppWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = slackAppRepository.findAll().size();

        // Create the SlackApp with an existing ID
        slackApp.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSlackAppMockMvc.perform(post("/api/slack-apps")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(slackApp)))
            .andExpect(status().isBadRequest());

        // Validate the SlackApp in the database
        List<SlackApp> slackAppList = slackAppRepository.findAll();
        assertThat(slackAppList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllSlackApps() throws Exception {
        // Initialize the database
        slackAppRepository.saveAndFlush(slackApp);

        // Get all the slackAppList
        restSlackAppMockMvc.perform(get("/api/slack-apps?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(slackApp.getId().intValue())));
    }
    
    @Test
    @Transactional
    public void getSlackApp() throws Exception {
        // Initialize the database
        slackAppRepository.saveAndFlush(slackApp);

        // Get the slackApp
        restSlackAppMockMvc.perform(get("/api/slack-apps/{id}", slackApp.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(slackApp.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingSlackApp() throws Exception {
        // Get the slackApp
        restSlackAppMockMvc.perform(get("/api/slack-apps/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSlackApp() throws Exception {
        // Initialize the database
        slackAppService.save(slackApp);

        int databaseSizeBeforeUpdate = slackAppRepository.findAll().size();

        // Update the slackApp
        SlackApp updatedSlackApp = slackAppRepository.findById(slackApp.getId()).get();
        // Disconnect from session so that the updates on updatedSlackApp are not directly saved in db
        em.detach(updatedSlackApp);

        restSlackAppMockMvc.perform(put("/api/slack-apps")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedSlackApp)))
            .andExpect(status().isOk());

        // Validate the SlackApp in the database
        List<SlackApp> slackAppList = slackAppRepository.findAll();
        assertThat(slackAppList).hasSize(databaseSizeBeforeUpdate);
        SlackApp testSlackApp = slackAppList.get(slackAppList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingSlackApp() throws Exception {
        int databaseSizeBeforeUpdate = slackAppRepository.findAll().size();

        // Create the SlackApp

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSlackAppMockMvc.perform(put("/api/slack-apps")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(slackApp)))
            .andExpect(status().isBadRequest());

        // Validate the SlackApp in the database
        List<SlackApp> slackAppList = slackAppRepository.findAll();
        assertThat(slackAppList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSlackApp() throws Exception {
        // Initialize the database
        slackAppService.save(slackApp);

        int databaseSizeBeforeDelete = slackAppRepository.findAll().size();

        // Get the slackApp
        restSlackAppMockMvc.perform(delete("/api/slack-apps/{id}", slackApp.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<SlackApp> slackAppList = slackAppRepository.findAll();
        assertThat(slackAppList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SlackApp.class);
        SlackApp slackApp1 = new SlackApp();
        slackApp1.setId(1L);
        SlackApp slackApp2 = new SlackApp();
        slackApp2.setId(slackApp1.getId());
        assertThat(slackApp1).isEqualTo(slackApp2);
        slackApp2.setId(2L);
        assertThat(slackApp1).isNotEqualTo(slackApp2);
        slackApp1.setId(null);
        assertThat(slackApp1).isNotEqualTo(slackApp2);
    }
}
