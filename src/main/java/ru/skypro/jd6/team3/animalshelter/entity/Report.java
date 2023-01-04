package ru.skypro.jd6.team3.animalshelter.entity;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "reports")
public class Report {

    @Id
    @GeneratedValue(generator = "reportGen")
    private Long id;
    private Long fileSize;
    private String photoId;
    private LocalDate dayOfMonth;
    private String reportText;
    private String mediaType;

    @ManyToOne
    @JoinColumn(name = "potential_owner_id")
    private PotentialOwner potentialOwner;

    public Report() {
        this.id = 0L;
        this.fileSize = 0L;
        this.dayOfMonth = null;
        this.reportText = null;
        this.mediaType = null;
        this.photoId = null;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getFileSize() {
        return fileSize;
    }

    public String getMediaType() {
        return mediaType;
    }

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    public LocalDate getDayOfMonth() {
        return dayOfMonth;
    }

    public void setDayOfMonth(LocalDate day) {
        this.dayOfMonth = day;
    }

    public PotentialOwner getOwner() {
        return potentialOwner;
    }

    public void setOwner(PotentialOwner potentialOwner) {
        this.potentialOwner = potentialOwner;
    }

    public String getPhotoId() {
        return photoId;
    }

    public void setPhotoId(String fileId) {
        this.photoId = fileId;
    }

    public String getReportText() {
        return reportText;
    }

    public void setReportText(String reportText) {
        this.reportText = reportText;
    }

    @Override
    public String toString() {
        return "Report{" +
                "id=" + id +
                ", photoId='" + photoId + '\'' +
                ", dayOfMonth=" + dayOfMonth +
                ", reportText='" + reportText + '\'' +
                ", potentialOwner=" + potentialOwner +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Report report = (Report) o;
        return id.equals(report.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}