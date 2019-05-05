import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { CeSharedModule } from 'app/shared';
import {
    ExperimentPeaksComponent,
    ExperimentPeaksDetailComponent,
    ExperimentPeaksUpdateComponent,
    ExperimentPeaksDeletePopupComponent,
    ExperimentPeaksDeleteDialogComponent,
    experimentPeaksRoute,
    experimentPeaksPopupRoute
} from './';

const ENTITY_STATES = [...experimentPeaksRoute, ...experimentPeaksPopupRoute];

@NgModule({
    imports: [CeSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ExperimentPeaksComponent,
        ExperimentPeaksDetailComponent,
        ExperimentPeaksUpdateComponent,
        ExperimentPeaksDeleteDialogComponent,
        ExperimentPeaksDeletePopupComponent
    ],
    entryComponents: [
        ExperimentPeaksComponent,
        ExperimentPeaksUpdateComponent,
        ExperimentPeaksDeleteDialogComponent,
        ExperimentPeaksDeletePopupComponent
    ],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CeExperimentPeaksModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
