import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { FinanceAdminTestModule } from '../../../test.module';
import { AccountTUpdateComponent } from 'app/entities/account-t/account-t-update.component';
import { AccountTService } from 'app/entities/account-t/account-t.service';
import { AccountT } from 'app/shared/model/account-t.model';

describe('Component Tests', () => {
  describe('AccountT Management Update Component', () => {
    let comp: AccountTUpdateComponent;
    let fixture: ComponentFixture<AccountTUpdateComponent>;
    let service: AccountTService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [FinanceAdminTestModule],
        declarations: [AccountTUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(AccountTUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AccountTUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AccountTService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new AccountT(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new AccountT();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
