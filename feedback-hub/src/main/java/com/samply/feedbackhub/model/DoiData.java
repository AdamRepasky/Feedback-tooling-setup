package com.samply.feedbackhub.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.macasaet.fernet.Key;
import com.macasaet.fernet.Token;
import jakarta.persistence.*;
@Entity
@Table(name = "doi_data")
public class DoiData {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "request_id")
    private String requestID;

    @JsonIgnore
    @Column(name = "publication_reference_token")
    private String publicationReferenceToken;

    @JsonIgnore
    @Column(name = "sym_enc_key")
    private String symEncKey;

    @JsonIgnore
    @Column(name = "access_code")
    private String accessCode;
    public DoiData() {
        super();
    }

    public DoiData(String requestID, String publicationReference, String symEncKey, String accessCode) {
        this.requestID = requestID;
        this.symEncKey = symEncKey;
        this.publicationReferenceToken = Token.generate(new Key(symEncKey), publicationReference).serialise();
        this.accessCode = accessCode;
        //this.setPublicationReferenceToken(symEncKey, publicationReference);
    }

    public long getId() {
        return id;
    }

    public String getRequestID() {
        return requestID;
    }

    public void setRequestID(String requestID) {
        this.requestID = requestID;
    }

    public void setPublicationReferenceToken(String publicationReference, String symEncKey) {
        this.publicationReferenceToken = Token.generate(new Key(symEncKey), publicationReference).serialise();
    }
    public void setPublicationReferenceToken(String publicationReferenceToken) {
        this.publicationReferenceToken = publicationReferenceToken;
    }

    public String getPublicationReferenceToken() {
        return this.publicationReferenceToken;
    }

    public String getSymEncKey() {
        return symEncKey;
    }

    public void setSymEncKey(String encryptionToken) {
        this.symEncKey = encryptionToken;
    }

    public String getAccessCode() {
        return accessCode;
    }

    public void setAccessCode(String accessCode) {
        this.accessCode = accessCode;
    }
}