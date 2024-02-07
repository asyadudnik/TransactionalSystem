package com.optum.payment.system.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.optum.payment.system.entities.enums.ActiveStatus;
import jakarta.persistence.*;

import lombok.*;

import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Objects;

@SuppressWarnings("serial")
@Entity
@Table(name = "TRANSACTIONS_HISTORY")
@Getter
@Setter
@ToString
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonRootName(value = "transactionHistory")
public class TransactionHistory extends Audit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false, unique = true, insertable = false, updatable = false)
    private Long id;

    @NotNull
    @Column(name = "TRANSACTION_STATUS", nullable = false)
    @JsonProperty
    private String transactionStatus = ActiveStatus.ACTIVE.name();

    @NotNull
    @Column(name = "TRANSACTION_SUM", nullable = false)
    @JsonProperty
    private BigDecimal transactionSum = BigDecimal.ZERO;

    @NotNull
    @Column(name = "TRANSACTION_CURRENCY", nullable = false)
    @JsonProperty
    private String transactionCurrency = "EUR";


    //@ManyToOne=======================================================================================
    @NotNull
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "TRANSACTION_ID", nullable = false)
    @JsonProperty
    private Transaction transaction;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TransactionHistory)) return false;
        TransactionHistory that = (TransactionHistory) o;
        return Objects.equals(getId(), that.getId()) && Objects.equals(getTransactionStatus(), that.getTransactionStatus()) && Objects.equals(getTransactionSum(), that.getTransactionSum()) && Objects.equals(getTransactionCurrency(), that.getTransactionCurrency()) && Objects.equals(getTransaction(), that.getTransaction());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getTransactionStatus(), getTransactionSum(), getTransactionCurrency(), getTransaction());
    }
}
