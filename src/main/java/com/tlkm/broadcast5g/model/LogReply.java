package com.tlkm.broadcast5g.model;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
public class LogReply {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateReply;

    @Column
    private String encryptNo;

    @Column
    private String content;

    @Column
    private String optId;

    @Column
    private String shortCode;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDateReply() {
        return dateReply;
    }

    public void setDateReply(Date dateReply) {
        this.dateReply = dateReply;
    }

    public String getEncryptNo() {
        return encryptNo;
    }

    public void setEncryptNo(String encryptNo) {
        this.encryptNo = encryptNo;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getOptId() {
        return optId;
    }

    public void setOptId(String optId) {
        this.optId = optId;
    }

    public String getShortCode() {
        return shortCode;
    }

    public void setShortCode(String shortCode) {
        this.shortCode = shortCode;
    }
}
