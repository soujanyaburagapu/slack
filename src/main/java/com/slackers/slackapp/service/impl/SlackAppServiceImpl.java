package com.slackers.slackapp.service.impl;

import com.slackers.slackapp.service.SlackAppService;
import com.slackers.slackapp.domain.SlackApp;
import com.slackers.slackapp.repository.SlackAppRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing SlackApp.
 */
@Service
@Transactional
public class SlackAppServiceImpl implements SlackAppService {

    private final Logger log = LoggerFactory.getLogger(SlackAppServiceImpl.class);

    private final SlackAppRepository slackAppRepository;

    public SlackAppServiceImpl(SlackAppRepository slackAppRepository) {
        this.slackAppRepository = slackAppRepository;
    }

    /**
     * Save a slackApp.
     *
     * @param slackApp the entity to save
     * @return the persisted entity
     */
    @Override
    public SlackApp save(SlackApp slackApp) {
        log.debug("Request to save SlackApp : {}", slackApp);
        return slackAppRepository.save(slackApp);
    }

    /**
     * Get all the slackApps.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<SlackApp> findAll() {
        log.debug("Request to get all SlackApps");
        return slackAppRepository.findAll();
    }


    /**
     * Get one slackApp by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<SlackApp> findOne(Long id) {
        log.debug("Request to get SlackApp : {}", id);
        return slackAppRepository.findById(id);
    }

    /**
     * Delete the slackApp by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete SlackApp : {}", id);
        slackAppRepository.deleteById(id);
    }
}
