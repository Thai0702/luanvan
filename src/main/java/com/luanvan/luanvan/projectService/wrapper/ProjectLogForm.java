package com.luanvan.luanvan.projectService.wrapper;

import java.sql.Timestamp;

public class ProjectLogForm {
    private int created_by;
    private Timestamp createdAt;
    private String logTitle;
    private String logDescription;
    private String attachment;

    public ProjectLogForm(int created_by, Timestamp createdAt, String logTitle, String logDescription, String attachment) {
        this.created_by = created_by;
        this.createdAt = createdAt;
        this.logTitle = logTitle;
        this.logDescription = logDescription;
        this.attachment = attachment;
    }

    public int getCreated_by() {
        return created_by;
    }

    public void setCreated_by(int created_by) {
        this.created_by = created_by;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }


    public String getLogTitle() {
        return logTitle;
    }

    public void setLogTitle(String logTitle) {
        this.logTitle = logTitle;
    }

    public String getLogDescription() {
        return logDescription;
    }

    public void setLogDescription(String logDescription) {
        this.logDescription = logDescription;
    }

    public String getAttachment() {
        return attachment;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }
}
