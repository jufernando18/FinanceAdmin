package com.jferza.financeadmin.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.jferza.financeadmin.web.rest.TestUtil;

public class UserRegistryDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserRegistryDTO.class);
        UserRegistryDTO userRegistryDTO1 = new UserRegistryDTO();
        userRegistryDTO1.setId(1L);
        UserRegistryDTO userRegistryDTO2 = new UserRegistryDTO();
        assertThat(userRegistryDTO1).isNotEqualTo(userRegistryDTO2);
        userRegistryDTO2.setId(userRegistryDTO1.getId());
        assertThat(userRegistryDTO1).isEqualTo(userRegistryDTO2);
        userRegistryDTO2.setId(2L);
        assertThat(userRegistryDTO1).isNotEqualTo(userRegistryDTO2);
        userRegistryDTO1.setId(null);
        assertThat(userRegistryDTO1).isNotEqualTo(userRegistryDTO2);
    }
}
