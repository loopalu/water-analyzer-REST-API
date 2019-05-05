import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { MatrixList } from 'app/shared/model/matrix-list.model';
import { MatrixListService } from './matrix-list.service';
import { MatrixListComponent } from './matrix-list.component';
import { MatrixListDetailComponent } from './matrix-list-detail.component';
import { MatrixListUpdateComponent } from './matrix-list-update.component';
import { MatrixListDeletePopupComponent } from './matrix-list-delete-dialog.component';
import { IMatrixList } from 'app/shared/model/matrix-list.model';

@Injectable({ providedIn: 'root' })
export class MatrixListResolve implements Resolve<IMatrixList> {
    constructor(private service: MatrixListService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IMatrixList> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<MatrixList>) => response.ok),
                map((matrixList: HttpResponse<MatrixList>) => matrixList.body)
            );
        }
        return of(new MatrixList());
    }
}

export const matrixListRoute: Routes = [
    {
        path: '',
        component: MatrixListComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ceApp.matrixList.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: MatrixListDetailComponent,
        resolve: {
            matrixList: MatrixListResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ceApp.matrixList.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: MatrixListUpdateComponent,
        resolve: {
            matrixList: MatrixListResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ceApp.matrixList.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: MatrixListUpdateComponent,
        resolve: {
            matrixList: MatrixListResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ceApp.matrixList.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const matrixListPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: MatrixListDeletePopupComponent,
        resolve: {
            matrixList: MatrixListResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ceApp.matrixList.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
