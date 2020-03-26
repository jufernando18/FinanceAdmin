import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IAccountT, AccountT } from 'app/shared/model/account-t.model';
import { AccountTService } from './account-t.service';
import { AccountTComponent } from './account-t.component';
import { AccountTDetailComponent } from './account-t-detail.component';
import { AccountTUpdateComponent } from './account-t-update.component';

@Injectable({ providedIn: 'root' })
export class AccountTResolve implements Resolve<IAccountT> {
  constructor(private service: AccountTService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAccountT> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((accountT: HttpResponse<AccountT>) => {
          if (accountT.body) {
            return of(accountT.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new AccountT());
  }
}

export const accountTRoute: Routes = [
  {
    path: '',
    component: AccountTComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'financeAdminApp.accountT.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: AccountTDetailComponent,
    resolve: {
      accountT: AccountTResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'financeAdminApp.accountT.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: AccountTUpdateComponent,
    resolve: {
      accountT: AccountTResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'financeAdminApp.accountT.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: AccountTUpdateComponent,
    resolve: {
      accountT: AccountTResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'financeAdminApp.accountT.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
