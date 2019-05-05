import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IBGEComposition } from 'app/shared/model/bge-composition.model';

@Component({
    selector: 'jhi-bge-composition-detail',
    templateUrl: './bge-composition-detail.component.html'
})
export class BGECompositionDetailComponent implements OnInit {
    bGEComposition: IBGEComposition;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ bGEComposition }) => {
            this.bGEComposition = bGEComposition;
        });
    }

    previousState() {
        window.history.back();
    }
}
