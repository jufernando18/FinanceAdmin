import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

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
    return this.http.post<IUserRegistry>(this.resourceUrl, userRegistry, { observe: 'response' });
  }

  update(userRegistry: IUserRegistry): Observable<EntityResponseType> {
    return this.http.put<IUserRegistry>(this.resourceUrl, userRegistry, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IUserRegistry>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IUserRegistry[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
