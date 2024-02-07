package com.optum.payment.system.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import jakarta.persistence.*;

import jakarta.validation.constraints.NotNull;
import lombok.*;


import java.util.Set;


@Entity
@Table(name = "BANKS")
@Data
@ToString
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonRootName(value = "bank")
/*Bank which is supporting of user of the payment system*/
@JsonIgnoreProperties(ignoreUnknown = true)
public class Bank extends Audit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false, unique = true, insertable = false, updatable = false)
    private Long id;

    @JsonProperty
    @NotNull(message = "Please enter name")
    @Column(name = "BANK_NAME", nullable = false, unique = true)
    private String bankName;

    @JsonProperty
    @NotNull(message = "Please enter IBAN account")
    @Column(name = "BANK_IBAN_ACCOUNT", nullable = false, unique = true)
    private String bankIbanAccount;


    @JsonProperty
    @NotNull(message = "Please enter name")
    @Column(name = "BANK_BIC", nullable = false, unique = true)
    private String bankBIC;

    //OneToOne=======================================================================================

    @NotNull
    @OneToMany(cascade = CascadeType.ALL,fetch=FetchType.LAZY, mappedBy = "bank")
    private Set<Participant> participants;

}
