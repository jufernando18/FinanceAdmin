import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FinanceAdminSharedModule } from 'app/shared/shared.module';
import { AccountTComponent } from './account-t.component';
import { AccountTDetailComponent } from './account-t-detail.component';
import { AccountTUpdateComponent } from './account-t-update.component';
import { AccountTDeleteDialogComponent } from './account-t-delete-dialog.component';
import { accountTRoute } from './account-t.route';

@NgModule({
  imports: [FinanceAdminSharedModule, RouterModule.forChild(accountTRoute)],
  declarations: [AccountTComponent, AccountTDetailComponent, AccountTUpdateComponent, AccountTDeleteDialogComponent],
  entryComponents: [AccountTDeleteDialogComponent]
})
export class FinanceAdminAccountTModule {}
