package com.optum.payment.system.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.optum.payment.system.entities.enums.RoleName;
import jakarta.persistence.*;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@SuppressWarnings("serial")
@Entity
@Table(name = "ROLES")
@Data
@EqualsAndHashCode(callSuper = false)
//@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
/*Role which have at the moment user of the payment system*/
public class Role extends Audit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false, unique = true, insertable = false, updatable = false)
    private Long id;

    @NotNull(message = "Please enter role name")
    @Column(name = "ROLE_NAME", nullable = false)
    private String roleName = RoleName.ADMIN.name();

    @Column(name = "ROLE_DESCRIPTION", nullable = true)
    private String roleDescription = roleName;


    //ManyToOne=======================================================================================
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "USER_ID", referencedColumnName = "id", nullable = false)
    @JsonProperty
    private User user;

}
