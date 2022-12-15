package ru.skypro.jd6.team3.animalshelter.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name = "reports")
public class Report {

    @Id
    @GeneratedValue
    private long reportId;
    private long description;


    public Report() {
    }

    public long getReportId() {
        return reportId;
    }

    public void setReportId(long reportId) {
        this.reportId = reportId;
    }

    public long getDescription() {
        return description;
    }

    public void setDescription(long description) {
        this.description = description;
    }

}
