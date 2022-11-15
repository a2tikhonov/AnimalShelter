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

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    private Owner owner;

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

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }
}
