import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IMatrixList } from 'app/shared/model/matrix-list.model';

@Component({
    selector: 'jhi-matrix-list-detail',
    templateUrl: './matrix-list-detail.component.html'
})
export class MatrixListDetailComponent implements OnInit {
    matrixList: IMatrixList;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ matrixList }) => {
            this.matrixList = matrixList;
        });
    }

    previousState() {
        window.history.back();
    }
}
