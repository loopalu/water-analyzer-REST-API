import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { AnalytesOfInterest } from 'app/shared/model/analytes-of-interest.model';
import { AnalytesOfInterestService } from './analytes-of-interest.service';
import { AnalytesOfInterestComponent } from './analytes-of-interest.component';
import { AnalytesOfInterestDetailComponent } from './analytes-of-interest-detail.component';
import { AnalytesOfInterestUpdateComponent } from './analytes-of-interest-update.component';
import { AnalytesOfInterestDeletePopupComponent } from './analytes-of-interest-delete-dialog.component';
import { IAnalytesOfInterest } from 'app/shared/model/analytes-of-interest.model';

@Injectable({ providedIn: 'root' })
export class AnalytesOfInterestResolve implements Resolve<IAnalytesOfInterest> {
    constructor(private service: AnalytesOfInterestService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IAnalytesOfInterest> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<AnalytesOfInterest>) => response.ok),
                map((analytesOfInterest: HttpResponse<AnalytesOfInterest>) => analytesOfInterest.body)
            );
        }
        return of(new AnalytesOfInterest());
    }
}

export const analytesOfInterestRoute: Routes = [
    {
        path: '',
        component: AnalytesOfInterestComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ceApp.analytesOfInterest.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: AnalytesOfInterestDetailComponent,
        resolve: {
            analytesOfInterest: AnalytesOfInterestResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ceApp.analytesOfInterest.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: AnalytesOfInterestUpdateComponent,
        resolve: {
            analytesOfInterest: AnalytesOfInterestResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ceApp.analytesOfInterest.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: AnalytesOfInterestUpdateComponent,
        resolve: {
            analytesOfInterest: AnalytesOfInterestResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ceApp.analytesOfInterest.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const analytesOfInterestPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: AnalytesOfInterestDeletePopupComponent,
        resolve: {
            analytesOfInterest: AnalytesOfInterestResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ceApp.analytesOfInterest.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
