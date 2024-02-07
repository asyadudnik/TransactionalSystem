package com.optum.payment.system.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.optum.payment.system.entities.enums.ActiveStatus;
import com.optum.payment.system.entities.enums.SystemEventName;
import jakarta.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.NotNull;
import java.util.Date;
import java.util.Objects;
import java.util.Set;

@SuppressWarnings("serial")
@Entity
@Table(name = "SYSTEM_EVENTS")
@Getter
@Setter
@ToString
@NoArgsConstructor
@JsonRootName(value = "systemEvent")
@JsonIgnoreProperties(ignoreUnknown = true)
/*System Event in the payment system, in which it is doing transaction*/
public class SystemEvent extends Audit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false, unique = true, insertable = false, updatable = false)
    private Long id;

    @NotNull(message = "Please enter system event name")
    @Column(name = "SYSTEM_EVENT_NAME", nullable = false, unique = true)
    @JsonProperty
    private String systemEventName = SystemEventName.SHIFT_OPEN.name();


    @Column(name = "SYSTEM_EVENT_DESCRIPTION", nullable = true)
    @JsonProperty
    private String systemEventDescription = systemEventName;

    @NotNull
    @Column(name = "SYSTEM_EVENT_PRIORITY", nullable = false)
    @JsonProperty
    private Integer systemEventPriority = 0;

    @NotNull
    @Column(name = "SYSTEM_EVENT_STATUS", nullable = false)
    @JsonProperty
    private String systemEventStatus = ActiveStatus.ACTIVE.name();

    /*if system event has status Locked*/
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "SYSTEM_EVENT_LOCK_START_TIME", nullable = true)
    @JsonProperty
    private Date systemEventLockStartTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "SYSTEM_EVENT_LOCK_END_TIME", nullable = true)
    @JsonProperty
    private Date systemEventLockEndTime;

    //manyToOne=======================================================================================

    @NotNull
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "SYSTEM_ID", referencedColumnName = "id", nullable = false)
    @JsonProperty
    private System system;


    //OneToOne=======================================================================================

    @NotNull
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Order> orders;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SystemEvent)) return false;
        SystemEvent that = (SystemEvent) o;
        return Objects.equals(getId(), that.getId()) && Objects.equals(getSystemEventName(), that.getSystemEventName()) && Objects.equals(getSystemEventDescription(), that.getSystemEventDescription()) && Objects.equals(getSystemEventPriority(), that.getSystemEventPriority()) && Objects.equals(getSystemEventStatus(), that.getSystemEventStatus()) && Objects.equals(getSystemEventLockStartTime(), that.getSystemEventLockStartTime()) && Objects.equals(getSystemEventLockEndTime(), that.getSystemEventLockEndTime()) && Objects.equals(getSystem(), that.getSystem()) && Objects.equals(getOrders(), that.getOrders());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getSystemEventName(), getSystemEventDescription(), getSystemEventPriority(), getSystemEventStatus(), getSystemEventLockStartTime(), getSystemEventLockEndTime(), getSystem(), getOrders());
    }
}
