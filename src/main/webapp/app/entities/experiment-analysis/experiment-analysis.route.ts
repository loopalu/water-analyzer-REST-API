import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ExperimentAnalysis } from 'app/shared/model/experiment-analysis.model';
import { ExperimentAnalysisService } from './experiment-analysis.service';
import { ExperimentAnalysisComponent } from './experiment-analysis.component';
import { ExperimentAnalysisDetailComponent } from './experiment-analysis-detail.component';
import { ExperimentAnalysisUpdateComponent } from './experiment-analysis-update.component';
import { ExperimentAnalysisDeletePopupComponent } from './experiment-analysis-delete-dialog.component';
import { IExperimentAnalysis } from 'app/shared/model/experiment-analysis.model';

@Injectable({ providedIn: 'root' })
export class ExperimentAnalysisResolve implements Resolve<IExperimentAnalysis> {
    constructor(private service: ExperimentAnalysisService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IExperimentAnalysis> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<ExperimentAnalysis>) => response.ok),
                map((experimentAnalysis: HttpResponse<ExperimentAnalysis>) => experimentAnalysis.body)
            );
        }
        return of(new ExperimentAnalysis());
    }
}

export const experimentAnalysisRoute: Routes = [
    {
        path: '',
        component: ExperimentAnalysisComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ceApp.experimentAnalysis.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: ExperimentAnalysisDetailComponent,
        resolve: {
            experimentAnalysis: ExperimentAnalysisResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ceApp.experimentAnalysis.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: ExperimentAnalysisUpdateComponent,
        resolve: {
            experimentAnalysis: ExperimentAnalysisResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ceApp.experimentAnalysis.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: ExperimentAnalysisUpdateComponent,
        resolve: {
            experimentAnalysis: ExperimentAnalysisResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ceApp.experimentAnalysis.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const experimentAnalysisPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: ExperimentAnalysisDeletePopupComponent,
        resolve: {
            experimentAnalysis: ExperimentAnalysisResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ceApp.experimentAnalysis.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
