import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IUserRegistry } from 'app/shared/model/user-registry.model';

type EntityResponseType = HttpResponse<IUserRegistry>;
type EntityArrayResponseType = HttpResponse<IUserRegistry[]>;

@Injectable({ providedIn: 'root' })
export class UserRegistryService {
  public resourceUrl = SERVER_API_URL + 'api/user-registries';

  constructor(protected http: HttpClient) {}

  create(userRegistry: IUserRegistry): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(userRegistry);
    return this.http
      .post<IUserRegistry>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(userRegistry: IUserRegistry): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(userRegistry);
    return this.http
      .put<IUserRegistry>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IUserRegistry>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IUserRegistry[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(userRegistry: IUserRegistry): IUserRegistry {
    const copy: IUserRegistry = Object.assign({}, userRegistry, {
      createdAt: userRegistry.createdAt && userRegistry.createdAt.isValid() ? userRegistry.createdAt.toJSON() : undefined,
      updatedAt: userRegistry.updatedAt && userRegistry.updatedAt.isValid() ? userRegistry.updatedAt.toJSON() : undefined
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.createdAt = res.body.createdAt ? moment(res.body.createdAt) : undefined;
      res.body.updatedAt = res.body.updatedAt ? moment(res.body.updatedAt) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((userRegistry: IUserRegistry) => {
        userRegistry.createdAt = userRegistry.createdAt ? moment(userRegistry.createdAt) : undefined;
        userRegistry.updatedAt = userRegistry.updatedAt ? moment(userRegistry.updatedAt) : undefined;
      });
    }
    return res;
  }
}
