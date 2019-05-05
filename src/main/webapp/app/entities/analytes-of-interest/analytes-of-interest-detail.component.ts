import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAnalytesOfInterest } from 'app/shared/model/analytes-of-interest.model';

@Component({
    selector: 'jhi-analytes-of-interest-detail',
    templateUrl: './analytes-of-interest-detail.component.html'
})
export class AnalytesOfInterestDetailComponent implements OnInit {
    analytesOfInterest: IAnalytesOfInterest;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ analytesOfInterest }) => {
            this.analytesOfInterest = analytesOfInterest;
        });
    }

    previousState() {
        window.history.back();
    }
}
