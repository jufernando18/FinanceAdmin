import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FinanceAdminSharedModule } from 'app/shared/shared.module';
import { UserRegistryComponent } from './user-registry.component';
import { UserRegistryDetailComponent } from './user-registry-detail.component';
import { UserRegistryUpdateComponent } from './user-registry-update.component';
import { UserRegistryDeleteDialogComponent } from './user-registry-delete-dialog.component';
import { userRegistryRoute } from './user-registry.route';

@NgModule({
  imports: [FinanceAdminSharedModule, RouterModule.forChild(userRegistryRoute)],
  declarations: [UserRegistryComponent, UserRegistryDetailComponent, UserRegistryUpdateComponent, UserRegistryDeleteDialogComponent],
  entryComponents: [UserRegistryDeleteDialogComponent]
})
export class FinanceAdminUserRegistryModule {}
