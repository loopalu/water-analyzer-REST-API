import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { CeSharedModule } from 'app/shared';
import {
    ExperimentComponent,
    ExperimentDetailComponent,
    ExperimentUpdateComponent,
    ExperimentDeletePopupComponent,
    ExperimentDeleteDialogComponent,
    experimentRoute,
    experimentPopupRoute
} from './';

const ENTITY_STATES = [...experimentRoute, ...experimentPopupRoute];

@NgModule({
    imports: [CeSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ExperimentComponent,
        ExperimentDetailComponent,
        ExperimentUpdateComponent,
        ExperimentDeleteDialogComponent,
        ExperimentDeletePopupComponent
    ],
    entryComponents: [ExperimentComponent, ExperimentUpdateComponent, ExperimentDeleteDialogComponent, ExperimentDeletePopupComponent],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CeExperimentModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
