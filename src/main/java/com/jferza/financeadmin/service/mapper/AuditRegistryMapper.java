package com.jferza.financeadmin.service.mapper;


import com.jferza.financeadmin.domain.*;
import com.jferza.financeadmin.service.dto.AuditRegistryDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link AuditRegistry} and its DTO {@link AuditRegistryDTO}.
 */
@Mapper(componentModel = "spring", uses = {UserRegistryMapper.class})
public interface AuditRegistryMapper extends EntityMapper<AuditRegistryDTO, AuditRegistry> {

    @Mapping(source = "createdBy.id", target = "createdById")
    @Mapping(source = "lastModifiedBy.id", target = "lastModifiedById")
    AuditRegistryDTO toDto(AuditRegistry auditRegistry);

    @Mapping(source = "createdById", target = "createdBy")
    @Mapping(source = "lastModifiedById", target = "lastModifiedBy")
    AuditRegistry toEntity(AuditRegistryDTO auditRegistryDTO);

    default AuditRegistry fromId(Long id) {
        if (id == null) {
            return null;
        }
        AuditRegistry auditRegistry = new AuditRegistry();
        auditRegistry.setId(id);
        return auditRegistry;
    }
}
