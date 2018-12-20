package com.slackers.slackapp.web.rest;

import com.slackers.slackapp.SlackJhipApp;

import com.slackers.slackapp.domain.Channel;
import com.slackers.slackapp.repository.ChannelRepository;
import com.slackers.slackapp.service.ChannelService;
import com.slackers.slackapp.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;


import static com.slackers.slackapp.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ChannelResource REST controller.
 *
 * @see ChannelResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SlackJhipApp.class)
public class ChannelResourceIntTest {

    private static final String DEFAULT_CHANNEL_NAME = "AAAAAAAAAA";
    private static final String UPDATED_CHANNEL_NAME = "BBBBBBBBBB";

    private static final Long DEFAULT_CHANNEL_ID = 1L;
    private static final Long UPDATED_CHANNEL_ID = 2L;

    private static final String DEFAULT_CHANNEL_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_CHANNEL_DESCRIPTION = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_PRIVATE = false;
    private static final Boolean UPDATED_IS_PRIVATE = true;

    @Autowired
    private ChannelRepository channelRepository;

    @Mock
    private ChannelRepository channelRepositoryMock;

    @Mock
    private ChannelService channelServiceMock;

    @Autowired
    private ChannelService channelService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restChannelMockMvc;

    private Channel channel;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ChannelResource channelResource = new ChannelResource(channelService);
        this.restChannelMockMvc = MockMvcBuilders.standaloneSetup(channelResource)
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
    public static Channel createEntity(EntityManager em) {
        Channel channel = new Channel()
            .channelName(DEFAULT_CHANNEL_NAME)
            .channelID(DEFAULT_CHANNEL_ID)
            .channelDescription(DEFAULT_CHANNEL_DESCRIPTION)
            .isPrivate(DEFAULT_IS_PRIVATE);
        return channel;
    }

    @Before
    public void initTest() {
        channel = createEntity(em);
    }

