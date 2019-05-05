import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { CapillaryType } from 'app/shared/model/capillary-type.model';
import { CapillaryTypeService } from './capillary-type.service';
import { CapillaryTypeComponent } from './capillary-type.component';
import { CapillaryTypeDetailComponent } from './capillary-type-detail.component';
import { CapillaryTypeUpdateComponent } from './capillary-type-update.component';
import { CapillaryTypeDeletePopupComponent } from './capillary-type-delete-dialog.component';
import { ICapillaryType } from 'app/shared/model/capillary-type.model';

@Injectable({ providedIn: 'root' })
export class CapillaryTypeResolve implements Resolve<ICapillaryType> {
    constructor(private service: CapillaryTypeService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ICapillaryType> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<CapillaryType>) => response.ok),
                map((capillaryType: HttpResponse<CapillaryType>) => capillaryType.body)
            );
        }
        return of(new CapillaryType());
    }
}

export const capillaryTypeRoute: Routes = [
    {
        path: '',
        component: CapillaryTypeComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ceApp.capillaryType.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: CapillaryTypeDetailComponent,
        resolve: {
            capillaryType: CapillaryTypeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ceApp.capillaryType.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: CapillaryTypeUpdateComponent,
        resolve: {
            capillaryType: CapillaryTypeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ceApp.capillaryType.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: CapillaryTypeUpdateComponent,
        resolve: {
            capillaryType: CapillaryTypeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ceApp.capillaryType.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const capillaryTypePopupRoute: Routes = [
    {
        path: ':id/delete',
        component: CapillaryTypeDeletePopupComponent,
        resolve: {
            capillaryType: CapillaryTypeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ceApp.capillaryType.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
