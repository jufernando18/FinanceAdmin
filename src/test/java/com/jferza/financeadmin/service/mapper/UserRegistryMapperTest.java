package com.jferza.financeadmin.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class UserRegistryMapperTest {

    private UserRegistryMapper userRegistryMapper;

    @BeforeEach
    public void setUp() {
        userRegistryMapper = new UserRegistryMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(userRegistryMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(userRegistryMapper.fromId(null)).isNull();
    }
}
