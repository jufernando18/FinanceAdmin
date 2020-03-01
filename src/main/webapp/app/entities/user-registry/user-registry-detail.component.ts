import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IUserRegistry } from 'app/shared/model/user-registry.model';

@Component({
  selector: 'jhi-user-registry-detail',
  templateUrl: './user-registry-detail.component.html'
})
export class UserRegistryDetailComponent implements OnInit {
  userRegistry: IUserRegistry | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ userRegistry }) => (this.userRegistry = userRegistry));
  }

  previousState(): void {
    window.history.back();
  }
}
