package ru.skypro.jd6.team3.animalshelter.entity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "reports")
public class Report {

    @Id
    @GeneratedValue
    private Long id;
    private int fileSize;
    private byte[] photo;
    private LocalDateTime dateTime;
    private String diet;
    private String condition;
    private String changes;
    private String mediaType;

    @ManyToOne
    @JoinColumn(name = "potential_owner_id")
    private PotentialOwner potentialOwner;

    public Report() {
        this.id = 0L;
        this.fileSize = 0;
        this.photo = null;
        this.dateTime = null;
        this.diet = null;
        this.condition = null;
        this.changes = null;
        this.mediaType = null;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getFileSize() {
        return fileSize;
    }

    public String getMediaType() {
        return mediaType;
    }

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }

    public void setFileSize(int fileSize) {
        this.fileSize = fileSize;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public String getDiet() {
        return diet;
    }

    public void setDiet(String diet) {
        this.diet = diet;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getChanges() {
        return changes;
    }

    public void setChanges(String changes) {
        this.changes = changes;
    }

    public PotentialOwner getOwner() {
        return potentialOwner;
    }

    public void setOwner(PotentialOwner potentialOwner) {
        this.potentialOwner = potentialOwner;
    }

    @Override
    public String toString() {
        return "Report{" +
                "id=" + id +
                ", dateTime=" + dateTime +
                ", diet='" + diet + '\'' +
                ", condition='" + condition + '\'' +
                ", changes='" + changes + '\'' +
                ", mediaType='" + mediaType + '\'' +
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