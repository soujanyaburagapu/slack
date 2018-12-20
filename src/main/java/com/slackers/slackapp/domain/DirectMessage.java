package com.slackers.slackapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DirectMessage.
 */
@Entity
@Table(name = "direct_message")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class DirectMessage implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "dm_id")
    private Long dmId;

    @Column(name = "message")
    private String message;

    @OneToMany(mappedBy = "directMessage")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Message> messages = new HashSet<>();
    @ManyToMany(mappedBy = "directMessages")
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

    public Long getDmId() {
        return dmId;
    }

    public DirectMessage dmId(Long dmId) {
        this.dmId = dmId;
        return this;
    }

    public void setDmId(Long dmId) {
        this.dmId = dmId;
    }

    public String getMessage() {
        return message;
    }

    public DirectMessage message(String message) {
        this.message = message;
        return this;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Set<Message> getMessages() {
        return messages;
    }

    public DirectMessage messages(Set<Message> messages) {
        this.messages = messages;
        return this;
    }

    public DirectMessage addMessage(Message message) {
        this.messages.add(message);
        message.setDirectMessage(this);
        return this;
    }

    public DirectMessage removeMessage(Message message) {
        this.messages.remove(message);
        message.setDirectMessage(null);
        return this;
    }

    public void setMessages(Set<Message> messages) {
        this.messages = messages;
    }

    public Set<AppUser> getAppUsers() {
        return appUsers;
    }

    public DirectMessage appUsers(Set<AppUser> appUsers) {
        this.appUsers = appUsers;
        return this;
    }

    public DirectMessage addAppUser(AppUser appUser) {
        this.appUsers.add(appUser);
        appUser.getDirectMessages().add(this);
        return this;
    }

    public DirectMessage removeAppUser(AppUser appUser) {
        this.appUsers.remove(appUser);
        appUser.getDirectMessages().remove(this);
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
        DirectMessage directMessage = (DirectMessage) o;
        if (directMessage.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), directMessage.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DirectMessage{" +
            "id=" + getId() +
            ", dmId=" + getDmId() +
            ", message='" + getMessage() + "'" +
            "}";
    }
}
