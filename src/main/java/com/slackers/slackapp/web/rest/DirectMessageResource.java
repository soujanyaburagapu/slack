package com.slackers.slackapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.slackers.slackapp.domain.DirectMessage;
import com.slackers.slackapp.service.DirectMessageService;
import com.slackers.slackapp.web.rest.errors.BadRequestAlertException;
import com.slackers.slackapp.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing DirectMessage.
 */
@RestController
@RequestMapping("/api")
public class DirectMessageResource {

    private final Logger log = LoggerFactory.getLogger(DirectMessageResource.class);

    private static final String ENTITY_NAME = "directMessage";

    private final DirectMessageService directMessageService;

    public DirectMessageResource(DirectMessageService directMessageService) {
        this.directMessageService = directMessageService;
    }

    /**
     * POST  /direct-messages : Create a new directMessage.
     *
     * @param directMessage the directMessage to create
     * @return the ResponseEntity with status 201 (Created) and with body the new directMessage, or with status 400 (Bad Request) if the directMessage has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/direct-messages")
    @Timed
    public ResponseEntity<DirectMessage> createDirectMessage(@RequestBody DirectMessage directMessage) throws URISyntaxException {
        log.debug("REST request to save DirectMessage : {}", directMessage);
        if (directMessage.getId() != null) {
            throw new BadRequestAlertException("A new directMessage cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DirectMessage result = directMessageService.save(directMessage);
        return ResponseEntity.created(new URI("/api/direct-messages/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /direct-messages : Updates an existing directMessage.
     *
     * @param directMessage the directMessage to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated directMessage,
     * or with status 400 (Bad Request) if the directMessage is not valid,
     * or with status 500 (Internal Server Error) if the directMessage couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/direct-messages")
    @Timed
    public ResponseEntity<DirectMessage> updateDirectMessage(@RequestBody DirectMessage directMessage) throws URISyntaxException {
        log.debug("REST request to update DirectMessage : {}", directMessage);
        if (directMessage.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DirectMessage result = directMessageService.save(directMessage);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, directMessage.getId().toString()))
            .body(result);
    }

    /**
     * GET  /direct-messages : get all the directMessages.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of directMessages in body
     */
    @GetMapping("/direct-messages")
    @Timed
    public List<DirectMessage> getAllDirectMessages() {
        log.debug("REST request to get all DirectMessages");
        return directMessageService.findAll();
    }

    /**
     * GET  /direct-messages/:id : get the "id" directMessage.
     *
     * @param id the id of the directMessage to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the directMessage, or with status 404 (Not Found)
     */
    @GetMapping("/direct-messages/{id}")
    @Timed
    public ResponseEntity<DirectMessage> getDirectMessage(@PathVariable Long id) {
        log.debug("REST request to get DirectMessage : {}", id);
        Optional<DirectMessage> directMessage = directMessageService.findOne(id);
        return ResponseUtil.wrapOrNotFound(directMessage);
    }

    /**
     * DELETE  /direct-messages/:id : delete the "id" directMessage.
     *
     * @param id the id of the directMessage to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/direct-messages/{id}")
    @Timed
    public ResponseEntity<Void> deleteDirectMessage(@PathVariable Long id) {
        log.debug("REST request to delete DirectMessage : {}", id);
        directMessageService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
