package com.example.Web.Project.model;

import com.example.Web.Project.customID.CustomID;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name = "chat")
public class Chat {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "chat_id")
    @GenericGenerator(name = "chat_id",strategy = "com.example.Web.Project.customID.CustomID",parameters = {
            @org.hibernate.annotations.Parameter(name = CustomID.INCREMENT_PARAM, value = "1"),
            @org.hibernate.annotations.Parameter(name = CustomID.VALUE_PREFIX_PARAMETER, value = "chat"),
            @org.hibernate.annotations.Parameter(name = CustomID.NUMBER_FORMAT_PARAMETER, value = "%05d")
    })
    private String id;

    private String message;
    private String msgFrom;
    private String createdAt;
    private String mediaFile;
    private String media;

    public String getMedia() {
        return media;
    }

    public void setMedia(String media) {
        this.media = media;
    }

    public String getMsgFrom() {
        return msgFrom;
    }

    public void setMsgFrom(String msgFrom) {
        this.msgFrom = msgFrom;
    }

    public String getMediaType() {
        return mediaType;
    }

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }

    private String mediaType;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getMediaFile() {
        return mediaFile;
    }

    public void setMediaFile(String mediaFile) {
        this.mediaFile = mediaFile;
    }

    public Chat() {
    }
}
