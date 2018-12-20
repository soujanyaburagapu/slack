package com.slackers.slackapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.*;

/**
 * A Channel.
 */
@Entity
@Table(name = "channel")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Channel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "channel_name")
    private String channelName;

    @Column(name = "channel_id")
    private Long channelID;

    @Column(name = "channel_description")
    private String channelDescription;

    @Column(name = "is_private")
    private Boolean isPrivate;

    @OneToMany(mappedBy = "channel")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Message> messages = new HashSet<>();
    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "channel_app_user",
               joinColumns = @JoinColumn(name = "channels_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "app_users_id", referencedColumnName = "id"))
    private Set<AppUser> appUsers = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("channels")
    private Workspace workspace;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getChannelName() {
        return channelName;
    }

    public Channel channelName(String channelName) {
        this.channelName = channelName;
        return this;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public Long getChannelID() {
        return channelID;
    }

    public Channel channelID(Long channelID) {
        this.channelID = channelID;
        return this;
    }

    public void setChannelID(Long channelID) {
        this.channelID = channelID;
    }

    public String getChannelDescription() {
        return channelDescription;
    }

    public Channel channelDescription(String channelDescription) {
        this.channelDescription = channelDescription;
        return this;
    }

    public void setChannelDescription(String channelDescription) {
        this.channelDescription = channelDescription;
    }

    public Boolean isIsPrivate() {
        return isPrivate;
    }

    public Channel isPrivate(Boolean isPrivate) {
        this.isPrivate = isPrivate;
        return this;
    }

    public void setIsPrivate(Boolean isPrivate) {
        this.isPrivate = isPrivate;
    }

    public Set<Message> getMessages() {
        return messages;
    }

    public Channel messages(Set<Message> messages) {
        this.messages = messages;
        return this;
    }

    public Channel addMessage(Message message) {
        this.messages.add(message);
        message.setChannel(this);
        return this;
    }

    public Channel removeMessage(Message message) {
        this.messages.remove(message);
        message.setChannel(null);
        return this;
    }

    public void setMessages(Set<Message> messages) {
        this.messages = messages;
    }

    public Set<AppUser> getAppUsers() {
        return appUsers;
    }

    public Channel appUsers(Set<AppUser> appUsers) {
        this.appUsers = appUsers;
        return this;
    }

    public Channel addAppUser(AppUser appUser) {
        this.appUsers.add(appUser);
        appUser.getChannels().add(this);
        return this;
    }

    public Channel removeAppUser(AppUser appUser) {
        this.appUsers.remove(appUser);
        appUser.getChannels().remove(this);
        return this;
    }

    public void setAppUsers(Set<AppUser> appUsers) {
        this.appUsers = appUsers;
    }

    public Workspace getWorkspace() {
        return workspace;
    }

    public Channel workspace(Workspace workspace) {
        this.workspace = workspace;
        return this;
    }

    public void setWorkspace(Workspace workspace) {
        this.workspace = workspace;
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
        Channel channel = (Channel) o;
        if (channel.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), channel.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Channel{" +
            "id=" + getId() +
            ", channelName='" + getChannelName() + "'" +
            ", channelID=" + getChannelID() +
            ", channelDescription='" + getChannelDescription() + "'" +
            ", isPrivate='" + isIsPrivate() + "'" +
            "}";
    }
}
