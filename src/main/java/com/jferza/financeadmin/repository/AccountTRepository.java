package com.jferza.financeadmin.repository;

import com.jferza.financeadmin.domain.AccountT;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the AccountT entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AccountTRepository extends JpaRepository<AccountT, Long> {

}