    @Test
    @Transactional
    public void createChannel() throws Exception {
        int databaseSizeBeforeCreate = channelRepository.findAll().size();

        // Create the Channel
        restChannelMockMvc.perform(post("/api/channels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(channel)))
            .andExpect(status().isCreated());

        // Validate the Channel in the database
        List<Channel> channelList = channelRepository.findAll();
        assertThat(channelList).hasSize(databaseSizeBeforeCreate + 1);
        Channel testChannel = channelList.get(channelList.size() - 1);
        assertThat(testChannel.getChannelName()).isEqualTo(DEFAULT_CHANNEL_NAME);
        assertThat(testChannel.getChannelID()).isEqualTo(DEFAULT_CHANNEL_ID);
        assertThat(testChannel.getChannelDescription()).isEqualTo(DEFAULT_CHANNEL_DESCRIPTION);
        assertThat(testChannel.isIsPrivate()).isEqualTo(DEFAULT_IS_PRIVATE);
    }

    @Test
    @Transactional
    public void createChannelWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = channelRepository.findAll().size();

        // Create the Channel with an existing ID
        channel.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restChannelMockMvc.perform(post("/api/channels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(channel)))
            .andExpect(status().isBadRequest());

        // Validate the Channel in the database
        List<Channel> channelList = channelRepository.findAll();
        assertThat(channelList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllChannels() throws Exception {
        // Initialize the database
        channelRepository.saveAndFlush(channel);

        // Get all the channelList
        restChannelMockMvc.perform(get("/api/channels?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(channel.getId().intValue())))
            .andExpect(jsonPath("$.[*].channelName").value(hasItem(DEFAULT_CHANNEL_NAME.toString())))
            .andExpect(jsonPath("$.[*].channelID").value(hasItem(DEFAULT_CHANNEL_ID.intValue())))
            .andExpect(jsonPath("$.[*].channelDescription").value(hasItem(DEFAULT_CHANNEL_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].isPrivate").value(hasItem(DEFAULT_IS_PRIVATE.booleanValue())));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllChannelsWithEagerRelationshipsIsEnabled() throws Exception {
        ChannelResource channelResource = new ChannelResource(channelServiceMock);
        when(channelServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restChannelMockMvc = MockMvcBuilders.standaloneSetup(channelResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restChannelMockMvc.perform(get("/api/channels?eagerload=true"))
        .andExpect(status().isOk());

        verify(channelServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllChannelsWithEagerRelationshipsIsNotEnabled() throws Exception {
        ChannelResource channelResource = new ChannelResource(channelServiceMock);
            when(channelServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restChannelMockMvc = MockMvcBuilders.standaloneSetup(channelResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restChannelMockMvc.perform(get("/api/channels?eagerload=true"))
        .andExpect(status().isOk());

            verify(channelServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getChannel() throws Exception {
        // Initialize the database
        channelRepository.saveAndFlush(channel);

        // Get the channel
        restChannelMockMvc.perform(get("/api/channels/{id}", channel.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(channel.getId().intValue()))
            .andExpect(jsonPath("$.channelName").value(DEFAULT_CHANNEL_NAME.toString()))
            .andExpect(jsonPath("$.channelID").value(DEFAULT_CHANNEL_ID.intValue()))
            .andExpect(jsonPath("$.channelDescription").value(DEFAULT_CHANNEL_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.isPrivate").value(DEFAULT_IS_PRIVATE.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingChannel() throws Exception {
        // Get the channel
        restChannelMockMvc.perform(get("/api/channels/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateChannel() throws Exception {
        // Initialize the database
        channelService.save(channel);

        int databaseSizeBeforeUpdate = channelRepository.findAll().size();

        // Update the channel
        Channel updatedChannel = channelRepository.findById(channel.getId()).get();
        // Disconnect from session so that the updates on updatedChannel are not directly saved in db
        em.detach(updatedChannel);
        updatedChannel
            .channelName(UPDATED_CHANNEL_NAME)
            .channelID(UPDATED_CHANNEL_ID)
            .channelDescription(UPDATED_CHANNEL_DESCRIPTION)
            .isPrivate(UPDATED_IS_PRIVATE);

        restChannelMockMvc.perform(put("/api/channels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedChannel)))
            .andExpect(status().isOk());

        // Validate the Channel in the database
        List<Channel> channelList = channelRepository.findAll();
        assertThat(channelList).hasSize(databaseSizeBeforeUpdate);
        Channel testChannel = channelList.get(channelList.size() - 1);
        assertThat(testChannel.getChannelName()).isEqualTo(UPDATED_CHANNEL_NAME);
        assertThat(testChannel.getChannelID()).isEqualTo(UPDATED_CHANNEL_ID);
        assertThat(testChannel.getChannelDescription()).isEqualTo(UPDATED_CHANNEL_DESCRIPTION);
        assertThat(testChannel.isIsPrivate()).isEqualTo(UPDATED_IS_PRIVATE);
    }

    @Test
    @Transactional
    public void updateNonExistingChannel() throws Exception {
        int databaseSizeBeforeUpdate = channelRepository.findAll().size();

        // Create the Channel

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restChannelMockMvc.perform(put("/api/channels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(channel)))
            .andExpect(status().isBadRequest());

        // Validate the Channel in the database
        List<Channel> channelList = channelRepository.findAll();
        assertThat(channelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteChannel() throws Exception {
        // Initialize the database
        channelService.save(channel);

        int databaseSizeBeforeDelete = channelRepository.findAll().size();

        // Get the channel
        restChannelMockMvc.perform(delete("/api/channels/{id}", channel.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Channel> channelList = channelRepository.findAll();
        assertThat(channelList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Channel.class);
        Channel channel1 = new Channel();
        channel1.setId(1L);
        Channel channel2 = new Channel();
        channel2.setId(channel1.getId());
        assertThat(channel1).isEqualTo(channel2);
        channel2.setId(2L);
        assertThat(channel1).isNotEqualTo(channel2);
        channel1.setId(null);
        assertThat(channel1).isNotEqualTo(channel2);
    }
}
