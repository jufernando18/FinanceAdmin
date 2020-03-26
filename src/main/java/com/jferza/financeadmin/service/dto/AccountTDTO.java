package com.jferza.financeadmin.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import com.jferza.financeadmin.domain.enumeration.TransactionType;

/**
 * A DTO for the {@link com.jferza.financeadmin.domain.AccountT} entity.
 */
public class AccountTDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    private String description;

    
    private Integer code;

    private TransactionType increaseWhen;

    private TransactionType decreaseWhen;

    @NotNull
    private Integer balance;


    private Long auditId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public TransactionType getIncreaseWhen() {
        return increaseWhen;
    }

    public void setIncreaseWhen(TransactionType increaseWhen) {
        this.increaseWhen = increaseWhen;
    }

    public TransactionType getDecreaseWhen() {
        return decreaseWhen;
    }

    public void setDecreaseWhen(TransactionType decreaseWhen) {
        this.decreaseWhen = decreaseWhen;
    }

    public Integer getBalance() {
        return balance;
    }

    public void setBalance(Integer balance) {
        this.balance = balance;
    }

    public Long getAuditId() {
        return auditId;
    }

    public void setAuditId(Long auditRegistryId) {
        this.auditId = auditRegistryId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AccountTDTO accountTDTO = (AccountTDTO) o;
        if (accountTDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), accountTDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AccountTDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", code=" + getCode() +
            ", increaseWhen='" + getIncreaseWhen() + "'" +
            ", decreaseWhen='" + getDecreaseWhen() + "'" +
            ", balance=" + getBalance() +
            ", auditId=" + getAuditId() +
            "}";
    }
}
