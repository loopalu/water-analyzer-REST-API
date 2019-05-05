import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICapillaryType } from 'app/shared/model/capillary-type.model';

@Component({
    selector: 'jhi-capillary-type-detail',
    templateUrl: './capillary-type-detail.component.html'
})
export class CapillaryTypeDetailComponent implements OnInit {
    capillaryType: ICapillaryType;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ capillaryType }) => {
            this.capillaryType = capillaryType;
        });
    }

    previousState() {
        window.history.back();
    }
}
