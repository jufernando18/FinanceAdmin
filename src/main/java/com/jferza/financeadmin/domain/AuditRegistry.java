package com.jferza.financeadmin.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.ZonedDateTime;

import com.jferza.financeadmin.domain.enumeration.State;

/**
 * A AuditRegistry.
 */
@Entity
@Table(name = "audit_registry")
public class AuditRegistry implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "state", nullable = false)
    private State state;

    @NotNull
    @Column(name = "created_by", nullable = false)
    private Long createdBy;

    @NotNull
    @Column(name = "created_date", nullable = false)
    private ZonedDateTime createdDate;

    @Column(name = "last_modified_by")
    private Long lastModifiedBy;

    @Column(name = "last_modified_date")
    private ZonedDateTime lastModifiedDate;

    @ManyToOne
    @JsonIgnoreProperties("auditRegistries")
    private UserRegistry createdBy;

    @ManyToOne
    @JsonIgnoreProperties("auditRegistries")
    private UserRegistry lastModifiedBy;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public State getState() {
        return state;
    }

    public AuditRegistry state(State state) {
        this.state = state;
        return this;
    }

    public void setState(State state) {
        this.state = state;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public AuditRegistry createdBy(Long createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public ZonedDateTime getCreatedDate() {
        return createdDate;
    }

    public AuditRegistry createdDate(ZonedDateTime createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public void setCreatedDate(ZonedDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public Long getLastModifiedBy() {
        return lastModifiedBy;
    }

    public AuditRegistry lastModifiedBy(Long lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
        return this;
    }

    public void setLastModifiedBy(Long lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public ZonedDateTime getLastModifiedDate() {
        return lastModifiedDate;
    }

    public AuditRegistry lastModifiedDate(ZonedDateTime lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
        return this;
    }

    public void setLastModifiedDate(ZonedDateTime lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public UserRegistry getCreatedBy() {
        return createdBy;
    }

    public AuditRegistry createdBy(UserRegistry userRegistry) {
        this.createdBy = userRegistry;
        return this;
    }

    public void setCreatedBy(UserRegistry userRegistry) {
        this.createdBy = userRegistry;
    }

    public UserRegistry getLastModifiedBy() {
        return lastModifiedBy;
    }

    public AuditRegistry lastModifiedBy(UserRegistry userRegistry) {
        this.lastModifiedBy = userRegistry;
        return this;
    }

    public void setLastModifiedBy(UserRegistry userRegistry) {
        this.lastModifiedBy = userRegistry;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AuditRegistry)) {
            return false;
        }
        return id != null && id.equals(((AuditRegistry) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "AuditRegistry{" +
            "id=" + getId() +
            ", state='" + getState() + "'" +
            ", createdBy=" + getCreatedBy() +
            ", createdDate='" + getCreatedDate() + "'" +
            ", lastModifiedBy=" + getLastModifiedBy() +
            ", lastModifiedDate='" + getLastModifiedDate() + "'" +
            "}";
    }
}
