import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IMethod } from 'app/shared/model/method.model';

@Component({
    selector: 'jhi-method-detail',
    templateUrl: './method-detail.component.html'
})
export class MethodDetailComponent implements OnInit {
    method: IMethod;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ method }) => {
            this.method = method;
        });
    }

    previousState() {
        window.history.back();
    }
}
