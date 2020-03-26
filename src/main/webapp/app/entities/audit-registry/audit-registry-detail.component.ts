import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAuditRegistry } from 'app/shared/model/audit-registry.model';

@Component({
  selector: 'jhi-audit-registry-detail',
  templateUrl: './audit-registry-detail.component.html'
})
export class AuditRegistryDetailComponent implements OnInit {
  auditRegistry: IAuditRegistry | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ auditRegistry }) => (this.auditRegistry = auditRegistry));
  }

  previousState(): void {
    window.history.back();
  }
}
