import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { FinanceAdminTestModule } from '../../../test.module';
import { UserRegistryDetailComponent } from 'app/entities/user-registry/user-registry-detail.component';
import { UserRegistry } from 'app/shared/model/user-registry.model';

describe('Component Tests', () => {
  describe('UserRegistry Management Detail Component', () => {
    let comp: UserRegistryDetailComponent;
    let fixture: ComponentFixture<UserRegistryDetailComponent>;
    const route = ({ data: of({ userRegistry: new UserRegistry(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [FinanceAdminTestModule],
        declarations: [UserRegistryDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(UserRegistryDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(UserRegistryDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load userRegistry on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.userRegistry).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
