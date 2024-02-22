package com.optum.payment.system.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.optum.payment.system.entities.enums.Gender;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import java.util.Set;


@Entity
@Table(name = "USERS")
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonRootName(value = "user")
/*User in the payment system, active transaction's sender*/
public class User extends Audit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false, unique = true, insertable = false, updatable = false)
    private Long id;

    public User(@JsonProperty("login") String login,
                @JsonProperty("password") String password) {
        this.login = login;
        this.password = password;
    }


    //@NotNull(message = "Please enter first name")
    @Column(name = "FIRST_NAME")
    //@NotBlank(message = "User first name cannot be blank")
    private String firstName;

    //@NotNull(message = "Please enter last name")
    @Column(name = "LAST_NAME")
    //@NotBlank(message = "User last name cannot be blank")
    private String lastName;

    //@NotNull
    @Column(name = "FULL_NAME")
    //@NotBlank(message = "User full name cannot be blank")
    private String fullName;

    //key

    //@NotNull(message = "Please enter email")
    @Email(message = "Email should be valid")
    @Column(name = "EMAIL")
    private String email;

    //key
    @Column(name = "LOGIN", nullable = false, unique = true)
    @NotBlank(message = "Login cannot be blank")
    private String login;

    //key
    @Length(min = 5, max = 142, message = "Password should be grater than  5 characters")
    @Column(name = "PASSWORD", nullable = false)
    private String password;


    @Past
    @Column(name = "BIRTH_DATE")
    private java.util.Date birthDate;

    @Pattern(regexp = "^\\+?[0-9. ()-]{7,25}$", message = "Phone number")
    @Size(max = 25)
    @Column(name = "PHONE_NUMBER")
    private String phoneNumber;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "GENDER")
    private Gender gender;

    @Column(name = "PICTURE")
    private String picture;

    @Column(name = "NOTES", length = 200)
    private String notes;


    //ManyToOne=======================================================================================
    //@NotNull(message = "Please enter system name")
    @OneToMany(cascade = CascadeType.ALL,fetch=FetchType.LAZY)
    @JoinColumn(name = "SYSTEM_ID", referencedColumnName = "id", nullable = true)
    private Set<System> systems;

    //OneToMany=======================================================================================

    //@NotNull(message = "Please enter role name")
    @OneToMany(cascade = CascadeType.ALL,fetch=FetchType.LAZY)
    @JoinColumn(name = "ROLE_ID", referencedColumnName = "id")
/*
    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "id"))
    @Enumerated(EnumType.STRING)
*/
    private Set<Role> roles;
}
