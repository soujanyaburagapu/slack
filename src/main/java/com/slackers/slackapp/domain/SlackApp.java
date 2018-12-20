package com.slackers.slackapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * Task entity.
 * @author The JHipster team.
 */
@ApiModel(description = "Task entity. @author The JHipster team.")
@Entity
@Table(name = "slack_app")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class SlackApp implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @OneToMany(mappedBy = "slackApp")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Workspace> workspaces = new HashSet<>();
    @OneToMany(mappedBy = "slackApp")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<AppUser> appUsers = new HashSet<>();
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<Workspace> getWorkspaces() {
        return workspaces;
    }

    public SlackApp workspaces(Set<Workspace> workspaces) {
        this.workspaces = workspaces;
        return this;
    }

    public SlackApp addWorkspace(Workspace workspace) {
        this.workspaces.add(workspace);
        workspace.setSlackApp(this);
        return this;
    }

    public SlackApp removeWorkspace(Workspace workspace) {
        this.workspaces.remove(workspace);
        workspace.setSlackApp(null);
        return this;
    }

    public void setWorkspaces(Set<Workspace> workspaces) {
        this.workspaces = workspaces;
    }

    public Set<AppUser> getAppUsers() {
        return appUsers;
    }

    public SlackApp appUsers(Set<AppUser> appUsers) {
        this.appUsers = appUsers;
        return this;
    }

    public SlackApp addAppUser(AppUser appUser) {
        this.appUsers.add(appUser);
        appUser.setSlackApp(this);
        return this;
    }

    public SlackApp removeAppUser(AppUser appUser) {
        this.appUsers.remove(appUser);
        appUser.setSlackApp(null);
        return this;
    }

    public void setAppUsers(Set<AppUser> appUsers) {
        this.appUsers = appUsers;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SlackApp slackApp = (SlackApp) o;
        if (slackApp.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), slackApp.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SlackApp{" +
            "id=" + getId() +
            "}";
    }
}
