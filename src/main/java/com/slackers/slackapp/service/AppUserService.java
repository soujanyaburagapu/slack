package com.slackers.slackapp.service;

import com.slackers.slackapp.domain.AppUser;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing AppUser.
 */
public interface AppUserService {

    /**
     * Save a appUser.
     *
     * @param appUser the entity to save
     * @return the persisted entity
     */
    AppUser save(AppUser appUser);

    /**
     * Get all the appUsers.
     *
     * @return the list of entities
     */
    List<AppUser> findAll();

    /**
     * Get all the AppUser with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    Page<AppUser> findAllWithEagerRelationships(Pageable pageable);
    
    /**
     * Get the "id" appUser.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<AppUser> findOne(Long id);

    /**
     * Delete the "id" appUser.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
