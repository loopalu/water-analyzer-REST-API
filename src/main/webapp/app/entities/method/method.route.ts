import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Method } from 'app/shared/model/method.model';
import { MethodService } from './method.service';
import { MethodComponent } from './method.component';
import { MethodDetailComponent } from './method-detail.component';
import { MethodUpdateComponent } from './method-update.component';
import { MethodDeletePopupComponent } from './method-delete-dialog.component';
import { IMethod } from 'app/shared/model/method.model';

@Injectable({ providedIn: 'root' })
export class MethodResolve implements Resolve<IMethod> {
    constructor(private service: MethodService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IMethod> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Method>) => response.ok),
                map((method: HttpResponse<Method>) => method.body)
            );
        }
        return of(new Method());
    }
}

export const methodRoute: Routes = [
    {
        path: '',
        component: MethodComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ceApp.method.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: MethodDetailComponent,
        resolve: {
            method: MethodResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ceApp.method.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: MethodUpdateComponent,
        resolve: {
            method: MethodResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ceApp.method.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: MethodUpdateComponent,
        resolve: {
            method: MethodResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ceApp.method.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const methodPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: MethodDeletePopupComponent,
        resolve: {
            method: MethodResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ceApp.method.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
