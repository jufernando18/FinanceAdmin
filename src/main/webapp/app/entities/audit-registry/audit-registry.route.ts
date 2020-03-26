import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IAuditRegistry, AuditRegistry } from 'app/shared/model/audit-registry.model';
import { AuditRegistryService } from './audit-registry.service';
import { AuditRegistryComponent } from './audit-registry.component';
import { AuditRegistryDetailComponent } from './audit-registry-detail.component';
import { AuditRegistryUpdateComponent } from './audit-registry-update.component';

@Injectable({ providedIn: 'root' })
export class AuditRegistryResolve implements Resolve<IAuditRegistry> {
  constructor(private service: AuditRegistryService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAuditRegistry> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((auditRegistry: HttpResponse<AuditRegistry>) => {
          if (auditRegistry.body) {
            return of(auditRegistry.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new AuditRegistry());
  }
}

export const auditRegistryRoute: Routes = [
  {
    path: '',
    component: AuditRegistryComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'financeAdminApp.auditRegistry.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: AuditRegistryDetailComponent,
    resolve: {
      auditRegistry: AuditRegistryResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'financeAdminApp.auditRegistry.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: AuditRegistryUpdateComponent,
    resolve: {
      auditRegistry: AuditRegistryResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'financeAdminApp.auditRegistry.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: AuditRegistryUpdateComponent,
    resolve: {
      auditRegistry: AuditRegistryResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'financeAdminApp.auditRegistry.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
