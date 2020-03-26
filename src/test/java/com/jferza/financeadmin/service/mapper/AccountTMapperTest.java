package com.jferza.financeadmin.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class AccountTMapperTest {

    private AccountTMapper accountTMapper;

    @BeforeEach
    public void setUp() {
        accountTMapper = new AccountTMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(accountTMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(accountTMapper.fromId(null)).isNull();
    }
}
