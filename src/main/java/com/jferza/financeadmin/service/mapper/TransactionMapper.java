package com.jferza.financeadmin.service.mapper;


import com.jferza.financeadmin.domain.*;
import com.jferza.financeadmin.service.dto.TransactionDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Transaction} and its DTO {@link TransactionDTO}.
 */
@Mapper(componentModel = "spring", uses = {AuditRegistryMapper.class, AccountTMapper.class})
public interface TransactionMapper extends EntityMapper<TransactionDTO, Transaction> {

    @Mapping(source = "audit.id", target = "auditId")
    @Mapping(source = "accountT.id", target = "accountTId")
    TransactionDTO toDto(Transaction transaction);

    @Mapping(source = "auditId", target = "audit")
    @Mapping(source = "accountTId", target = "accountT")
    Transaction toEntity(TransactionDTO transactionDTO);

    default Transaction fromId(Long id) {
        if (id == null) {
            return null;
        }
        Transaction transaction = new Transaction();
        transaction.setId(id);
        return transaction;
    }
}
