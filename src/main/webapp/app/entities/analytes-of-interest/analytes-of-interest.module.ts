import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { CeSharedModule } from 'app/shared';
import {
    AnalytesOfInterestComponent,
    AnalytesOfInterestDetailComponent,
    AnalytesOfInterestUpdateComponent,
    AnalytesOfInterestDeletePopupComponent,
    AnalytesOfInterestDeleteDialogComponent,
    analytesOfInterestRoute,
    analytesOfInterestPopupRoute
} from './';

const ENTITY_STATES = [...analytesOfInterestRoute, ...analytesOfInterestPopupRoute];

@NgModule({
    imports: [CeSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        AnalytesOfInterestComponent,
        AnalytesOfInterestDetailComponent,
        AnalytesOfInterestUpdateComponent,
        AnalytesOfInterestDeleteDialogComponent,
        AnalytesOfInterestDeletePopupComponent
    ],
    entryComponents: [
        AnalytesOfInterestComponent,
        AnalytesOfInterestUpdateComponent,
        AnalytesOfInterestDeleteDialogComponent,
        AnalytesOfInterestDeletePopupComponent
    ],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CeAnalytesOfInterestModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
