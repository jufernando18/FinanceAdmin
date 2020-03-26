package com.jferza.financeadmin.repository;

import com.jferza.financeadmin.domain.AuditRegistry;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the AuditRegistry entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AuditRegistryRepository extends JpaRepository<AuditRegistry, Long> {

}
