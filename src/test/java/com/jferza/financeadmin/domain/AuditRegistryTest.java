package com.jferza.financeadmin.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.jferza.financeadmin.web.rest.TestUtil;

public class AuditRegistryTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AuditRegistry.class);
        AuditRegistry auditRegistry1 = new AuditRegistry();
        auditRegistry1.setId(1L);
        AuditRegistry auditRegistry2 = new AuditRegistry();
        auditRegistry2.setId(auditRegistry1.getId());
        assertThat(auditRegistry1).isEqualTo(auditRegistry2);
        auditRegistry2.setId(2L);
        assertThat(auditRegistry1).isNotEqualTo(auditRegistry2);
        auditRegistry1.setId(null);
        assertThat(auditRegistry1).isNotEqualTo(auditRegistry2);
    }
}
