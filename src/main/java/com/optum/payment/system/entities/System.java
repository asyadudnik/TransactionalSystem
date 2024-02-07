package com.optum.payment.system.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.optum.payment.system.entities.enums.SystemName;
import jakarta.persistence.*;

import lombok.*;

import jakarta.validation.constraints.NotNull;
import java.util.List;


@SuppressWarnings("serial")
@Entity
@Table(name = "SYSTEMS")
@Data
@EqualsAndHashCode(callSuper = false)
//@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
/*System in the payment system, in which it is doing transaction*/
public class System extends Audit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false, unique = true, insertable = false, updatable = false)
    private Long id;

    @NotNull(message = "Please enter system name")
    @Column(name = "SYSTEM_NAME", nullable = false, unique = true)
    private String systemName= SystemName.SECURITY.name();

    @NotNull
    @Column(name = "SYSTEM_DB_CONNECTION_STRING", nullable = true)
    private String systemDbConnectionString;

    @Column(name = "SYSTEM_DESCRIPTION")
    private String systemDescription= SystemName.SECURITY.name();


    //OneToMany=======================================================================================
    @NotNull
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "USER_ID", referencedColumnName = "id")
    @JsonProperty
    private User user;

    //OneToMany=======================================================================================
    @NotNull
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "id", orphanRemoval = true)
    private List<SystemEvent> systemEvents;

    //OneToMany=======================================================================================
    @NotNull
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "id", orphanRemoval = true)
    private List<Order> orders;

    //OneToOne=======================================================================================

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "SOURCE_MODULE_ID", referencedColumnName = "id", nullable = false)
    @JsonProperty
    private SourceModule sourceModule;

}
