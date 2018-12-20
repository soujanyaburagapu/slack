package com.slackers.slackapp.web.rest;

import com.slackers.slackapp.SlackJhipApp;

import com.slackers.slackapp.domain.DirectMessage;
import com.slackers.slackapp.repository.DirectMessageRepository;
import com.slackers.slackapp.service.DirectMessageService;
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
 * Test class for the DirectMessageResource REST controller.
 *
 * @see DirectMessageResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SlackJhipApp.class)
public class DirectMessageResourceIntTest {

    private static final Long DEFAULT_DM_ID = 1L;
    private static final Long UPDATED_DM_ID = 2L;

    private static final String DEFAULT_MESSAGE = "AAAAAAAAAA";
    private static final String UPDATED_MESSAGE = "BBBBBBBBBB";

    @Autowired
    private DirectMessageRepository directMessageRepository;

    @Autowired
    private DirectMessageService directMessageService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restDirectMessageMockMvc;

    private DirectMessage directMessage;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DirectMessageResource directMessageResource = new DirectMessageResource(directMessageService);
        this.restDirectMessageMockMvc = MockMvcBuilders.standaloneSetup(directMessageResource)
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
    public static DirectMessage createEntity(EntityManager em) {
        DirectMessage directMessage = new DirectMessage()
            .dmId(DEFAULT_DM_ID)
            .message(DEFAULT_MESSAGE);
        return directMessage;
    }

    @Before
    public void initTest() {
        directMessage = createEntity(em);
    }

    @Test
    @Transactional
    public void createDirectMessage() throws Exception {
        int databaseSizeBeforeCreate = directMessageRepository.findAll().size();

        // Create the DirectMessage
        restDirectMessageMockMvc.perform(post("/api/direct-messages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(directMessage)))
            .andExpect(status().isCreated());

        // Validate the DirectMessage in the database
        List<DirectMessage> directMessageList = directMessageRepository.findAll();
        assertThat(directMessageList).hasSize(databaseSizeBeforeCreate + 1);
        DirectMessage testDirectMessage = directMessageList.get(directMessageList.size() - 1);
        assertThat(testDirectMessage.getDmId()).isEqualTo(DEFAULT_DM_ID);
        assertThat(testDirectMessage.getMessage()).isEqualTo(DEFAULT_MESSAGE);
    }

    @Test
    @Transactional
    public void createDirectMessageWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = directMessageRepository.findAll().size();

        // Create the DirectMessage with an existing ID
        directMessage.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDirectMessageMockMvc.perform(post("/api/direct-messages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(directMessage)))
            .andExpect(status().isBadRequest());

        // Validate the DirectMessage in the database
        List<DirectMessage> directMessageList = directMessageRepository.findAll();
        assertThat(directMessageList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllDirectMessages() throws Exception {
        // Initialize the database
        directMessageRepository.saveAndFlush(directMessage);

        // Get all the directMessageList
        restDirectMessageMockMvc.perform(get("/api/direct-messages?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(directMessage.getId().intValue())))
            .andExpect(jsonPath("$.[*].dmId").value(hasItem(DEFAULT_DM_ID.intValue())))
            .andExpect(jsonPath("$.[*].message").value(hasItem(DEFAULT_MESSAGE.toString())));
    }
    
    @Test
    @Transactional
    public void getDirectMessage() throws Exception {
        // Initialize the database
        directMessageRepository.saveAndFlush(directMessage);

        // Get the directMessage
        restDirectMessageMockMvc.perform(get("/api/direct-messages/{id}", directMessage.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(directMessage.getId().intValue()))
            .andExpect(jsonPath("$.dmId").value(DEFAULT_DM_ID.intValue()))
            .andExpect(jsonPath("$.message").value(DEFAULT_MESSAGE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingDirectMessage() throws Exception {
        // Get the directMessage
        restDirectMessageMockMvc.perform(get("/api/direct-messages/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDirectMessage() throws Exception {
        // Initialize the database
        directMessageService.save(directMessage);

        int databaseSizeBeforeUpdate = directMessageRepository.findAll().size();

        // Update the directMessage
        DirectMessage updatedDirectMessage = directMessageRepository.findById(directMessage.getId()).get();
        // Disconnect from session so that the updates on updatedDirectMessage are not directly saved in db
        em.detach(updatedDirectMessage);
        updatedDirectMessage
            .dmId(UPDATED_DM_ID)
            .message(UPDATED_MESSAGE);

        restDirectMessageMockMvc.perform(put("/api/direct-messages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedDirectMessage)))
            .andExpect(status().isOk());

        // Validate the DirectMessage in the database
        List<DirectMessage> directMessageList = directMessageRepository.findAll();
        assertThat(directMessageList).hasSize(databaseSizeBeforeUpdate);
        DirectMessage testDirectMessage = directMessageList.get(directMessageList.size() - 1);
        assertThat(testDirectMessage.getDmId()).isEqualTo(UPDATED_DM_ID);
        assertThat(testDirectMessage.getMessage()).isEqualTo(UPDATED_MESSAGE);
    }

    @Test
    @Transactional
    public void updateNonExistingDirectMessage() throws Exception {
        int databaseSizeBeforeUpdate = directMessageRepository.findAll().size();

        // Create the DirectMessage

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDirectMessageMockMvc.perform(put("/api/direct-messages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(directMessage)))
            .andExpect(status().isBadRequest());

        // Validate the DirectMessage in the database
        List<DirectMessage> directMessageList = directMessageRepository.findAll();
        assertThat(directMessageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDirectMessage() throws Exception {
        // Initialize the database
        directMessageService.save(directMessage);

        int databaseSizeBeforeDelete = directMessageRepository.findAll().size();

        // Get the directMessage
        restDirectMessageMockMvc.perform(delete("/api/direct-messages/{id}", directMessage.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<DirectMessage> directMessageList = directMessageRepository.findAll();
        assertThat(directMessageList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DirectMessage.class);
        DirectMessage directMessage1 = new DirectMessage();
        directMessage1.setId(1L);
        DirectMessage directMessage2 = new DirectMessage();
        directMessage2.setId(directMessage1.getId());
        assertThat(directMessage1).isEqualTo(directMessage2);
        directMessage2.setId(2L);
        assertThat(directMessage1).isNotEqualTo(directMessage2);
        directMessage1.setId(null);
        assertThat(directMessage1).isNotEqualTo(directMessage2);
    }
}
