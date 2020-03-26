package com.jferza.financeadmin.service.mapper;


import com.jferza.financeadmin.domain.*;
import com.jferza.financeadmin.service.dto.AccountTDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link AccountT} and its DTO {@link AccountTDTO}.
 */
@Mapper(componentModel = "spring", uses = {AuditRegistryMapper.class})
public interface AccountTMapper extends EntityMapper<AccountTDTO, AccountT> {

    @Mapping(source = "audit.id", target = "auditId")
    AccountTDTO toDto(AccountT accountT);

    @Mapping(source = "auditId", target = "audit")
    AccountT toEntity(AccountTDTO accountTDTO);

    default AccountT fromId(Long id) {
        if (id == null) {
            return null;
        }
        AccountT accountT = new AccountT();
        accountT.setId(id);
        return accountT;
    }
}
