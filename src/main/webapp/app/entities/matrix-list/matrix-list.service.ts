import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IMatrixList } from 'app/shared/model/matrix-list.model';

type EntityResponseType = HttpResponse<IMatrixList>;
type EntityArrayResponseType = HttpResponse<IMatrixList[]>;

@Injectable({ providedIn: 'root' })
export class MatrixListService {
    public resourceUrl = SERVER_API_URL + 'api/matrix-lists';

    constructor(protected http: HttpClient) {}

    create(matrixList: IMatrixList): Observable<EntityResponseType> {
        return this.http.post<IMatrixList>(this.resourceUrl, matrixList, { observe: 'response' });
    }

    update(matrixList: IMatrixList): Observable<EntityResponseType> {
        return this.http.put<IMatrixList>(this.resourceUrl, matrixList, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IMatrixList>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IMatrixList[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
