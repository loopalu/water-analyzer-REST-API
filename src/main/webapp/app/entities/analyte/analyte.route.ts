import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Analyte } from 'app/shared/model/analyte.model';
import { AnalyteService } from './analyte.service';
import { AnalyteComponent } from './analyte.component';
import { AnalyteDetailComponent } from './analyte-detail.component';
import { AnalyteUpdateComponent } from './analyte-update.component';
import { AnalyteDeletePopupComponent } from './analyte-delete-dialog.component';
import { IAnalyte } from 'app/shared/model/analyte.model';

@Injectable({ providedIn: 'root' })
export class AnalyteResolve implements Resolve<IAnalyte> {
    constructor(private service: AnalyteService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IAnalyte> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Analyte>) => response.ok),
                map((analyte: HttpResponse<Analyte>) => analyte.body)
            );
        }
        return of(new Analyte());
    }
}

export const analyteRoute: Routes = [
    {
        path: '',
        component: AnalyteComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ceApp.analyte.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: AnalyteDetailComponent,
        resolve: {
            analyte: AnalyteResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ceApp.analyte.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: AnalyteUpdateComponent,
        resolve: {
            analyte: AnalyteResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ceApp.analyte.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: AnalyteUpdateComponent,
        resolve: {
            analyte: AnalyteResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ceApp.analyte.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const analytePopupRoute: Routes = [
    {
        path: ':id/delete',
        component: AnalyteDeletePopupComponent,
        resolve: {
            analyte: AnalyteResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ceApp.analyte.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
