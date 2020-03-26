import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IAuditRegistry, AuditRegistry } from 'app/shared/model/audit-registry.model';
import { AuditRegistryService } from './audit-registry.service';
import { IUserRegistry } from 'app/shared/model/user-registry.model';
import { UserRegistryService } from 'app/entities/user-registry/user-registry.service';

@Component({
  selector: 'jhi-audit-registry-update',
  templateUrl: './audit-registry-update.component.html'
})
export class AuditRegistryUpdateComponent implements OnInit {
  isSaving = false;
  userregistries: IUserRegistry[] = [];

  editForm = this.fb.group({
    id: [],
    state: [null, [Validators.required]],
    createdBy: [null, [Validators.required]],
    createdDate: [null, [Validators.required]],
    lastModifiedBy: [],
    lastModifiedDate: [],
    createdById: [],
    lastModifiedById: []
  });

  constructor(
    protected auditRegistryService: AuditRegistryService,
    protected userRegistryService: UserRegistryService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ auditRegistry }) => {
      if (!auditRegistry.id) {
        const today = moment().startOf('day');
        auditRegistry.createdDate = today;
        auditRegistry.lastModifiedDate = today;
      }

      this.updateForm(auditRegistry);

      this.userRegistryService.query().subscribe((res: HttpResponse<IUserRegistry[]>) => (this.userregistries = res.body || []));
    });
  }

  updateForm(auditRegistry: IAuditRegistry): void {
    this.editForm.patchValue({
      id: auditRegistry.id,
      state: auditRegistry.state,
      createdBy: auditRegistry.createdBy,
      createdDate: auditRegistry.createdDate ? auditRegistry.createdDate.format(DATE_TIME_FORMAT) : null,
      lastModifiedBy: auditRegistry.lastModifiedBy,
      lastModifiedDate: auditRegistry.lastModifiedDate ? auditRegistry.lastModifiedDate.format(DATE_TIME_FORMAT) : null,
      createdById: auditRegistry.createdById,
      lastModifiedById: auditRegistry.lastModifiedById
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const auditRegistry = this.createFromForm();
    if (auditRegistry.id !== undefined) {
      this.subscribeToSaveResponse(this.auditRegistryService.update(auditRegistry));
    } else {
      this.subscribeToSaveResponse(this.auditRegistryService.create(auditRegistry));
    }
  }

  private createFromForm(): IAuditRegistry {
    return {
      ...new AuditRegistry(),
      id: this.editForm.get(['id'])!.value,
      state: this.editForm.get(['state'])!.value,
      createdBy: this.editForm.get(['createdBy'])!.value,
      createdDate: this.editForm.get(['createdDate'])!.value
        ? moment(this.editForm.get(['createdDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      lastModifiedBy: this.editForm.get(['lastModifiedBy'])!.value,
      lastModifiedDate: this.editForm.get(['lastModifiedDate'])!.value
        ? moment(this.editForm.get(['lastModifiedDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      createdById: this.editForm.get(['createdById'])!.value,
      lastModifiedById: this.editForm.get(['lastModifiedById'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAuditRegistry>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: IUserRegistry): any {
    return item.id;
  }
}
