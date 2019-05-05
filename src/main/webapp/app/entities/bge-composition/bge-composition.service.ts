import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IBGEComposition } from 'app/shared/model/bge-composition.model';

type EntityResponseType = HttpResponse<IBGEComposition>;
type EntityArrayResponseType = HttpResponse<IBGEComposition[]>;

@Injectable({ providedIn: 'root' })
export class BGECompositionService {
    public resourceUrl = SERVER_API_URL + 'api/bge-compositions';

    constructor(protected http: HttpClient) {}

    create(bGEComposition: IBGEComposition): Observable<EntityResponseType> {
        return this.http.post<IBGEComposition>(this.resourceUrl, bGEComposition, { observe: 'response' });
    }

    update(bGEComposition: IBGEComposition): Observable<EntityResponseType> {
        return this.http.put<IBGEComposition>(this.resourceUrl, bGEComposition, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IBGEComposition>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IBGEComposition[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
