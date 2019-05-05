import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IAnalyte } from 'app/shared/model/analyte.model';

type EntityResponseType = HttpResponse<IAnalyte>;
type EntityArrayResponseType = HttpResponse<IAnalyte[]>;

@Injectable({ providedIn: 'root' })
export class AnalyteService {
    public resourceUrl = SERVER_API_URL + 'api/analytes';

    constructor(protected http: HttpClient) {}

    create(analyte: IAnalyte): Observable<EntityResponseType> {
        return this.http.post<IAnalyte>(this.resourceUrl, analyte, { observe: 'response' });
    }

    update(analyte: IAnalyte): Observable<EntityResponseType> {
        return this.http.put<IAnalyte>(this.resourceUrl, analyte, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IAnalyte>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IAnalyte[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
