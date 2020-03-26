import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAuditRegistry } from 'app/shared/model/audit-registry.model';
import { AuditRegistryService } from './audit-registry.service';

@Component({
  templateUrl: './audit-registry-delete-dialog.component.html'
})
export class AuditRegistryDeleteDialogComponent {
  auditRegistry?: IAuditRegistry;

  constructor(
    protected auditRegistryService: AuditRegistryService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.auditRegistryService.delete(id).subscribe(() => {
      this.eventManager.broadcast('auditRegistryListModification');
      this.activeModal.close();
    });
  }
}
