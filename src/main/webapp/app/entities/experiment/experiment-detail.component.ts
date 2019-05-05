import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IExperiment } from 'app/shared/model/experiment.model';

@Component({
    selector: 'jhi-experiment-detail',
    templateUrl: './experiment-detail.component.html'
})
export class ExperimentDetailComponent implements OnInit {
    experiment: IExperiment;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ experiment }) => {
            this.experiment = experiment;
        });
    }

    previousState() {
        window.history.back();
    }
}
