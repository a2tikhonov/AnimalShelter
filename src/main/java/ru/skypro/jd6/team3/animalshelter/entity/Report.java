package ru.skypro.jd6.team3.animalshelter.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.jetbrains.annotations.Nullable;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Objects;

@Entity
@Table(name = "reports")
public class Report {

    @Id
    @GeneratedValue
    private Long id;
    private String filePath;
    private int fileSize;
    private byte[] photo;
    private LocalDateTime dateTime;
    private String diet;
    private String condition;
    private String changes;
    private String mediaType;

    @ManyToOne
    @JoinColumn(name = "potential_owner_id")
    @JsonIgnore
    @Nullable
    private PotentialOwner potentialOwner;

    public Report() {
        this.id = 0L;
        this.filePath = null;
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

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
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
                ", filePath='" + filePath + '\'' +
                ", fileSize=" + fileSize +
                ", photo=" + Arrays.toString(photo) +
                ", dateTime=" + dateTime +
                ", diet='" + diet + '\'' +
                ", condition='" + condition + '\'' +
                ", changes='" + changes + '\'' +
                ", potentialOwner=" + potentialOwner +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Report report = (Report) o;
        return fileSize == report.fileSize && Objects.equals(id, report.id) && Objects.equals(filePath, report.filePath) && Arrays.equals(photo, report.photo) && Objects.equals(dateTime, report.dateTime) && Objects.equals(diet, report.diet) && Objects.equals(condition, report.condition) && Objects.equals(changes, report.changes) && Objects.equals(potentialOwner, report.potentialOwner);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(id, filePath, fileSize, dateTime, diet, condition, changes, potentialOwner);
        result = 31 * result + Arrays.hashCode(photo);
        return result;
    }
}
