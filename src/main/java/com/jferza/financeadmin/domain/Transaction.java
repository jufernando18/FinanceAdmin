package com.jferza.financeadmin.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.time.ZonedDateTime;

import com.jferza.financeadmin.domain.enumeration.TransactionType;

/**
 * A Transaction.
 */
@Entity
@Table(name = "transaction")
public class Transaction implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "concept")
    private String concept;

    @Column(name = "account_t")
    private Long accountT;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private TransactionType type;

    @Column(name = "value")
    private Integer value;

    @Column(name = "date")
    private ZonedDateTime date;

    @Column(name = "balance")
    private Integer balance;

    @OneToOne
    @JoinColumn(unique = true)
    private AuditRegistry audit;

    @ManyToOne
    @JsonIgnoreProperties("transactions")
    private AccountT accountT;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getConcept() {
        return concept;
    }

    public Transaction concept(String concept) {
        this.concept = concept;
        return this;
    }

    public void setConcept(String concept) {
        this.concept = concept;
    }

    public Long getAccountT() {
        return accountT;
    }

    public Transaction accountT(Long accountT) {
        this.accountT = accountT;
        return this;
    }

    public void setAccountT(Long accountT) {
        this.accountT = accountT;
    }

    public TransactionType getType() {
        return type;
    }

    public Transaction type(TransactionType type) {
        this.type = type;
        return this;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public Integer getValue() {
        return value;
    }

    public Transaction value(Integer value) {
        this.value = value;
        return this;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public ZonedDateTime getDate() {
        return date;
    }

    public Transaction date(ZonedDateTime date) {
        this.date = date;
        return this;
    }

    public void setDate(ZonedDateTime date) {
        this.date = date;
    }

    public Integer getBalance() {
        return balance;
    }

    public Transaction balance(Integer balance) {
        this.balance = balance;
        return this;
    }

    public void setBalance(Integer balance) {
        this.balance = balance;
    }

    public AuditRegistry getAudit() {
        return audit;
    }

    public Transaction audit(AuditRegistry auditRegistry) {
        this.audit = auditRegistry;
        return this;
    }

    public void setAudit(AuditRegistry auditRegistry) {
        this.audit = auditRegistry;
    }

    public AccountT getAccountT() {
        return accountT;
    }

    public Transaction accountT(AccountT accountT) {
        this.accountT = accountT;
        return this;
    }

    public void setAccountT(AccountT accountT) {
        this.accountT = accountT;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Transaction)) {
            return false;
        }
        return id != null && id.equals(((Transaction) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Transaction{" +
            "id=" + getId() +
            ", concept='" + getConcept() + "'" +
            ", accountT=" + getAccountT() +
            ", type='" + getType() + "'" +
            ", value=" + getValue() +
            ", date='" + getDate() + "'" +
            ", balance=" + getBalance() +
            "}";
    }
}
