package com.jferza.financeadmin.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.jferza.financeadmin.domain.UserRegistry} entity.
 */
public class UserRegistryDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    @NotNull
    @Size(min = 5)
    private String username;

    @NotNull
    @Size(min = 5)
    private String password;

    private String title;

    @NotNull
    private String token;

    @NotNull
    private Boolean session;


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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Boolean isSession() {
        return session;
    }

    public void setSession(Boolean session) {
        this.session = session;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        UserRegistryDTO userRegistryDTO = (UserRegistryDTO) o;
        if (userRegistryDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), userRegistryDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "UserRegistryDTO{" +
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
