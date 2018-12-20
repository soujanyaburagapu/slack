package com.slackers.slackapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Workspace.
 */
@Entity
@Table(name = "workspace")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Workspace implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "workspace_name")
    private String workspaceName;

    @Column(name = "jhi_admin")
    private String admin;

    @Column(name = "workspace_id")
    private Long workspaceID;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "workspace")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Channel> channels = new HashSet<>();
    @ManyToOne
    @JsonIgnoreProperties("workspaces")
    private SlackApp slackApp;

    @ManyToMany(mappedBy = "workspaces")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JsonIgnore
    private Set<AppUser> appUsers = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getWorkspaceName() {
        return workspaceName;
    }

    public Workspace workspaceName(String workspaceName) {
        this.workspaceName = workspaceName;
        return this;
    }

    public void setWorkspaceName(String workspaceName) {
        this.workspaceName = workspaceName;
    }

    public String getAdmin() {
        return admin;
    }

    public Workspace admin(String admin) {
        this.admin = admin;
        return this;
    }

    public void setAdmin(String admin) {
        this.admin = admin;
    }

    public Long getWorkspaceID() {
        return workspaceID;
    }

    public Workspace workspaceID(Long workspaceID) {
        this.workspaceID = workspaceID;
        return this;
    }

    public void setWorkspaceID(Long workspaceID) {
        this.workspaceID = workspaceID;
    }

    public String getDescription() {
        return description;
    }

    public Workspace description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Channel> getChannels() {
        return channels;
    }

    public Workspace channels(Set<Channel> channels) {
        this.channels = channels;
        return this;
    }

    public Workspace addChannel(Channel channel) {
        this.channels.add(channel);
        channel.setWorkspace(this);
        return this;
    }

    public Workspace removeChannel(Channel channel) {
        this.channels.remove(channel);
        channel.setWorkspace(null);
        return this;
    }

    public void setChannels(Set<Channel> channels) {
        this.channels = channels;
    }

    public SlackApp getSlackApp() {
        return slackApp;
    }

    public Workspace slackApp(SlackApp slackApp) {
        this.slackApp = slackApp;
        return this;
    }

    public void setSlackApp(SlackApp slackApp) {
        this.slackApp = slackApp;
    }

    public Set<AppUser> getAppUsers() {
        return appUsers;
    }

    public Workspace appUsers(Set<AppUser> appUsers) {
        this.appUsers = appUsers;
        return this;
    }

    public Workspace addAppUser(AppUser appUser) {
        this.appUsers.add(appUser);
        appUser.getWorkspaces().add(this);
        return this;
    }

    public Workspace removeAppUser(AppUser appUser) {
        this.appUsers.remove(appUser);
        appUser.getWorkspaces().remove(this);
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
        Workspace workspace = (Workspace) o;
        if (workspace.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), workspace.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Workspace{" +
            "id=" + getId() +
            ", workspaceName='" + getWorkspaceName() + "'" +
            ", admin='" + getAdmin() + "'" +
            ", workspaceID=" + getWorkspaceID() +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
