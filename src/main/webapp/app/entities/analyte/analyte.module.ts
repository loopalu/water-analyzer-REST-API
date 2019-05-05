import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { CeSharedModule } from 'app/shared';
import {
    AnalyteComponent,
    AnalyteDetailComponent,
    AnalyteUpdateComponent,
    AnalyteDeletePopupComponent,
    AnalyteDeleteDialogComponent,
    analyteRoute,
    analytePopupRoute
} from './';

const ENTITY_STATES = [...analyteRoute, ...analytePopupRoute];

@NgModule({
    imports: [CeSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        AnalyteComponent,
        AnalyteDetailComponent,
        AnalyteUpdateComponent,
        AnalyteDeleteDialogComponent,
        AnalyteDeletePopupComponent
    ],
    entryComponents: [AnalyteComponent, AnalyteUpdateComponent, AnalyteDeleteDialogComponent, AnalyteDeletePopupComponent],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CeAnalyteModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
