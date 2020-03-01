import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IUserRegistry, UserRegistry } from 'app/shared/model/user-registry.model';
import { UserRegistryService } from './user-registry.service';
import { UserRegistryComponent } from './user-registry.component';
import { UserRegistryDetailComponent } from './user-registry-detail.component';
import { UserRegistryUpdateComponent } from './user-registry-update.component';

@Injectable({ providedIn: 'root' })
export class UserRegistryResolve implements Resolve<IUserRegistry> {
  constructor(private service: UserRegistryService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IUserRegistry> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((userRegistry: HttpResponse<UserRegistry>) => {
          if (userRegistry.body) {
            return of(userRegistry.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new UserRegistry());
  }
}

export const userRegistryRoute: Routes = [
  {
    path: '',
    component: UserRegistryComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'financeAdminApp.userRegistry.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: UserRegistryDetailComponent,
    resolve: {
      userRegistry: UserRegistryResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'financeAdminApp.userRegistry.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: UserRegistryUpdateComponent,
    resolve: {
      userRegistry: UserRegistryResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'financeAdminApp.userRegistry.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: UserRegistryUpdateComponent,
    resolve: {
      userRegistry: UserRegistryResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'financeAdminApp.userRegistry.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
