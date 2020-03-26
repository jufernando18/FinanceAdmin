package com.jferza.financeadmin.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.jferza.financeadmin.web.rest.TestUtil;

public class AuditRegistryDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AuditRegistryDTO.class);
        AuditRegistryDTO auditRegistryDTO1 = new AuditRegistryDTO();
        auditRegistryDTO1.setId(1L);
        AuditRegistryDTO auditRegistryDTO2 = new AuditRegistryDTO();
        assertThat(auditRegistryDTO1).isNotEqualTo(auditRegistryDTO2);
        auditRegistryDTO2.setId(auditRegistryDTO1.getId());
        assertThat(auditRegistryDTO1).isEqualTo(auditRegistryDTO2);
        auditRegistryDTO2.setId(2L);
        assertThat(auditRegistryDTO1).isNotEqualTo(auditRegistryDTO2);
        auditRegistryDTO1.setId(null);
        assertThat(auditRegistryDTO1).isNotEqualTo(auditRegistryDTO2);
    }
}
