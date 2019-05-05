import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IExperimentPeaks } from 'app/shared/model/experiment-peaks.model';

type EntityResponseType = HttpResponse<IExperimentPeaks>;
type EntityArrayResponseType = HttpResponse<IExperimentPeaks[]>;

@Injectable({ providedIn: 'root' })
export class ExperimentPeaksService {
    public resourceUrl = SERVER_API_URL + 'api/experiment-peaks';

    constructor(protected http: HttpClient) {}

    create(experimentPeaks: IExperimentPeaks): Observable<EntityResponseType> {
        return this.http.post<IExperimentPeaks>(this.resourceUrl, experimentPeaks, { observe: 'response' });
    }

    update(experimentPeaks: IExperimentPeaks): Observable<EntityResponseType> {
        return this.http.put<IExperimentPeaks>(this.resourceUrl, experimentPeaks, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IExperimentPeaks>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IExperimentPeaks[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
