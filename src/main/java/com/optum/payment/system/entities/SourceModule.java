package com.optum.payment.system.entities;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.optum.payment.system.entities.enums.DataSourceType;
import com.optum.payment.system.entities.enums.SourceModuleName;
import jakarta.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


import jakarta.validation.constraints.NotNull;
import java.util.Objects;

@SuppressWarnings("serial")
@Entity
@Table(name = "SOURCE_MODULES")
@Getter
@Setter
@ToString
@NoArgsConstructor
@JsonRootName(value = "sourceModule")
@JsonIgnoreProperties(ignoreUnknown = true)
public class SourceModule extends Audit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false, unique = true, insertable = false, updatable = false)
    private Long id;

    @JsonProperty
    @NotNull
    @Column(name = "SOURCE_MODULE_NAME", nullable = false, unique = true)
    private String sourceModuleName = SourceModuleName.SECURITY_MODULE.name();

    @JsonProperty
    @Column(name = "SOURCE_MODULE_DESCRIPTION", nullable = true)
    private String sourceModuleDescription;

    @JsonProperty
    @Column(name = "SOURCE_MODULE_ADDRESS", nullable = true)
    private String sourceModuleAddress;

    @JsonProperty
    @NotNull
    @Column(name = "SOURCE_MODULE_TYPE", nullable = true)
    private String sourceModuleType = DataSourceType.JAVA.name();

    //OneToOne=======================================================================================
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "SYSTEM_NAME", nullable = false)
    @JsonProperty
    private System system;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SourceModule)) return false;
        SourceModule that = (SourceModule) o;
        return Objects.equals(getId(), that.getId()) && Objects.equals(getSourceModuleName(), that.getSourceModuleName()) && Objects.equals(getSourceModuleDescription(), that.getSourceModuleDescription()) && Objects.equals(getSourceModuleAddress(), that.getSourceModuleAddress()) && Objects.equals(getSourceModuleType(), that.getSourceModuleType()) && Objects.equals(getSystem(), that.getSystem());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getSourceModuleName(), getSourceModuleDescription(), getSourceModuleAddress(), getSourceModuleType(), getSystem());
    }
}
