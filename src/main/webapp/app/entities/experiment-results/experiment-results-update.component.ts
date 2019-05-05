import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IExperimentResults } from 'app/shared/model/experiment-results.model';
import { ExperimentResultsService } from './experiment-results.service';
import { IExperiment } from 'app/shared/model/experiment.model';
import { ExperimentService } from 'app/entities/experiment';

@Component({
    selector: 'jhi-experiment-results-update',
    templateUrl: './experiment-results-update.component.html'
})
export class ExperimentResultsUpdateComponent implements OnInit {
    experimentResults: IExperimentResults;
    isSaving: boolean;

    experiments: IExperiment[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected experimentResultsService: ExperimentResultsService,
        protected experimentService: ExperimentService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ experimentResults }) => {
            this.experimentResults = experimentResults;
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
        if (this.experimentResults.id !== undefined) {
            this.subscribeToSaveResponse(this.experimentResultsService.update(this.experimentResults));
        } else {
            this.subscribeToSaveResponse(this.experimentResultsService.create(this.experimentResults));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IExperimentResults>>) {
        result.subscribe((res: HttpResponse<IExperimentResults>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
