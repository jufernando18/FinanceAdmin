import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IUserRegistry } from 'app/shared/model/user-registry.model';
import { UserRegistryService } from './user-registry.service';

@Component({
  templateUrl: './user-registry-delete-dialog.component.html'
})
export class UserRegistryDeleteDialogComponent {
  userRegistry?: IUserRegistry;

  constructor(
    protected userRegistryService: UserRegistryService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.userRegistryService.delete(id).subscribe(() => {
      this.eventManager.broadcast('userRegistryListModification');
      this.activeModal.close();
    });
  }
}
