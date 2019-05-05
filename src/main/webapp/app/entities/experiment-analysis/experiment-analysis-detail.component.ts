import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IExperimentAnalysis } from 'app/shared/model/experiment-analysis.model';

@Component({
    selector: 'jhi-experiment-analysis-detail',
    templateUrl: './experiment-analysis-detail.component.html'
})
export class ExperimentAnalysisDetailComponent implements OnInit {
    experimentAnalysis: IExperimentAnalysis;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ experimentAnalysis }) => {
            this.experimentAnalysis = experimentAnalysis;
        });
    }

    previousState() {
        window.history.back();
    }
}
