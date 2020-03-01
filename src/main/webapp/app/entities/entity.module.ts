import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'user-registry',
        loadChildren: () => import('./user-registry/user-registry.module').then(m => m.FinanceAdminUserRegistryModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ]
})
export class FinanceAdminEntityModule {}
