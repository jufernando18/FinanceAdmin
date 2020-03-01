import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { FinanceAdminTestModule } from '../../../test.module';
import { UserRegistryUpdateComponent } from 'app/entities/user-registry/user-registry-update.component';
import { UserRegistryService } from 'app/entities/user-registry/user-registry.service';
import { UserRegistry } from 'app/shared/model/user-registry.model';

describe('Component Tests', () => {
  describe('UserRegistry Management Update Component', () => {
    let comp: UserRegistryUpdateComponent;
    let fixture: ComponentFixture<UserRegistryUpdateComponent>;
    let service: UserRegistryService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [FinanceAdminTestModule],
        declarations: [UserRegistryUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(UserRegistryUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(UserRegistryUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(UserRegistryService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new UserRegistry(123);
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
        const entity = new UserRegistry();
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
