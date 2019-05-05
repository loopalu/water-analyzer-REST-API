import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IExperimentResults } from 'app/shared/model/experiment-results.model';

@Component({
    selector: 'jhi-experiment-results-detail',
    templateUrl: './experiment-results-detail.component.html'
})
export class ExperimentResultsDetailComponent implements OnInit {
    experimentResults: IExperimentResults;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ experimentResults }) => {
            this.experimentResults = experimentResults;
        });
    }

    previousState() {
        window.history.back();
    }
}
