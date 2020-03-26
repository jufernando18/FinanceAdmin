import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAccountT } from 'app/shared/model/account-t.model';

@Component({
  selector: 'jhi-account-t-detail',
  templateUrl: './account-t-detail.component.html'
})
export class AccountTDetailComponent implements OnInit {
  accountT: IAccountT | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ accountT }) => (this.accountT = accountT));
  }

  previousState(): void {
    window.history.back();
  }
}
