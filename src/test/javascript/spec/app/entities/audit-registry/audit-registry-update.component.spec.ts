import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { FinanceAdminTestModule } from '../../../test.module';
import { AuditRegistryUpdateComponent } from 'app/entities/audit-registry/audit-registry-update.component';
import { AuditRegistryService } from 'app/entities/audit-registry/audit-registry.service';
import { AuditRegistry } from 'app/shared/model/audit-registry.model';

describe('Component Tests', () => {
  describe('AuditRegistry Management Update Component', () => {
    let comp: AuditRegistryUpdateComponent;
    let fixture: ComponentFixture<AuditRegistryUpdateComponent>;
    let service: AuditRegistryService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [FinanceAdminTestModule],
        declarations: [AuditRegistryUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(AuditRegistryUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AuditRegistryUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AuditRegistryService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new AuditRegistry(123);
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
        const entity = new AuditRegistry();
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
