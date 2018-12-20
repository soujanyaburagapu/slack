package com.slackers.slackapp.service;

import com.slackers.slackapp.domain.SlackApp;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing SlackApp.
 */
public interface SlackAppService {

    /**
     * Save a slackApp.
     *
     * @param slackApp the entity to save
     * @return the persisted entity
     */
    SlackApp save(SlackApp slackApp);

    /**
     * Get all the slackApps.
     *
     * @return the list of entities
     */
    List<SlackApp> findAll();


    /**
     * Get the "id" slackApp.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<SlackApp> findOne(Long id);

    /**
     * Delete the "id" slackApp.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
