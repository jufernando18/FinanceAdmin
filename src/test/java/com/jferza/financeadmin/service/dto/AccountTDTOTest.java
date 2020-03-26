package com.jferza.financeadmin.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.jferza.financeadmin.web.rest.TestUtil;

public class AccountTDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AccountTDTO.class);
        AccountTDTO accountTDTO1 = new AccountTDTO();
        accountTDTO1.setId(1L);
        AccountTDTO accountTDTO2 = new AccountTDTO();
        assertThat(accountTDTO1).isNotEqualTo(accountTDTO2);
        accountTDTO2.setId(accountTDTO1.getId());
        assertThat(accountTDTO1).isEqualTo(accountTDTO2);
        accountTDTO2.setId(2L);
        assertThat(accountTDTO1).isNotEqualTo(accountTDTO2);
        accountTDTO1.setId(null);
        assertThat(accountTDTO1).isNotEqualTo(accountTDTO2);
    }
}
