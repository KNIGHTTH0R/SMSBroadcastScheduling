package com.tlkm.broadcast5g.model;

import javax.persistence.*;
import java.util.Date;

@Entity
public class UploadHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String fileName;

    @Column
    private Date uploadDate;

    @Column
    private int generatedCount;

    @Column
    private Date lastGenerated;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Date getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(Date uploadDate) {
        this.uploadDate = uploadDate;
    }

    public int getGeneratedCount() {
        return generatedCount;
    }

    public void setGeneratedCount(int generatedCount) {
        this.generatedCount = generatedCount;
    }

    public Date getLastGenerated() {
        return lastGenerated;
    }

    public void setLastGenerated(Date lastGenerated) {
        this.lastGenerated = lastGenerated;
    }
}
