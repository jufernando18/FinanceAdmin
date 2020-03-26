import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'user-registry',
        loadChildren: () => import('./user-registry/user-registry.module').then(m => m.FinanceAdminUserRegistryModule)
      },
      {
        path: 'account-t',
        loadChildren: () => import('./account-t/account-t.module').then(m => m.FinanceAdminAccountTModule)
      },
      {
        path: 'transaction',
        loadChildren: () => import('./transaction/transaction.module').then(m => m.FinanceAdminTransactionModule)
      },
      {
        path: 'audit-registry',
        loadChildren: () => import('./audit-registry/audit-registry.module').then(m => m.FinanceAdminAuditRegistryModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ]
})
export class FinanceAdminEntityModule {}
