import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IAccountT } from 'app/shared/model/account-t.model';

type EntityResponseType = HttpResponse<IAccountT>;
type EntityArrayResponseType = HttpResponse<IAccountT[]>;

@Injectable({ providedIn: 'root' })
export class AccountTService {
  public resourceUrl = SERVER_API_URL + 'api/account-ts';

  constructor(protected http: HttpClient) {}

  create(accountT: IAccountT): Observable<EntityResponseType> {
    return this.http.post<IAccountT>(this.resourceUrl, accountT, { observe: 'response' });
  }

  update(accountT: IAccountT): Observable<EntityResponseType> {
    return this.http.put<IAccountT>(this.resourceUrl, accountT, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IAccountT>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAccountT[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
