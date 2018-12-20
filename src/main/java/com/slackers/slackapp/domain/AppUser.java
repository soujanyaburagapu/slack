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
 * A AppUser.
 */
@Entity
@Table(name = "app_user")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class AppUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "display_name")
    private String displayName;

    @Column(name = "email")
    private String email;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "jhi_password")
    private String password;

    @Column(name = "is_active")
    private Boolean isActive;

    @OneToOne    @JoinColumn(unique = true)
    private Message message;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "app_user_workspace",
               joinColumns = @JoinColumn(name = "app_users_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "workspaces_id", referencedColumnName = "id"))
    private Set<Workspace> workspaces = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "app_user_direct_message",
               joinColumns = @JoinColumn(name = "app_users_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "direct_messages_id", referencedColumnName = "id"))
    private Set<DirectMessage> directMessages = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("appUsers")
    private SlackApp slackApp;

    @ManyToMany(mappedBy = "appUsers")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JsonIgnore
    private Set<Channel> channels = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public AppUser userId(Long userId) {
        this.userId = userId;
        return this;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getDisplayName() {
        return displayName;
    }

    public AppUser displayName(String displayName) {
        this.displayName = displayName;
        return this;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getEmail() {
        return email;
    }

    public AppUser email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserName() {
        return userName;
    }

    public AppUser userName(String userName) {
        this.userName = userName;
        return this;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public AppUser password(String password) {
        this.password = password;
        return this;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean isIsActive() {
        return isActive;
    }

    public AppUser isActive(Boolean isActive) {
        this.isActive = isActive;
        return this;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Message getMessage() {
        return message;
    }

    public AppUser message(Message message) {
        this.message = message;
        return this;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public Set<Workspace> getWorkspaces() {
        return workspaces;
    }

    public AppUser workspaces(Set<Workspace> workspaces) {
        this.workspaces = workspaces;
        return this;
    }

    public AppUser addWorkspace(Workspace workspace) {
        this.workspaces.add(workspace);
        workspace.getAppUsers().add(this);
        return this;
    }

    public AppUser removeWorkspace(Workspace workspace) {
        this.workspaces.remove(workspace);
        workspace.getAppUsers().remove(this);
        return this;
    }

    public void setWorkspaces(Set<Workspace> workspaces) {
        this.workspaces = workspaces;
    }

    public Set<DirectMessage> getDirectMessages() {
        return directMessages;
    }

    public AppUser directMessages(Set<DirectMessage> directMessages) {
        this.directMessages = directMessages;
        return this;
    }

    public AppUser addDirectMessage(DirectMessage directMessage) {
        this.directMessages.add(directMessage);
        directMessage.getAppUsers().add(this);
        return this;
    }

    public AppUser removeDirectMessage(DirectMessage directMessage) {
        this.directMessages.remove(directMessage);
        directMessage.getAppUsers().remove(this);
        return this;
    }

    public void setDirectMessages(Set<DirectMessage> directMessages) {
        this.directMessages = directMessages;
    }

    public SlackApp getSlackApp() {
        return slackApp;
    }

    public AppUser slackApp(SlackApp slackApp) {
        this.slackApp = slackApp;
        return this;
    }

    public void setSlackApp(SlackApp slackApp) {
        this.slackApp = slackApp;
    }

    public Set<Channel> getChannels() {
        return channels;
    }

    public AppUser channels(Set<Channel> channels) {
        this.channels = channels;
        return this;
    }

    public AppUser addChannel(Channel channel) {
        this.channels.add(channel);
        channel.getAppUsers().add(this);
        return this;
    }

    public AppUser removeChannel(Channel channel) {
        this.channels.remove(channel);
        channel.getAppUsers().remove(this);
        return this;
    }

    public void setChannels(Set<Channel> channels) {
        this.channels = channels;
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
        AppUser appUser = (AppUser) o;
        if (appUser.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), appUser.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AppUser{" +
            "id=" + getId() +
            ", userId=" + getUserId() +
            ", displayName='" + getDisplayName() + "'" +
            ", email='" + getEmail() + "'" +
            ", userName='" + getUserName() + "'" +
            ", password='" + getPassword() + "'" +
            ", isActive='" + isIsActive() + "'" +
            "}";
    }
}
