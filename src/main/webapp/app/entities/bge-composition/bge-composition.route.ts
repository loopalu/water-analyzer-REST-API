import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { BGEComposition } from 'app/shared/model/bge-composition.model';
import { BGECompositionService } from './bge-composition.service';
import { BGECompositionComponent } from './bge-composition.component';
import { BGECompositionDetailComponent } from './bge-composition-detail.component';
import { BGECompositionUpdateComponent } from './bge-composition-update.component';
import { BGECompositionDeletePopupComponent } from './bge-composition-delete-dialog.component';
import { IBGEComposition } from 'app/shared/model/bge-composition.model';

@Injectable({ providedIn: 'root' })
export class BGECompositionResolve implements Resolve<IBGEComposition> {
    constructor(private service: BGECompositionService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IBGEComposition> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<BGEComposition>) => response.ok),
                map((bGEComposition: HttpResponse<BGEComposition>) => bGEComposition.body)
            );
        }
        return of(new BGEComposition());
    }
}

export const bGECompositionRoute: Routes = [
    {
        path: '',
        component: BGECompositionComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ceApp.bGEComposition.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: BGECompositionDetailComponent,
        resolve: {
            bGEComposition: BGECompositionResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ceApp.bGEComposition.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: BGECompositionUpdateComponent,
        resolve: {
            bGEComposition: BGECompositionResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ceApp.bGEComposition.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: BGECompositionUpdateComponent,
        resolve: {
            bGEComposition: BGECompositionResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ceApp.bGEComposition.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const bGECompositionPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: BGECompositionDeletePopupComponent,
        resolve: {
            bGEComposition: BGECompositionResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ceApp.bGEComposition.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
