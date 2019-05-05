import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { CeSharedModule } from 'app/shared';
import {
    MatrixListComponent,
    MatrixListDetailComponent,
    MatrixListUpdateComponent,
    MatrixListDeletePopupComponent,
    MatrixListDeleteDialogComponent,
    matrixListRoute,
    matrixListPopupRoute
} from './';

const ENTITY_STATES = [...matrixListRoute, ...matrixListPopupRoute];

@NgModule({
    imports: [CeSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        MatrixListComponent,
        MatrixListDetailComponent,
        MatrixListUpdateComponent,
        MatrixListDeleteDialogComponent,
        MatrixListDeletePopupComponent
    ],
    entryComponents: [MatrixListComponent, MatrixListUpdateComponent, MatrixListDeleteDialogComponent, MatrixListDeletePopupComponent],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CeMatrixListModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
