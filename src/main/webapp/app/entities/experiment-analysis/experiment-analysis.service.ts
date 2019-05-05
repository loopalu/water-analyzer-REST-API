import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IExperimentAnalysis } from 'app/shared/model/experiment-analysis.model';

type EntityResponseType = HttpResponse<IExperimentAnalysis>;
type EntityArrayResponseType = HttpResponse<IExperimentAnalysis[]>;

@Injectable({ providedIn: 'root' })
export class ExperimentAnalysisService {
    public resourceUrl = SERVER_API_URL + 'api/experiment-analyses';

    constructor(protected http: HttpClient) {}

    create(experimentAnalysis: IExperimentAnalysis): Observable<EntityResponseType> {
        return this.http.post<IExperimentAnalysis>(this.resourceUrl, experimentAnalysis, { observe: 'response' });
    }

    update(experimentAnalysis: IExperimentAnalysis): Observable<EntityResponseType> {
        return this.http.put<IExperimentAnalysis>(this.resourceUrl, experimentAnalysis, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IExperimentAnalysis>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IExperimentAnalysis[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
