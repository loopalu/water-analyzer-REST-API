import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ExperimentResults } from 'app/shared/model/experiment-results.model';
import { ExperimentResultsService } from './experiment-results.service';
import { ExperimentResultsComponent } from './experiment-results.component';
import { ExperimentResultsDetailComponent } from './experiment-results-detail.component';
import { ExperimentResultsUpdateComponent } from './experiment-results-update.component';
import { ExperimentResultsDeletePopupComponent } from './experiment-results-delete-dialog.component';
import { IExperimentResults } from 'app/shared/model/experiment-results.model';

@Injectable({ providedIn: 'root' })
export class ExperimentResultsResolve implements Resolve<IExperimentResults> {
    constructor(private service: ExperimentResultsService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IExperimentResults> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<ExperimentResults>) => response.ok),
                map((experimentResults: HttpResponse<ExperimentResults>) => experimentResults.body)
            );
        }
        return of(new ExperimentResults());
    }
}

export const experimentResultsRoute: Routes = [
    {
        path: '',
        component: ExperimentResultsComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ceApp.experimentResults.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: ExperimentResultsDetailComponent,
        resolve: {
            experimentResults: ExperimentResultsResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ceApp.experimentResults.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: ExperimentResultsUpdateComponent,
        resolve: {
            experimentResults: ExperimentResultsResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ceApp.experimentResults.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: ExperimentResultsUpdateComponent,
        resolve: {
            experimentResults: ExperimentResultsResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ceApp.experimentResults.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const experimentResultsPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: ExperimentResultsDeletePopupComponent,
        resolve: {
            experimentResults: ExperimentResultsResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ceApp.experimentResults.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
