import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { CeSharedModule } from 'app/shared';
import {
    BGECompositionComponent,
    BGECompositionDetailComponent,
    BGECompositionUpdateComponent,
    BGECompositionDeletePopupComponent,
    BGECompositionDeleteDialogComponent,
    bGECompositionRoute,
    bGECompositionPopupRoute
} from './';

const ENTITY_STATES = [...bGECompositionRoute, ...bGECompositionPopupRoute];

@NgModule({
    imports: [CeSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        BGECompositionComponent,
        BGECompositionDetailComponent,
        BGECompositionUpdateComponent,
        BGECompositionDeleteDialogComponent,
        BGECompositionDeletePopupComponent
    ],
    entryComponents: [
        BGECompositionComponent,
        BGECompositionUpdateComponent,
        BGECompositionDeleteDialogComponent,
        BGECompositionDeletePopupComponent
    ],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CeBGECompositionModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
