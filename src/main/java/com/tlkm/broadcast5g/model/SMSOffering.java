package com.tlkm.broadcast5g.model;


import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
public class SMSOffering {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(length = 20)
    private String msisdn;

    @Column
    private String content;

    @Column(length = 20)
    private String senderID;

    @Column
    private String responseID;

    @Column
    private String responseDesc;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date sentDate;

    @Column
    private String optName;

    @Column
    private String providerName;

    @Column
    private String pin;

    @Column
    private String userId;

    @Column
    private int smsCount;

    @Column
    private int statusId;

    @Column
    private String statusDesc;

    @Column
    private String trxId;

    @Column
    @ColumnDefault("0")
    private int replyStatus;


    @Temporal(TemporalType.TIMESTAMP)
    private Date replyDate;

    @Override
    public String toString() {
        return "SMSOffering{" +
                "id=" + id +
                ", msisdn='" + msisdn + '\'' +
                ", content='" + content + '\'' +
                ", senderID='" + senderID + '\'' +
                ", responseID='" + responseID + '\'' +
                ", responseDesc='" + responseDesc + '\'' +
                ", sentDate=" + sentDate +
                ", optName='" + optName + '\'' +
                ", providerName='" + providerName + '\'' +
                ", pin='" + pin + '\'' +
                ", userId='" + userId + '\'' +
                ", smsCount=" + smsCount +
                ", statusId=" + statusId +
                ", statusDesc='" + statusDesc + '\'' +
                ", trxId='" + trxId + '\'' +
                ", replyStatus=" + replyStatus +
                ", replyDate=" + replyDate +
                '}';
    }

    public String getSenderID() {
        return senderID;
    }

    public void setSenderID(String senderID) {
        this.senderID = senderID;
    }

    public String getResponseID() {
        return responseID;
    }

    public void setResponseID(String responseID) {
        this.responseID = responseID;
    }

    public String getResponseDesc() {
        return responseDesc;
    }

    public void setResponseDesc(String responseDesc) {
        this.responseDesc = responseDesc;
    }

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getSentDate() {
        return sentDate;
    }

    public void setSentDate(Date sentDate) {
        this.sentDate = sentDate;
    }

    public String getOptName() {
        return optName;
    }

    public void setOptName(String optName) {
        this.optName = optName;
    }

    public String getProviderName() {
        return providerName;
    }

    public void setProviderName(String providerName) {
        this.providerName = providerName;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getSmsCount() {
        return smsCount;
    }

    public void setSmsCount(int smsCount) {
        this.smsCount = smsCount;
    }

    public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }

    public String getStatusDesc() {
        return statusDesc;
    }

    public void setStatusDesc(String statusDesc) {
        this.statusDesc = statusDesc;
    }

    public String getTrxId() {
        return trxId;
    }

    public void setTrxId(String trxId) {
        this.trxId = trxId;
    }

    public int getReplyStatus() {
        return replyStatus;
    }

    public void setReplyStatus(int replyStatus) {
        this.replyStatus = replyStatus;
    }

    public Date getReplyDate() {
        return replyDate;
    }

    public void setReplyDate(Date replyDate) {
        this.replyDate = replyDate;
    }
}
