package com.slackers.slackapp.service;

import com.slackers.slackapp.domain.Workspace;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Workspace.
 */
public interface WorkspaceService {

    /**
     * Save a workspace.
     *
     * @param workspace the entity to save
     * @return the persisted entity
     */
    Workspace save(Workspace workspace);

    /**
     * Get all the workspaces.
     *
     * @return the list of entities
     */
    List<Workspace> findAll();


    /**
     * Get the "id" workspace.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<Workspace> findOne(Long id);

    /**
     * Delete the "id" workspace.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
