import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { CeSharedModule } from 'app/shared';
import {
    ExperimentResultsComponent,
    ExperimentResultsDetailComponent,
    ExperimentResultsUpdateComponent,
    ExperimentResultsDeletePopupComponent,
    ExperimentResultsDeleteDialogComponent,
    experimentResultsRoute,
    experimentResultsPopupRoute
} from './';

const ENTITY_STATES = [...experimentResultsRoute, ...experimentResultsPopupRoute];

@NgModule({
    imports: [CeSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ExperimentResultsComponent,
        ExperimentResultsDetailComponent,
        ExperimentResultsUpdateComponent,
        ExperimentResultsDeleteDialogComponent,
        ExperimentResultsDeletePopupComponent
    ],
    entryComponents: [
        ExperimentResultsComponent,
        ExperimentResultsUpdateComponent,
        ExperimentResultsDeleteDialogComponent,
        ExperimentResultsDeletePopupComponent
    ],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CeExperimentResultsModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
