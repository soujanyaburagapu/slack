package com.slackers.slackapp.service.impl;

import com.slackers.slackapp.service.WorkspaceService;
import com.slackers.slackapp.domain.Workspace;
import com.slackers.slackapp.repository.WorkspaceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing Workspace.
 */
@Service
@Transactional
public class WorkspaceServiceImpl implements WorkspaceService {

    private final Logger log = LoggerFactory.getLogger(WorkspaceServiceImpl.class);

    private final WorkspaceRepository workspaceRepository;

    public WorkspaceServiceImpl(WorkspaceRepository workspaceRepository) {
        this.workspaceRepository = workspaceRepository;
    }

    /**
     * Save a workspace.
     *
     * @param workspace the entity to save
     * @return the persisted entity
     */
    @Override
    public Workspace save(Workspace workspace) {
        log.debug("Request to save Workspace : {}", workspace);
        return workspaceRepository.save(workspace);
    }

    /**
     * Get all the workspaces.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<Workspace> findAll() {
        log.debug("Request to get all Workspaces");
        return workspaceRepository.findAll();
    }


    /**
     * Get one workspace by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Workspace> findOne(Long id) {
        log.debug("Request to get Workspace : {}", id);
        return workspaceRepository.findById(id);
    }

    /**
     * Delete the workspace by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Workspace : {}", id);
        workspaceRepository.deleteById(id);
    }
}
