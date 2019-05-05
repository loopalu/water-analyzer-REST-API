import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ExperimentPeaks } from 'app/shared/model/experiment-peaks.model';
import { ExperimentPeaksService } from './experiment-peaks.service';
import { ExperimentPeaksComponent } from './experiment-peaks.component';
import { ExperimentPeaksDetailComponent } from './experiment-peaks-detail.component';
import { ExperimentPeaksUpdateComponent } from './experiment-peaks-update.component';
import { ExperimentPeaksDeletePopupComponent } from './experiment-peaks-delete-dialog.component';
import { IExperimentPeaks } from 'app/shared/model/experiment-peaks.model';

@Injectable({ providedIn: 'root' })
export class ExperimentPeaksResolve implements Resolve<IExperimentPeaks> {
    constructor(private service: ExperimentPeaksService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IExperimentPeaks> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<ExperimentPeaks>) => response.ok),
                map((experimentPeaks: HttpResponse<ExperimentPeaks>) => experimentPeaks.body)
            );
        }
        return of(new ExperimentPeaks());
    }
}

export const experimentPeaksRoute: Routes = [
    {
        path: '',
        component: ExperimentPeaksComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ceApp.experimentPeaks.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: ExperimentPeaksDetailComponent,
        resolve: {
            experimentPeaks: ExperimentPeaksResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ceApp.experimentPeaks.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: ExperimentPeaksUpdateComponent,
        resolve: {
            experimentPeaks: ExperimentPeaksResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ceApp.experimentPeaks.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: ExperimentPeaksUpdateComponent,
        resolve: {
            experimentPeaks: ExperimentPeaksResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ceApp.experimentPeaks.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const experimentPeaksPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: ExperimentPeaksDeletePopupComponent,
        resolve: {
            experimentPeaks: ExperimentPeaksResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ceApp.experimentPeaks.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
