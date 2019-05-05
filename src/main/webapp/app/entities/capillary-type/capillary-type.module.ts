import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { CeSharedModule } from 'app/shared';
import {
    CapillaryTypeComponent,
    CapillaryTypeDetailComponent,
    CapillaryTypeUpdateComponent,
    CapillaryTypeDeletePopupComponent,
    CapillaryTypeDeleteDialogComponent,
    capillaryTypeRoute,
    capillaryTypePopupRoute
} from './';

const ENTITY_STATES = [...capillaryTypeRoute, ...capillaryTypePopupRoute];

@NgModule({
    imports: [CeSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        CapillaryTypeComponent,
        CapillaryTypeDetailComponent,
        CapillaryTypeUpdateComponent,
        CapillaryTypeDeleteDialogComponent,
        CapillaryTypeDeletePopupComponent
    ],
    entryComponents: [
        CapillaryTypeComponent,
        CapillaryTypeUpdateComponent,
        CapillaryTypeDeleteDialogComponent,
        CapillaryTypeDeletePopupComponent
    ],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CeCapillaryTypeModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
