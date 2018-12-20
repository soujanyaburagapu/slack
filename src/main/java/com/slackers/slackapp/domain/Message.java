package com.slackers.slackapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A Message.
 */
@Entity
@Table(name = "message")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Message implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "message_id")
    private Long messageId;

    @Column(name = "message")
    private String message;

    @Column(name = "time_stamp")
    private ZonedDateTime timeStamp;

    @Column(name = "date_stamp")
    private LocalDate dateStamp;

    @Column(name = "sender")
    private String sender;

    @OneToOne(mappedBy = "message")
    @JsonIgnore
    private AppUser appUser;

    @ManyToOne
    @JsonIgnoreProperties("messages")
    private Channel channel;

    @ManyToOne
    @JsonIgnoreProperties("messages")
    private DirectMessage directMessage;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMessageId() {
        return messageId;
    }

    public Message messageId(Long messageId) {
        this.messageId = messageId;
        return this;
    }

    public void setMessageId(Long messageId) {
        this.messageId = messageId;
    }

    public String getMessage() {
        return message;
    }

    public Message message(String message) {
        this.message = message;
        return this;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ZonedDateTime getTimeStamp() {
        return timeStamp;
    }

    public Message timeStamp(ZonedDateTime timeStamp) {
        this.timeStamp = timeStamp;
        return this;
    }

    public void setTimeStamp(ZonedDateTime timeStamp) {
        this.timeStamp = timeStamp;
    }

    public LocalDate getDateStamp() {
        return dateStamp;
    }

    public Message dateStamp(LocalDate dateStamp) {
        this.dateStamp = dateStamp;
        return this;
    }

    public void setDateStamp(LocalDate dateStamp) {
        this.dateStamp = dateStamp;
    }

    public String getSender() {
        return sender;
    }

    public Message sender(String sender) {
        this.sender = sender;
        return this;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public AppUser getAppUser() {
        return appUser;
    }

    public Message appUser(AppUser appUser) {
        this.appUser = appUser;
        return this;
    }

    public void setAppUser(AppUser appUser) {
        this.appUser = appUser;
    }

    public Channel getChannel() {
        return channel;
    }

    public Message channel(Channel channel) {
        this.channel = channel;
        return this;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    public DirectMessage getDirectMessage() {
        return directMessage;
    }

    public Message directMessage(DirectMessage directMessage) {
        this.directMessage = directMessage;
        return this;
    }

    public void setDirectMessage(DirectMessage directMessage) {
        this.directMessage = directMessage;
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
        Message message = (Message) o;
        if (message.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), message.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Message{" +
            "id=" + getId() +
            ", messageId=" + getMessageId() +
            ", message='" + getMessage() + "'" +
            ", timeStamp='" + getTimeStamp() + "'" +
            ", dateStamp='" + getDateStamp() + "'" +
            ", sender='" + getSender() + "'" +
            "}";
    }
}
