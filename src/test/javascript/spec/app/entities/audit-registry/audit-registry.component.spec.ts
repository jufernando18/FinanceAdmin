import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Data } from '@angular/router';

import { FinanceAdminTestModule } from '../../../test.module';
import { AuditRegistryComponent } from 'app/entities/audit-registry/audit-registry.component';
import { AuditRegistryService } from 'app/entities/audit-registry/audit-registry.service';
import { AuditRegistry } from 'app/shared/model/audit-registry.model';

describe('Component Tests', () => {
  describe('AuditRegistry Management Component', () => {
    let comp: AuditRegistryComponent;
    let fixture: ComponentFixture<AuditRegistryComponent>;
    let service: AuditRegistryService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [FinanceAdminTestModule],
        declarations: [AuditRegistryComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: {
              data: {
                subscribe: (fn: (value: Data) => void) =>
                  fn({
                    pagingParams: {
                      predicate: 'id',
                      reverse: false,
                      page: 0
                    }
                  })
              }
            }
          }
        ]
      })
        .overrideTemplate(AuditRegistryComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AuditRegistryComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AuditRegistryService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new AuditRegistry(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.auditRegistries && comp.auditRegistries[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should load a page', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new AuditRegistry(123)],
            headers
          })
        )
      );

      // WHEN
      comp.loadPage(1);

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.auditRegistries && comp.auditRegistries[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should calculate the sort attribute for an id', () => {
      // WHEN
      comp.ngOnInit();
      const result = comp.sort();

      // THEN
      expect(result).toEqual(['id,desc']);
    });

    it('should calculate the sort attribute for a non-id attribute', () => {
      // INIT
      comp.ngOnInit();

      // GIVEN
      comp.predicate = 'name';

      // WHEN
      const result = comp.sort();

      // THEN
      expect(result).toEqual(['name,desc', 'id']);
    });
  });
});
