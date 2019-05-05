import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IMethod } from 'app/shared/model/method.model';

type EntityResponseType = HttpResponse<IMethod>;
type EntityArrayResponseType = HttpResponse<IMethod[]>;

@Injectable({ providedIn: 'root' })
export class MethodService {
    public resourceUrl = SERVER_API_URL + 'api/methods';

    constructor(protected http: HttpClient) {}

    create(method: IMethod): Observable<EntityResponseType> {
        return this.http.post<IMethod>(this.resourceUrl, method, { observe: 'response' });
    }

    update(method: IMethod): Observable<EntityResponseType> {
        return this.http.put<IMethod>(this.resourceUrl, method, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IMethod>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IMethod[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
