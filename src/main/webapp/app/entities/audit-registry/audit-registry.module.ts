import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FinanceAdminSharedModule } from 'app/shared/shared.module';
import { AuditRegistryComponent } from './audit-registry.component';
import { AuditRegistryDetailComponent } from './audit-registry-detail.component';
import { AuditRegistryUpdateComponent } from './audit-registry-update.component';
import { AuditRegistryDeleteDialogComponent } from './audit-registry-delete-dialog.component';
import { auditRegistryRoute } from './audit-registry.route';

@NgModule({
  imports: [FinanceAdminSharedModule, RouterModule.forChild(auditRegistryRoute)],
  declarations: [AuditRegistryComponent, AuditRegistryDetailComponent, AuditRegistryUpdateComponent, AuditRegistryDeleteDialogComponent],
  entryComponents: [AuditRegistryDeleteDialogComponent]
})
export class FinanceAdminAuditRegistryModule {}
