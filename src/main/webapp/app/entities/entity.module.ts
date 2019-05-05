import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
    imports: [
        RouterModule.forChild([
            {
                path: 'experiment',
                loadChildren: './experiment/experiment.module#CeExperimentModule'
            },
            {
                path: 'analyte',
                loadChildren: './analyte/analyte.module#CeAnalyteModule'
            },
            {
                path: 'experiment-analysis',
                loadChildren: './experiment-analysis/experiment-analysis.module#CeExperimentAnalysisModule'
            },
            {
                path: 'method',
                loadChildren: './method/method.module#CeMethodModule'
            },
            {
                path: 'bge-composition',
                loadChildren: './bge-composition/bge-composition.module#CeBGECompositionModule'
            },
            {
                path: 'capillary-type',
                loadChildren: './capillary-type/capillary-type.module#CeCapillaryTypeModule'
            },
            {
                path: 'experiment-peaks',
                loadChildren: './experiment-peaks/experiment-peaks.module#CeExperimentPeaksModule'
            },
            {
                path: 'matrix-list',
                loadChildren: './matrix-list/matrix-list.module#CeMatrixListModule'
            },
            {
                path: 'experiment-results',
                loadChildren: './experiment-results/experiment-results.module#CeExperimentResultsModule'
            },
            {
                path: 'analytes-of-interest',
                loadChildren: './analytes-of-interest/analytes-of-interest.module#CeAnalytesOfInterestModule'
            }
            /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
        ])
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CeEntityModule {}
