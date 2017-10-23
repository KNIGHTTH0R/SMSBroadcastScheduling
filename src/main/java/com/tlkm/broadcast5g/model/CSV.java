package com.tlkm.broadcast5g.model;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
public class CSV extends SMS {

    @Id
    @GeneratedValue(strategy =GenerationType.AUTO)
    private Long id;

    @Column
    private String ncli;

    @Column
    private String ndPots;


    @Column
    private String namaPelanggan;

    @Column
    private String noHP;

    @Column
    private String email;

    @Column
    private String namaPemilik;

    @Column
    private String alamat;

    @Column
    private String kota;

    @Column
    private String regional;

    @Column
    private String fileName;

    @Column
    private String replyContent;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date saveDate;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date sentDate;


    @Column
    private String encrtypNo;

    public String getEncrtypNo() {
        return encrtypNo;
    }

    public void setEncrtypNo(String encrtypNo) {
        this.encrtypNo = encrtypNo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNcli() {
        return ncli;
    }

    public void setNcli(String ncli) {
        this.ncli = ncli;
    }

    public String getNdPots() {
        return ndPots;
    }

    public void setNdPots(String ndPots) {
        this.ndPots = ndPots;
    }

    public String getNamaPelanggan() {
        return namaPelanggan;
    }

    public void setNamaPelanggan(String namaPelanggan) {
        this.namaPelanggan = namaPelanggan;
    }

    public String getNoHP() {
        return noHP;
    }

    public void setNoHP(String noHP) {
        this.noHP = noHP;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNamaPemilik() {
        return namaPemilik;
    }

    public void setNamaPemilik(String namaPemilik) {
        this.namaPemilik = namaPemilik;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getKota() {
        return kota;
    }

    public void setKota(String kota) {
        this.kota = kota;
    }

    public String getRegional() {
        return regional;
    }

    public void setRegional(String regional) {
        this.regional = regional;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public String getReplyContent() {
        return replyContent;
    }

    @Override
    public void setReplyContent(String replyContent) {
        this.replyContent = replyContent;
    }

    public Date getSaveDate() {
        return saveDate;
    }

    public void setSaveDate(Date saveDate) {
        this.saveDate = saveDate;
    }

    @Override
    public Date getSentDate() {
        return sentDate;
    }

    @Override
    public void setSentDate(Date sentDate) {
        this.sentDate = sentDate;
    }
}
