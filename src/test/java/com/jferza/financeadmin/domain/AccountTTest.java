package com.jferza.financeadmin.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.jferza.financeadmin.web.rest.TestUtil;

public class AccountTTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AccountT.class);
        AccountT accountT1 = new AccountT();
        accountT1.setId(1L);
        AccountT accountT2 = new AccountT();
        accountT2.setId(accountT1.getId());
        assertThat(accountT1).isEqualTo(accountT2);
        accountT2.setId(2L);
        assertThat(accountT1).isNotEqualTo(accountT2);
        accountT1.setId(null);
        assertThat(accountT1).isNotEqualTo(accountT2);
    }
}
