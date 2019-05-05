import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IExperimentPeaks } from 'app/shared/model/experiment-peaks.model';

@Component({
    selector: 'jhi-experiment-peaks-detail',
    templateUrl: './experiment-peaks-detail.component.html'
})
export class ExperimentPeaksDetailComponent implements OnInit {
    experimentPeaks: IExperimentPeaks;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ experimentPeaks }) => {
            this.experimentPeaks = experimentPeaks;
        });
    }

    previousState() {
        window.history.back();
    }
}
