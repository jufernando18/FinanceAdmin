import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { ITransaction, Transaction } from 'app/shared/model/transaction.model';
import { TransactionService } from './transaction.service';
import { IAuditRegistry } from 'app/shared/model/audit-registry.model';
import { AuditRegistryService } from 'app/entities/audit-registry/audit-registry.service';
import { IAccountT } from 'app/shared/model/account-t.model';
import { AccountTService } from 'app/entities/account-t/account-t.service';

type SelectableEntity = IAuditRegistry | IAccountT;

@Component({
  selector: 'jhi-transaction-update',
  templateUrl: './transaction-update.component.html'
})
export class TransactionUpdateComponent implements OnInit {
  isSaving = false;
  audits: IAuditRegistry[] = [];
  accountts: IAccountT[] = [];

  editForm = this.fb.group({
    id: [],
    concept: [],
    accountT: [],
    type: [],
    value: [],
    date: [],
    balance: [],
    auditId: [],
    accountTId: []
  });

  constructor(
    protected transactionService: TransactionService,
    protected auditRegistryService: AuditRegistryService,
    protected accountTService: AccountTService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ transaction }) => {
      if (!transaction.id) {
        const today = moment().startOf('day');
        transaction.date = today;
      }

      this.updateForm(transaction);

      this.auditRegistryService
        .query({ filter: 'transaction-is-null' })
        .pipe(
          map((res: HttpResponse<IAuditRegistry[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: IAuditRegistry[]) => {
          if (!transaction.auditId) {
            this.audits = resBody;
          } else {
            this.auditRegistryService
              .find(transaction.auditId)
              .pipe(
                map((subRes: HttpResponse<IAuditRegistry>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IAuditRegistry[]) => (this.audits = concatRes));
          }
        });

      this.accountTService.query().subscribe((res: HttpResponse<IAccountT[]>) => (this.accountts = res.body || []));
    });
  }

  updateForm(transaction: ITransaction): void {
    this.editForm.patchValue({
      id: transaction.id,
      concept: transaction.concept,
      accountT: transaction.accountT,
      type: transaction.type,
      value: transaction.value,
      date: transaction.date ? transaction.date.format(DATE_TIME_FORMAT) : null,
      balance: transaction.balance,
      auditId: transaction.auditId,
      accountTId: transaction.accountTId
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const transaction = this.createFromForm();
    if (transaction.id !== undefined) {
      this.subscribeToSaveResponse(this.transactionService.update(transaction));
    } else {
      this.subscribeToSaveResponse(this.transactionService.create(transaction));
    }
  }

  private createFromForm(): ITransaction {
    return {
      ...new Transaction(),
      id: this.editForm.get(['id'])!.value,
      concept: this.editForm.get(['concept'])!.value,
      accountT: this.editForm.get(['accountT'])!.value,
      type: this.editForm.get(['type'])!.value,
      value: this.editForm.get(['value'])!.value,
      date: this.editForm.get(['date'])!.value ? moment(this.editForm.get(['date'])!.value, DATE_TIME_FORMAT) : undefined,
      balance: this.editForm.get(['balance'])!.value,
      auditId: this.editForm.get(['auditId'])!.value,
      accountTId: this.editForm.get(['accountTId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITransaction>>): void {
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

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }
}
