import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { IUserRegistry, UserRegistry } from 'app/shared/model/user-registry.model';
import { UserRegistryService } from './user-registry.service';
import { IAuditRegistry } from 'app/shared/model/audit-registry.model';
import { AuditRegistryService } from 'app/entities/audit-registry/audit-registry.service';

@Component({
  selector: 'jhi-user-registry-update',
  templateUrl: './user-registry-update.component.html'
})
export class UserRegistryUpdateComponent implements OnInit {
  isSaving = false;
  audits: IAuditRegistry[] = [];

  editForm = this.fb.group({
    id: [],
    name: [],
    username: [null, [Validators.required, Validators.minLength(5)]],
    password: [null, [Validators.required, Validators.minLength(5)]],
    title: [],
    token: [null, [Validators.required]],
    session: [null, [Validators.required]],
    auditId: []
  });

  constructor(
    protected userRegistryService: UserRegistryService,
    protected auditRegistryService: AuditRegistryService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ userRegistry }) => {
      this.updateForm(userRegistry);

      this.auditRegistryService
        .query({ filter: 'userregistry-is-null' })
        .pipe(
          map((res: HttpResponse<IAuditRegistry[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: IAuditRegistry[]) => {
          if (!userRegistry.auditId) {
            this.audits = resBody;
          } else {
            this.auditRegistryService
              .find(userRegistry.auditId)
              .pipe(
                map((subRes: HttpResponse<IAuditRegistry>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IAuditRegistry[]) => (this.audits = concatRes));
          }
        });
    });
  }

  updateForm(userRegistry: IUserRegistry): void {
    this.editForm.patchValue({
      id: userRegistry.id,
      name: userRegistry.name,
      username: userRegistry.username,
      password: userRegistry.password,
      title: userRegistry.title,
      token: userRegistry.token,
      session: userRegistry.session,
      auditId: userRegistry.auditId
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const userRegistry = this.createFromForm();
    if (userRegistry.id !== undefined) {
      this.subscribeToSaveResponse(this.userRegistryService.update(userRegistry));
    } else {
      this.subscribeToSaveResponse(this.userRegistryService.create(userRegistry));
    }
  }

  private createFromForm(): IUserRegistry {
    return {
      ...new UserRegistry(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      username: this.editForm.get(['username'])!.value,
      password: this.editForm.get(['password'])!.value,
      title: this.editForm.get(['title'])!.value,
      token: this.editForm.get(['token'])!.value,
      session: this.editForm.get(['session'])!.value,
      auditId: this.editForm.get(['auditId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IUserRegistry>>): void {
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

  trackById(index: number, item: IAuditRegistry): any {
    return item.id;
  }
}
