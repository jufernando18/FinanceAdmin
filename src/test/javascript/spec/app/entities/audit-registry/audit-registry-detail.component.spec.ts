import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { FinanceAdminTestModule } from '../../../test.module';
import { AuditRegistryDetailComponent } from 'app/entities/audit-registry/audit-registry-detail.component';
import { AuditRegistry } from 'app/shared/model/audit-registry.model';

describe('Component Tests', () => {
  describe('AuditRegistry Management Detail Component', () => {
    let comp: AuditRegistryDetailComponent;
    let fixture: ComponentFixture<AuditRegistryDetailComponent>;
    const route = ({ data: of({ auditRegistry: new AuditRegistry(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [FinanceAdminTestModule],
        declarations: [AuditRegistryDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(AuditRegistryDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AuditRegistryDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load auditRegistry on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.auditRegistry).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
