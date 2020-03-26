package com.jferza.financeadmin.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

import com.jferza.financeadmin.domain.enumeration.TransactionType;

/**
 * A AccountT.
 */
@Entity
@Table(name = "account_t")
public class AccountT implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "description")
    private String description;

    
    @Column(name = "code", unique = true)
    private Integer code;

    @Enumerated(EnumType.STRING)
    @Column(name = "increase_when")
    private TransactionType increaseWhen;

    @Enumerated(EnumType.STRING)
    @Column(name = "decrease_when")
    private TransactionType decreaseWhen;

    @NotNull
    @Column(name = "balance", nullable = false)
    private Integer balance;

    @OneToOne
    @JoinColumn(unique = true)
    private AuditRegistry audit;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public AccountT name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public AccountT description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getCode() {
        return code;
    }

    public AccountT code(Integer code) {
        this.code = code;
        return this;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public TransactionType getIncreaseWhen() {
        return increaseWhen;
    }

    public AccountT increaseWhen(TransactionType increaseWhen) {
        this.increaseWhen = increaseWhen;
        return this;
    }

    public void setIncreaseWhen(TransactionType increaseWhen) {
        this.increaseWhen = increaseWhen;
    }

    public TransactionType getDecreaseWhen() {
        return decreaseWhen;
    }

    public AccountT decreaseWhen(TransactionType decreaseWhen) {
        this.decreaseWhen = decreaseWhen;
        return this;
    }

    public void setDecreaseWhen(TransactionType decreaseWhen) {
        this.decreaseWhen = decreaseWhen;
    }

    public Integer getBalance() {
        return balance;
    }

    public AccountT balance(Integer balance) {
        this.balance = balance;
        return this;
    }

    public void setBalance(Integer balance) {
        this.balance = balance;
    }

    public AuditRegistry getAudit() {
        return audit;
    }

    public AccountT audit(AuditRegistry auditRegistry) {
        this.audit = auditRegistry;
        return this;
    }

    public void setAudit(AuditRegistry auditRegistry) {
        this.audit = auditRegistry;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AccountT)) {
            return false;
        }
        return id != null && id.equals(((AccountT) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "AccountT{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", code=" + getCode() +
            ", increaseWhen='" + getIncreaseWhen() + "'" +
            ", decreaseWhen='" + getDecreaseWhen() + "'" +
            ", balance=" + getBalance() +
            "}";
    }
}
