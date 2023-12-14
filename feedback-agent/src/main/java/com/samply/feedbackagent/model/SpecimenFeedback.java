package com.samply.feedbackagent.model;

import jakarta.persistence.*;
@Entity
@Table(name = "specimen_feedback")
public class SpecimenFeedback {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "sample_id")
    private String sampleID;

    @Column(name = "request_id")
    private String requestID;

    @Column(name = "patient_id")
    private String patientID;

    @Column(name = "type")
    private String type;

    @Column(name = "collection_date")
    private String collectionDate;

    @Column(name = "publication_reference")
    private String publicationReference;

    public SpecimenFeedback() {
        super();
    }

    public SpecimenFeedback(long id, String sampleID, String requestID, String patientID, String collectionDate, String type, String publicationReference) {
        this.id = id;
        this.sampleID = sampleID;
        this.requestID = requestID;
        this.patientID = patientID;
        this.collectionDate = collectionDate;
        this.type = type;
        this.publicationReference = publicationReference;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSampleID() {
        return sampleID;
    }

    public void setSampleID(String specimenID) {
        this.sampleID = specimenID;
    }

    public String getRequestID() {
        return requestID;
    }

    public void setRequestID(String requestID) {
        this.requestID = requestID;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPublicationReference() {
        return publicationReference;
    }

    public String getPatientID() {
        return patientID;
    }

    public void setPatientID(String patientID) {
        this.patientID = patientID;
    }

    public String getCollectionDate() {
        return collectionDate;
    }

    public void setCollectionDate(String collectionDate) {
        this.collectionDate = collectionDate;
    }

    public void setPublicationReference(String publicationReference) {
        this.publicationReference = publicationReference;
    }
}