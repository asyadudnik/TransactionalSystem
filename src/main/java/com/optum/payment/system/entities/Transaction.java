package com.optum.payment.system.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import jakarta.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.resource.transaction.spi.TransactionStatus;
import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;
import java.util.Set;

@SuppressWarnings("serial")
@Entity
@Table(name = "TRANSACTIONS")
@Getter
@Setter
@ToString
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonRootName(value = "transaction")
public class Transaction extends Audit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false, unique = true, insertable = false, updatable = false)
    private Long id;

    @NotNull
    @Column(name = "TRANSACTION_STATUS", nullable = false)
    @JsonProperty
    private String transactionStatus = TransactionStatus.ACTIVE.name();

    @NotNull
    @Column(name = "TRANSACTION_SUM", nullable = false)
    @JsonProperty
    private BigDecimal transactionSum = BigDecimal.ZERO;

    @NotNull
    @Column(name = "TRANSACTION_CURRENCY", nullable = false)
    @JsonProperty
    private String transactionCurrency = "EUR";

    @NotNull
    @Column(name = "TRANSACTION_DEBIT", nullable = false)
    @JsonProperty
    private Integer transactionDebit = 1;

    @NotNull
    @Column(name = "TRANSACTION_CREDIT", nullable = false)
    @JsonProperty
    private Integer transactionCredit = 0;

    @Column(name = "TRANSACTION_DESCRIPTION")
    @JsonProperty
    private String transactionDescription;


    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "TRANSACTION_LOCK_START_TIME")
    @JsonProperty
    private Date transactionLockStartTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "TRANSACTION_LOCK_END_TIME")
    @JsonProperty
    private Date transactionLockEndTime;

    @NotNull
    //@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Column(name = "PARTICIPANT_SENDER_IBAN_ACCOUNT", nullable = false)
    @JsonProperty
    private String participantSenderIbanAccount;

    @NotNull
//    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Column(name = "PARTICIPANT_RECEIVER_IBAN_ACCOUNT", nullable = false)
    @JsonProperty
    private String participantReceiverIbanAccount;

    //@OneToOne=====================================================================

    @NotNull
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ORDER_ID", referencedColumnName = "id")
    @JsonProperty
    private Order order;

    //@OneToMany=====================================================================

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<TransactionHistory> transactionHistories;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Transaction)) return false;
        Transaction that = (Transaction) o;
        return Objects.equals(getId(), that.getId()) && Objects.equals(getTransactionStatus(), that.getTransactionStatus()) && Objects.equals(getTransactionSum(), that.getTransactionSum()) && Objects.equals(getTransactionCurrency(), that.getTransactionCurrency()) && Objects.equals(getTransactionDebit(), that.getTransactionDebit()) && Objects.equals(getTransactionCredit(), that.getTransactionCredit()) && Objects.equals(getTransactionDescription(), that.getTransactionDescription()) && Objects.equals(getTransactionLockStartTime(), that.getTransactionLockStartTime()) && Objects.equals(getTransactionLockEndTime(), that.getTransactionLockEndTime()) && Objects.equals(getParticipantSenderIbanAccount(), that.getParticipantSenderIbanAccount()) && Objects.equals(getParticipantReceiverIbanAccount(), that.getParticipantReceiverIbanAccount()) && Objects.equals(getOrder(), that.getOrder()) && Objects.equals(getTransactionHistories(), that.getTransactionHistories());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getTransactionStatus(), getTransactionSum(), getTransactionCurrency(), getTransactionDebit(), getTransactionCredit(), getTransactionDescription(), getTransactionLockStartTime(), getTransactionLockEndTime(), getParticipantSenderIbanAccount(), getParticipantReceiverIbanAccount(), getOrder(), getTransactionHistories());
    }

}
