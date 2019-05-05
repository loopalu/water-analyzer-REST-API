import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IExperimentAnalysis } from 'app/shared/model/experiment-analysis.model';
import { ExperimentAnalysisService } from './experiment-analysis.service';
import { IExperiment } from 'app/shared/model/experiment.model';
import { ExperimentService } from 'app/entities/experiment';

@Component({
    selector: 'jhi-experiment-analysis-update',
    templateUrl: './experiment-analysis-update.component.html'
})
export class ExperimentAnalysisUpdateComponent implements OnInit {
    experimentAnalysis: IExperimentAnalysis;
    isSaving: boolean;

    experiments: IExperiment[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected experimentAnalysisService: ExperimentAnalysisService,
        protected experimentService: ExperimentService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ experimentAnalysis }) => {
            this.experimentAnalysis = experimentAnalysis;
        });
        this.experimentService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IExperiment[]>) => mayBeOk.ok),
                map((response: HttpResponse<IExperiment[]>) => response.body)
            )
            .subscribe((res: IExperiment[]) => (this.experiments = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.experimentAnalysis.id !== undefined) {
            this.subscribeToSaveResponse(this.experimentAnalysisService.update(this.experimentAnalysis));
        } else {
            this.subscribeToSaveResponse(this.experimentAnalysisService.create(this.experimentAnalysis));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IExperimentAnalysis>>) {
        result.subscribe((res: HttpResponse<IExperimentAnalysis>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackExperimentById(index: number, item: IExperiment) {
        return item.id;
    }
}
