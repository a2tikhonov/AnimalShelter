package ru.skypro.jd6.team3.animalshelter.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.List;
import java.util.Objects;

@Entity
//@Table(name = "recommendations")
public class Recommendations {
    @Id
    @GeneratedValue
    private long id;

    public Recommendations() {}

    public String rules;
    public String documents;
    public String transportationInformation;
    public String arrangementInformation;
    public String cynologistAdvices;
    public String listOfCynologists;
    public String commonRejectReasons;

    @Override
    public String toString() {
        return "Recommendations{" +
                "id=" + id +
                ", rules='" + rules + '\'' +
                ", documents='" + documents + '\'' +
                ", transportationInformation='" + transportationInformation + '\'' +
                ", arrangementInformation='" + arrangementInformation + '\'' +
                ", cynologistAdvices='" + cynologistAdvices + '\'' +
                ", listOfCynologists='" + listOfCynologists + '\'' +
                ", commonRejectReasons='" + commonRejectReasons + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Recommendations that = (Recommendations) o;
        return id == that.id && Objects.equals(rules, that.rules) && Objects.equals(documents, that.documents) && Objects.equals(transportationInformation, that.transportationInformation) && Objects.equals(arrangementInformation, that.arrangementInformation) && Objects.equals(cynologistAdvices, that.cynologistAdvices) && Objects.equals(listOfCynologists, that.listOfCynologists) && Objects.equals(commonRejectReasons, that.commonRejectReasons);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, rules, documents, transportationInformation, arrangementInformation, cynologistAdvices, listOfCynologists, commonRejectReasons);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getRules() {
        return rules;
    }

    public void setRules(String rules) {
        this.rules = rules;
    }

    public String getDocuments() {
        return documents;
    }

    public void setDocuments(String documents) {
        this.documents = documents;
    }

    public String getTransportationInformation() {
        return transportationInformation;
    }

    public void setTransportationInformation(String transportationInformation) {
        this.transportationInformation = transportationInformation;
    }

    public String getArrangementInformation() {
        return arrangementInformation;
    }

    public void setArrangementInformation(String arrangementInformation) {
        this.arrangementInformation = arrangementInformation;
    }

    public String getCynologistAdvices() {
        return cynologistAdvices;
    }

    public void setCynologistAdvices(String cynologistAdvices) {
        this.cynologistAdvices = cynologistAdvices;
    }

    public String getListOfCynologists() {
        return listOfCynologists;
    }

    public void setListOfCynologists(String listOfCynologists) {
        this.listOfCynologists = listOfCynologists;
    }

    public String getCommonRejectReasons() {
        return commonRejectReasons;
    }

    public void setCommonRejectReasons(String commonRejectReasons) {
        this.commonRejectReasons = commonRejectReasons;
    }
}
