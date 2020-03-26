package com.jferza.financeadmin.service.dto;

import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import com.jferza.financeadmin.domain.enumeration.State;

/**
 * A DTO for the {@link com.jferza.financeadmin.domain.AuditRegistry} entity.
 */
public class AuditRegistryDTO implements Serializable {

    private Long id;

    @NotNull
    private State state;

    @NotNull
    private Long createdBy;

    @NotNull
    private ZonedDateTime createdDate;

    private Long lastModifiedBy;

    private ZonedDateTime lastModifiedDate;


    private Long createdById;

    private Long lastModifiedById;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public ZonedDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(ZonedDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public Long getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(Long lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public ZonedDateTime getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(ZonedDateTime lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public Long getCreatedById() {
        return createdById;
    }

    public void setCreatedById(Long userRegistryId) {
        this.createdById = userRegistryId;
    }

    public Long getLastModifiedById() {
        return lastModifiedById;
    }

    public void setLastModifiedById(Long userRegistryId) {
        this.lastModifiedById = userRegistryId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AuditRegistryDTO auditRegistryDTO = (AuditRegistryDTO) o;
        if (auditRegistryDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), auditRegistryDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AuditRegistryDTO{" +
            "id=" + getId() +
            ", state='" + getState() + "'" +
            ", createdBy=" + getCreatedBy() +
            ", createdDate='" + getCreatedDate() + "'" +
            ", lastModifiedBy=" + getLastModifiedBy() +
            ", lastModifiedDate='" + getLastModifiedDate() + "'" +
            ", createdById=" + getCreatedById() +
            ", lastModifiedById=" + getLastModifiedById() +
            "}";
    }
}
