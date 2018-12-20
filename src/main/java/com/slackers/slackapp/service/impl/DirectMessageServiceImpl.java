package com.slackers.slackapp.service.impl;

import com.slackers.slackapp.service.DirectMessageService;
import com.slackers.slackapp.domain.DirectMessage;
import com.slackers.slackapp.repository.DirectMessageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing DirectMessage.
 */
@Service
@Transactional
public class DirectMessageServiceImpl implements DirectMessageService {

    private final Logger log = LoggerFactory.getLogger(DirectMessageServiceImpl.class);

    private final DirectMessageRepository directMessageRepository;

    public DirectMessageServiceImpl(DirectMessageRepository directMessageRepository) {
        this.directMessageRepository = directMessageRepository;
    }

    /**
     * Save a directMessage.
     *
     * @param directMessage the entity to save
     * @return the persisted entity
     */
    @Override
    public DirectMessage save(DirectMessage directMessage) {
        log.debug("Request to save DirectMessage : {}", directMessage);
        return directMessageRepository.save(directMessage);
    }

    /**
     * Get all the directMessages.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<DirectMessage> findAll() {
        log.debug("Request to get all DirectMessages");
        return directMessageRepository.findAll();
    }


    /**
     * Get one directMessage by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<DirectMessage> findOne(Long id) {
        log.debug("Request to get DirectMessage : {}", id);
        return directMessageRepository.findById(id);
    }

    /**
     * Delete the directMessage by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete DirectMessage : {}", id);
        directMessageRepository.deleteById(id);
    }
}
