import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IAnalytesOfInterest } from 'app/shared/model/analytes-of-interest.model';

type EntityResponseType = HttpResponse<IAnalytesOfInterest>;
type EntityArrayResponseType = HttpResponse<IAnalytesOfInterest[]>;

@Injectable({ providedIn: 'root' })
export class AnalytesOfInterestService {
    public resourceUrl = SERVER_API_URL + 'api/analytes-of-interests';

    constructor(protected http: HttpClient) {}

    create(analytesOfInterest: IAnalytesOfInterest): Observable<EntityResponseType> {
        return this.http.post<IAnalytesOfInterest>(this.resourceUrl, analytesOfInterest, { observe: 'response' });
    }

    update(analytesOfInterest: IAnalytesOfInterest): Observable<EntityResponseType> {
        return this.http.put<IAnalytesOfInterest>(this.resourceUrl, analytesOfInterest, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IAnalytesOfInterest>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IAnalytesOfInterest[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
