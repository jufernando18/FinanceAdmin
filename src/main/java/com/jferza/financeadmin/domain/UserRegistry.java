package com.jferza.financeadmin.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A UserRegistry.
 */
@Entity
@Table(name = "user_registry")
public class UserRegistry implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @NotNull
    @Size(min = 5)
    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @NotNull
    @Size(min = 5)
    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "title")
    private String title;

    @NotNull
    @Column(name = "token", nullable = false, unique = true)
    private String token;

    @NotNull
    @Column(name = "session", nullable = false)
    private Boolean session;

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

    public UserRegistry name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public UserRegistry username(String username) {
        this.username = username;
        return this;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public UserRegistry password(String password) {
        this.password = password;
        return this;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTitle() {
        return title;
    }

    public UserRegistry title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getToken() {
        return token;
    }

    public UserRegistry token(String token) {
        this.token = token;
        return this;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Boolean isSession() {
        return session;
    }

    public UserRegistry session(Boolean session) {
        this.session = session;
        return this;
    }

    public void setSession(Boolean session) {
        this.session = session;
    }

    public AuditRegistry getAudit() {
        return audit;
    }

    public UserRegistry audit(AuditRegistry auditRegistry) {
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
        if (!(o instanceof UserRegistry)) {
            return false;
        }
        return id != null && id.equals(((UserRegistry) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "UserRegistry{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", username='" + getUsername() + "'" +
            ", password='" + getPassword() + "'" +
            ", title='" + getTitle() + "'" +
            ", token='" + getToken() + "'" +
            ", session='" + isSession() + "'" +
            "}";
    }
}
