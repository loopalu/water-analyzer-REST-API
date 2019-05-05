import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IExperimentResults } from 'app/shared/model/experiment-results.model';

type EntityResponseType = HttpResponse<IExperimentResults>;
type EntityArrayResponseType = HttpResponse<IExperimentResults[]>;

@Injectable({ providedIn: 'root' })
export class ExperimentResultsService {
    public resourceUrl = SERVER_API_URL + 'api/experiment-results';

    constructor(protected http: HttpClient) {}

    create(experimentResults: IExperimentResults): Observable<EntityResponseType> {
        return this.http.post<IExperimentResults>(this.resourceUrl, experimentResults, { observe: 'response' });
    }

    update(experimentResults: IExperimentResults): Observable<EntityResponseType> {
        return this.http.put<IExperimentResults>(this.resourceUrl, experimentResults, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IExperimentResults>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IExperimentResults[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
