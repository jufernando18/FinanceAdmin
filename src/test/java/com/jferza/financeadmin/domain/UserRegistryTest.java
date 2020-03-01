package com.jferza.financeadmin.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.jferza.financeadmin.web.rest.TestUtil;

public class UserRegistryTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserRegistry.class);
        UserRegistry userRegistry1 = new UserRegistry();
        userRegistry1.setId(1L);
        UserRegistry userRegistry2 = new UserRegistry();
        userRegistry2.setId(userRegistry1.getId());
        assertThat(userRegistry1).isEqualTo(userRegistry2);
        userRegistry2.setId(2L);
        assertThat(userRegistry1).isNotEqualTo(userRegistry2);
        userRegistry1.setId(null);
        assertThat(userRegistry1).isNotEqualTo(userRegistry2);
    }
}
