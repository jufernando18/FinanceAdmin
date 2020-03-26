import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAccountT } from 'app/shared/model/account-t.model';
import { AccountTService } from './account-t.service';

@Component({
  templateUrl: './account-t-delete-dialog.component.html'
})
export class AccountTDeleteDialogComponent {
  accountT?: IAccountT;

  constructor(protected accountTService: AccountTService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.accountTService.delete(id).subscribe(() => {
      this.eventManager.broadcast('accountTListModification');
      this.activeModal.close();
    });
  }
}
