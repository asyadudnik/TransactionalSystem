package com.optum.payment.system.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.optum.payment.system.entities.enums.ActiveStatus;
import jakarta.persistence.*;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;


import java.util.Date;
import java.util.Objects;

@SuppressWarnings("serial")
@Entity
@Table(name = "PARTICIPANTS")
@Getter
@Setter
@ToString
@NoArgsConstructor
@JsonRootName(value = "participant")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Participant extends Audit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false, unique = true, insertable = false, updatable = false)
    private Long id;

    @NotNull(message = "Please enter full name")
    @Column(name = "PARTICIPANT_NAME", nullable = false)
    @JsonProperty
    private String participantName;


    @NotNull(message = "Please enter IBAN ACCOUNT")
    @Column(name = "IBAN_ACCOUNT", nullable = false, unique = true)
    @JsonProperty
    private String ibanAccount;

    /*card*/
    @NotNull(message = "Please enter card number")
    @Column(name = "CARD_NUMBER", nullable = false, unique = true)
    @JsonProperty
    private String cardNumber;

    @NotNull(message = "Please enter card valid term")
    @Length(min = 5, max = 5, message = "MM/YY")
    @Column(name = "CARD_VALID_TERM", nullable = false)
    @JsonProperty
    private String cardValidTerm;


    @NotNull(message = "Please enter card holder")
    @Column(name = "CARD_HOLDER", nullable = false)
    @JsonProperty
    private String cardHolder;

    @NotNull(message = "Please enter card valid CVV")
    @Length(min = 3, max = 3, message = "CVV")
    @Column(name = "CARD_CVV", nullable = false, unique = true)
    @JsonProperty
    private String cardCVV;

    /*status*/
    @NotNull
    @Column(name = "PARTICIPANT_STATUS", nullable = false)
    @JsonProperty
    private String participantStatus = ActiveStatus.ACTIVE.name();

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "LOCK_START_TIME", nullable = true)
    @JsonProperty
    private Date lockStartTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "LOCK_END_TIME", nullable = true)
    @JsonProperty
    private Date lockEndDate;

    //OneToMany=======================================================================================
    /*bank*/


    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "BANK_ID")
    private Bank bank;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Participant)) return false;
        Participant that = (Participant) o;
        return Objects.equals(getId(), that.getId()) && Objects.equals(getParticipantName(), that.getParticipantName()) && Objects.equals(getIbanAccount(), that.getIbanAccount()) && Objects.equals(getCardNumber(), that.getCardNumber()) && Objects.equals(getCardValidTerm(), that.getCardValidTerm()) && Objects.equals(getCardHolder(), that.getCardHolder()) && Objects.equals(getCardCVV(), that.getCardCVV()) && Objects.equals(getParticipantStatus(), that.getParticipantStatus()) && Objects.equals(getLockStartTime(), that.getLockStartTime()) && Objects.equals(getLockEndDate(), that.getLockEndDate()) && Objects.equals(getBank(), that.getBank());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getParticipantName(), getIbanAccount(), getCardNumber(), getCardValidTerm(), getCardHolder(), getCardCVV(), getParticipantStatus(), getLockStartTime(), getLockEndDate(), getBank());
    }
}
