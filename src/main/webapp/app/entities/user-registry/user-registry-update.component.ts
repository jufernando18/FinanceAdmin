import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IUserRegistry, UserRegistry } from 'app/shared/model/user-registry.model';
import { UserRegistryService } from './user-registry.service';

@Component({
  selector: 'jhi-user-registry-update',
  templateUrl: './user-registry-update.component.html'
})
export class UserRegistryUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    username: [null, [Validators.required, Validators.minLength(5)]],
    password: [null, [Validators.required, Validators.minLength(5)]],
    title: [],
    token: [null, [Validators.required]],
    session: [null, [Validators.required]],
    createdAt: [null, [Validators.required]],
    updatedAt: [null, [Validators.required]]
  });

  constructor(protected userRegistryService: UserRegistryService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ userRegistry }) => {
      if (!userRegistry.id) {
        const today = moment().startOf('day');
        userRegistry.createdAt = today;
        userRegistry.updatedAt = today;
      }

      this.updateForm(userRegistry);
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
      createdAt: userRegistry.createdAt ? userRegistry.createdAt.format(DATE_TIME_FORMAT) : null,
      updatedAt: userRegistry.updatedAt ? userRegistry.updatedAt.format(DATE_TIME_FORMAT) : null
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
      createdAt: this.editForm.get(['createdAt'])!.value ? moment(this.editForm.get(['createdAt'])!.value, DATE_TIME_FORMAT) : undefined,
      updatedAt: this.editForm.get(['updatedAt'])!.value ? moment(this.editForm.get(['updatedAt'])!.value, DATE_TIME_FORMAT) : undefined
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
}
