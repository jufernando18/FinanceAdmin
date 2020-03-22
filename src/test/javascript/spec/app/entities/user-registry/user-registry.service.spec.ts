import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { UserRegistryService } from 'app/entities/user-registry/user-registry.service';
import { IUserRegistry, UserRegistry } from 'app/shared/model/user-registry.model';

describe('Service Tests', () => {
  describe('UserRegistry Service', () => {
    let injector: TestBed;
    let service: UserRegistryService;
    let httpMock: HttpTestingController;
    let elemDefault: IUserRegistry;
    let expectedResult: IUserRegistry | IUserRegistry[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(UserRegistryService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new UserRegistry(0, 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', false);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a UserRegistry', () => {
        const returnedFromService = Object.assign(
          {
            id: 0
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new UserRegistry()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a UserRegistry', () => {
        const returnedFromService = Object.assign(
          {
            name: 'BBBBBB',
            username: 'BBBBBB',
            password: 'BBBBBB',
            title: 'BBBBBB',
            token: 'BBBBBB',
            session: true
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of UserRegistry', () => {
        const returnedFromService = Object.assign(
          {
            name: 'BBBBBB',
            username: 'BBBBBB',
            password: 'BBBBBB',
            title: 'BBBBBB',
            token: 'BBBBBB',
            session: true
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a UserRegistry', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
