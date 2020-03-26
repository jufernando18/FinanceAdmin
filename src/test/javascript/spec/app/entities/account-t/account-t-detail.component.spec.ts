import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { FinanceAdminTestModule } from '../../../test.module';
import { AccountTDetailComponent } from 'app/entities/account-t/account-t-detail.component';
import { AccountT } from 'app/shared/model/account-t.model';

describe('Component Tests', () => {
  describe('AccountT Management Detail Component', () => {
    let comp: AccountTDetailComponent;
    let fixture: ComponentFixture<AccountTDetailComponent>;
    const route = ({ data: of({ accountT: new AccountT(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [FinanceAdminTestModule],
        declarations: [AccountTDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(AccountTDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AccountTDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load accountT on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.accountT).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
