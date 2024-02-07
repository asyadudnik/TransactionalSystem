package com.optum.payment.system.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonRootName;
import jakarta.persistence.*;

import lombok.*;

import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "ORDERS")
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonRootName(value = "order")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Order extends Audit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, optional = false, mappedBy = "order")
    @JoinColumn(name = "ID")
    private Transaction transaction;


    //@ManyToOne=
    // ========================================================================
    @NotNull

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "SYSTEM_ID", referencedColumnName = "id")
    private System system;


    //@ManyToOne=========================================================================
    @NotNull
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "SYSTEM_EVENT_ID", referencedColumnName = "id")
    private SystemEvent systemEvent;

}