import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ICapillaryType } from 'app/shared/model/capillary-type.model';

type EntityResponseType = HttpResponse<ICapillaryType>;
type EntityArrayResponseType = HttpResponse<ICapillaryType[]>;

@Injectable({ providedIn: 'root' })
export class CapillaryTypeService {
    public resourceUrl = SERVER_API_URL + 'api/capillary-types';

    constructor(protected http: HttpClient) {}

    create(capillaryType: ICapillaryType): Observable<EntityResponseType> {
        return this.http.post<ICapillaryType>(this.resourceUrl, capillaryType, { observe: 'response' });
    }

    update(capillaryType: ICapillaryType): Observable<EntityResponseType> {
        return this.http.put<ICapillaryType>(this.resourceUrl, capillaryType, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<ICapillaryType>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ICapillaryType[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
