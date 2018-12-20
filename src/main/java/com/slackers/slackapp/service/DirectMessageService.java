package com.slackers.slackapp.service;

import com.slackers.slackapp.domain.DirectMessage;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing DirectMessage.
 */
public interface DirectMessageService {

    /**
     * Save a directMessage.
     *
     * @param directMessage the entity to save
     * @return the persisted entity
     */
    DirectMessage save(DirectMessage directMessage);

    /**
     * Get all the directMessages.
     *
     * @return the list of entities
     */
    List<DirectMessage> findAll();


    /**
     * Get the "id" directMessage.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<DirectMessage> findOne(Long id);

    /**
     * Delete the "id" directMessage.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
