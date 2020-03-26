package com.jferza.financeadmin.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class AuditRegistryMapperTest {

    private AuditRegistryMapper auditRegistryMapper;

    @BeforeEach
    public void setUp() {
        auditRegistryMapper = new AuditRegistryMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(auditRegistryMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(auditRegistryMapper.fromId(null)).isNull();
    }
}
