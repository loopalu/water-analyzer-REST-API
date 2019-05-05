import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAnalyte } from 'app/shared/model/analyte.model';

@Component({
    selector: 'jhi-analyte-detail',
    templateUrl: './analyte-detail.component.html'
})
export class AnalyteDetailComponent implements OnInit {
    analyte: IAnalyte;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ analyte }) => {
            this.analyte = analyte;
        });
    }

    previousState() {
        window.history.back();
    }
}
