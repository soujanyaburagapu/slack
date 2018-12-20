package com.slackers.slackapp.service.impl;

import com.slackers.slackapp.service.AppUserService;
import com.slackers.slackapp.domain.AppUser;
import com.slackers.slackapp.repository.AppUserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing AppUser.
 */
@Service
@Transactional
public class AppUserServiceImpl implements AppUserService {

    private final Logger log = LoggerFactory.getLogger(AppUserServiceImpl.class);

    private final AppUserRepository appUserRepository;

    public AppUserServiceImpl(AppUserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
    }

    /**
     * Save a appUser.
     *
     * @param appUser the entity to save
     * @return the persisted entity
     */
    @Override
    public AppUser save(AppUser appUser) {
        log.debug("Request to save AppUser : {}", appUser);
        return appUserRepository.save(appUser);
    }

    /**
     * Get all the appUsers.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<AppUser> findAll() {
        log.debug("Request to get all AppUsers");
        return appUserRepository.findAllWithEagerRelationships();
    }

    /**
     * Get all the AppUser with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    public Page<AppUser> findAllWithEagerRelationships(Pageable pageable) {
        return appUserRepository.findAllWithEagerRelationships(pageable);
    }
    

    /**
     * Get one appUser by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<AppUser> findOne(Long id) {
        log.debug("Request to get AppUser : {}", id);
        return appUserRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the appUser by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete AppUser : {}", id);
        appUserRepository.deleteById(id);
    }
}
