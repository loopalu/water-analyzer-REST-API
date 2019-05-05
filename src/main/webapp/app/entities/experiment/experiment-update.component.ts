import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { IExperiment } from 'app/shared/model/experiment.model';
import { ExperimentService } from './experiment.service';
import { IMethod } from 'app/shared/model/method.model';
import { MethodService } from 'app/entities/method';

@Component({
    selector: 'jhi-experiment-update',
    templateUrl: './experiment-update.component.html'
})
export class ExperimentUpdateComponent implements OnInit {
    experiment: IExperiment;
    isSaving: boolean;

    methods: IMethod[];
    experimentTime: string;

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected experimentService: ExperimentService,
        protected methodService: MethodService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ experiment }) => {
            this.experiment = experiment;
            this.experimentTime = this.experiment.experimentTime != null ? this.experiment.experimentTime.format(DATE_TIME_FORMAT) : null;
        });
        this.methodService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IMethod[]>) => mayBeOk.ok),
                map((response: HttpResponse<IMethod[]>) => response.body)
            )
            .subscribe((res: IMethod[]) => (this.methods = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.experiment.experimentTime = this.experimentTime != null ? moment(this.experimentTime, DATE_TIME_FORMAT) : null;
        if (this.experiment.id !== undefined) {
            this.subscribeToSaveResponse(this.experimentService.update(this.experiment));
        } else {
            this.subscribeToSaveResponse(this.experimentService.create(this.experiment));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IExperiment>>) {
        result.subscribe((res: HttpResponse<IExperiment>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackMethodById(index: number, item: IMethod) {
        return item.id;
    }
}
