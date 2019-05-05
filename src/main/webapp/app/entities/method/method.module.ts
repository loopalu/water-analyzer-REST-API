import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { CeSharedModule } from 'app/shared';
import {
    MethodComponent,
    MethodDetailComponent,
    MethodUpdateComponent,
    MethodDeletePopupComponent,
    MethodDeleteDialogComponent,
    methodRoute,
    methodPopupRoute
} from './';

const ENTITY_STATES = [...methodRoute, ...methodPopupRoute];

@NgModule({
    imports: [CeSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [MethodComponent, MethodDetailComponent, MethodUpdateComponent, MethodDeleteDialogComponent, MethodDeletePopupComponent],
    entryComponents: [MethodComponent, MethodUpdateComponent, MethodDeleteDialogComponent, MethodDeletePopupComponent],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CeMethodModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
