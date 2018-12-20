package com.slackers.slackapp.service.impl;

import com.slackers.slackapp.service.ChannelService;
import com.slackers.slackapp.domain.Channel;
import com.slackers.slackapp.repository.ChannelRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing Channel.
 */
@Service
@Transactional
public class ChannelServiceImpl implements ChannelService {

    private final Logger log = LoggerFactory.getLogger(ChannelServiceImpl.class);

    private final ChannelRepository channelRepository;

    public ChannelServiceImpl(ChannelRepository channelRepository) {
        this.channelRepository = channelRepository;
    }

    /**
     * Save a channel.
     *
     * @param channel the entity to save
     * @return the persisted entity
     */
    @Override
    public Channel save(Channel channel) {
        log.debug("Request to save Channel : {}", channel);
        return channelRepository.save(channel);
    }

    /**
     * Get all the channels.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<Channel> findAll() {
        log.debug("Request to get all Channels");
        return channelRepository.findAllWithEagerRelationships();
    }

    /**
     * Get all the Channel with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    public Page<Channel> findAllWithEagerRelationships(Pageable pageable) {
        return channelRepository.findAllWithEagerRelationships(pageable);
    }
    

    /**
     * Get one channel by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Channel> findOne(Long id) {
        log.debug("Request to get Channel : {}", id);
        return channelRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the channel by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Channel : {}", id);
        channelRepository.deleteById(id);
    }
}
