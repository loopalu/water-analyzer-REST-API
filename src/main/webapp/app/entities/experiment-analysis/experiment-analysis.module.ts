import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { CeSharedModule } from 'app/shared';
import {
    ExperimentAnalysisComponent,
    ExperimentAnalysisDetailComponent,
    ExperimentAnalysisUpdateComponent,
    ExperimentAnalysisDeletePopupComponent,
    ExperimentAnalysisDeleteDialogComponent,
    experimentAnalysisRoute,
    experimentAnalysisPopupRoute
} from './';

const ENTITY_STATES = [...experimentAnalysisRoute, ...experimentAnalysisPopupRoute];

@NgModule({
    imports: [CeSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ExperimentAnalysisComponent,
        ExperimentAnalysisDetailComponent,
        ExperimentAnalysisUpdateComponent,
        ExperimentAnalysisDeleteDialogComponent,
        ExperimentAnalysisDeletePopupComponent
    ],
    entryComponents: [
        ExperimentAnalysisComponent,
        ExperimentAnalysisUpdateComponent,
        ExperimentAnalysisDeleteDialogComponent,
        ExperimentAnalysisDeletePopupComponent
    ],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CeExperimentAnalysisModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
