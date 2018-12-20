package com.slackers.slackapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.slackers.slackapp.domain.SlackApp;
import com.slackers.slackapp.service.SlackAppService;
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
 * REST controller for managing SlackApp.
 */
@RestController
@RequestMapping("/api")
public class SlackAppResource {

    private final Logger log = LoggerFactory.getLogger(SlackAppResource.class);

    private static final String ENTITY_NAME = "slackApp";

    private final SlackAppService slackAppService;

    public SlackAppResource(SlackAppService slackAppService) {
        this.slackAppService = slackAppService;
    }

    /**
     * POST  /slack-apps : Create a new slackApp.
     *
     * @param slackApp the slackApp to create
     * @return the ResponseEntity with status 201 (Created) and with body the new slackApp, or with status 400 (Bad Request) if the slackApp has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/slack-apps")
    @Timed
    public ResponseEntity<SlackApp> createSlackApp(@RequestBody SlackApp slackApp) throws URISyntaxException {
        log.debug("REST request to save SlackApp : {}", slackApp);
        if (slackApp.getId() != null) {
            throw new BadRequestAlertException("A new slackApp cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SlackApp result = slackAppService.save(slackApp);
        return ResponseEntity.created(new URI("/api/slack-apps/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /slack-apps : Updates an existing slackApp.
     *
     * @param slackApp the slackApp to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated slackApp,
     * or with status 400 (Bad Request) if the slackApp is not valid,
     * or with status 500 (Internal Server Error) if the slackApp couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/slack-apps")
    @Timed
    public ResponseEntity<SlackApp> updateSlackApp(@RequestBody SlackApp slackApp) throws URISyntaxException {
        log.debug("REST request to update SlackApp : {}", slackApp);
        if (slackApp.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SlackApp result = slackAppService.save(slackApp);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, slackApp.getId().toString()))
            .body(result);
    }

    /**
     * GET  /slack-apps : get all the slackApps.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of slackApps in body
     */
    @GetMapping("/slack-apps")
    @Timed
    public List<SlackApp> getAllSlackApps() {
        log.debug("REST request to get all SlackApps");
        return slackAppService.findAll();
    }

    /**
     * GET  /slack-apps/:id : get the "id" slackApp.
     *
     * @param id the id of the slackApp to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the slackApp, or with status 404 (Not Found)
     */
    @GetMapping("/slack-apps/{id}")
    @Timed
    public ResponseEntity<SlackApp> getSlackApp(@PathVariable Long id) {
        log.debug("REST request to get SlackApp : {}", id);
        Optional<SlackApp> slackApp = slackAppService.findOne(id);
        return ResponseUtil.wrapOrNotFound(slackApp);
    }

    /**
     * DELETE  /slack-apps/:id : delete the "id" slackApp.
     *
     * @param id the id of the slackApp to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/slack-apps/{id}")
    @Timed
    public ResponseEntity<Void> deleteSlackApp(@PathVariable Long id) {
        log.debug("REST request to delete SlackApp : {}", id);
        slackAppService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
