package com.jferza.financeadmin.service.mapper;


import com.jferza.financeadmin.domain.*;
import com.jferza.financeadmin.service.dto.UserRegistryDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link UserRegistry} and its DTO {@link UserRegistryDTO}.
 */
@Mapper(componentModel = "spring", uses = {AuditRegistryMapper.class})
public interface UserRegistryMapper extends EntityMapper<UserRegistryDTO, UserRegistry> {

    @Mapping(source = "audit.id", target = "auditId")
    UserRegistryDTO toDto(UserRegistry userRegistry);

    @Mapping(source = "auditId", target = "audit")
    UserRegistry toEntity(UserRegistryDTO userRegistryDTO);

    default UserRegistry fromId(Long id) {
        if (id == null) {
            return null;
        }
        UserRegistry userRegistry = new UserRegistry();
        userRegistry.setId(id);
        return userRegistry;
    }
}
