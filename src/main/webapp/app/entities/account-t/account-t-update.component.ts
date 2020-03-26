import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { IAccountT, AccountT } from 'app/shared/model/account-t.model';
import { AccountTService } from './account-t.service';
import { IAuditRegistry } from 'app/shared/model/audit-registry.model';
import { AuditRegistryService } from 'app/entities/audit-registry/audit-registry.service';

@Component({
  selector: 'jhi-account-t-update',
  templateUrl: './account-t-update.component.html'
})
export class AccountTUpdateComponent implements OnInit {
  isSaving = false;
  audits: IAuditRegistry[] = [];

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    description: [],
    code: [null, []],
    increaseWhen: [],
    decreaseWhen: [],
    balance: [null, [Validators.required]],
    auditId: []
  });

  constructor(
    protected accountTService: AccountTService,
    protected auditRegistryService: AuditRegistryService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ accountT }) => {
      this.updateForm(accountT);

      this.auditRegistryService
        .query({ filter: 'accountt-is-null' })
        .pipe(
          map((res: HttpResponse<IAuditRegistry[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: IAuditRegistry[]) => {
          if (!accountT.auditId) {
            this.audits = resBody;
          } else {
            this.auditRegistryService
              .find(accountT.auditId)
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

  updateForm(accountT: IAccountT): void {
    this.editForm.patchValue({
      id: accountT.id,
      name: accountT.name,
      description: accountT.description,
      code: accountT.code,
      increaseWhen: accountT.increaseWhen,
      decreaseWhen: accountT.decreaseWhen,
      balance: accountT.balance,
      auditId: accountT.auditId
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const accountT = this.createFromForm();
    if (accountT.id !== undefined) {
      this.subscribeToSaveResponse(this.accountTService.update(accountT));
    } else {
      this.subscribeToSaveResponse(this.accountTService.create(accountT));
    }
  }

  private createFromForm(): IAccountT {
    return {
      ...new AccountT(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      description: this.editForm.get(['description'])!.value,
      code: this.editForm.get(['code'])!.value,
      increaseWhen: this.editForm.get(['increaseWhen'])!.value,
      decreaseWhen: this.editForm.get(['decreaseWhen'])!.value,
      balance: this.editForm.get(['balance'])!.value,
      auditId: this.editForm.get(['auditId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAccountT>>): void {
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
