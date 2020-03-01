package com.jferza.financeadmin.repository;

import com.jferza.financeadmin.domain.UserRegistry;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the UserRegistry entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UserRegistryRepository extends JpaRepository<UserRegistry, Long> {

}
