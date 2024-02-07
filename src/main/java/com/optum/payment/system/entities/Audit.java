package com.optum.payment.system.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

import static com.optum.payment.system.global.InstallConstants.DEFAULT_TIMEZONE;


@SuppressWarnings("serial")
@MappedSuperclass
@Getter
@Setter
@NoArgsConstructor
/*Skeleton of records of the payment system*/
public class Audit implements Serializable {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss",
            locale = JsonFormat.DEFAULT_LOCALE, timezone = DEFAULT_TIMEZONE)
    @Column(name = "CREATED", nullable = false)
    @JsonProperty
    private Date created;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss",
            locale = JsonFormat.DEFAULT_LOCALE, timezone = DEFAULT_TIMEZONE)
    @Column(name = "UPDATED", nullable = false)
    @JsonProperty
    private Date updated;

    @PrePersist
    protected void setCreatedDate() {
        created = new Date();
        updated = new Date();
    }

    @PreUpdate
    protected void setUpdatedDate() {
        updated = new Date();
    }

    @Column(name = "CREATED_BY", nullable = false)
    @JsonProperty
    private String createdBy="ADMIN";

    @Column(name = "CHANGED_BY", nullable = false)
    @JsonProperty
    private String changedBy="ADMIN";

    public Audit(Date created, Date updated, String createdBy, String changedBy) {
        this.created = created;
        this.updated = updated;
        this.createdBy = createdBy;
        this.changedBy = changedBy;
    }

}