package com.jferza.financeadmin.service.dto;

import java.time.ZonedDateTime;
import java.io.Serializable;
import java.util.Objects;
import com.jferza.financeadmin.domain.enumeration.TransactionType;

/**
 * A DTO for the {@link com.jferza.financeadmin.domain.Transaction} entity.
 */
public class TransactionDTO implements Serializable {

    private Long id;

    private String concept;

    private Long accountT;

    private TransactionType type;

    private Integer value;

    private ZonedDateTime date;

    private Integer balance;


    private Long auditId;

    private Long accountTId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getConcept() {
        return concept;
    }

    public void setConcept(String concept) {
        this.concept = concept;
    }

    public Long getAccountT() {
        return accountT;
    }

    public void setAccountT(Long accountT) {
        this.accountT = accountT;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public ZonedDateTime getDate() {
        return date;
    }

    public void setDate(ZonedDateTime date) {
        this.date = date;
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

    public Long getAccountTId() {
        return accountTId;
    }

    public void setAccountTId(Long accountTId) {
        this.accountTId = accountTId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TransactionDTO transactionDTO = (TransactionDTO) o;
        if (transactionDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), transactionDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TransactionDTO{" +
            "id=" + getId() +
            ", concept='" + getConcept() + "'" +
            ", accountT=" + getAccountT() +
            ", type='" + getType() + "'" +
            ", value=" + getValue() +
            ", date='" + getDate() + "'" +
            ", balance=" + getBalance() +
            ", auditId=" + getAuditId() +
            ", accountTId=" + getAccountTId() +
            "}";
    }
}
