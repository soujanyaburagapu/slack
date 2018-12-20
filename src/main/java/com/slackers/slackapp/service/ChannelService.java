package com.slackers.slackapp.service;

import com.slackers.slackapp.domain.Channel;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Channel.
 */
public interface ChannelService {

    /**
     * Save a channel.
     *
     * @param channel the entity to save
     * @return the persisted entity
     */
    Channel save(Channel channel);

    /**
     * Get all the channels.
     *
     * @return the list of entities
     */
    List<Channel> findAll();

    /**
     * Get all the Channel with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    Page<Channel> findAllWithEagerRelationships(Pageable pageable);
    
    /**
     * Get the "id" channel.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<Channel> findOne(Long id);

    /**
     * Delete the "id" channel.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
