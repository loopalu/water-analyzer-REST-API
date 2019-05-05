import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IExperimentPeaks } from 'app/shared/model/experiment-peaks.model';
import { ExperimentPeaksService } from './experiment-peaks.service';
import { IAnalyte } from 'app/shared/model/analyte.model';
import { AnalyteService } from 'app/entities/analyte';
import { IExperimentAnalysis } from 'app/shared/model/experiment-analysis.model';
import { ExperimentAnalysisService } from 'app/entities/experiment-analysis';

@Component({
    selector: 'jhi-experiment-peaks-update',
    templateUrl: './experiment-peaks-update.component.html'
})
export class ExperimentPeaksUpdateComponent implements OnInit {
    experimentPeaks: IExperimentPeaks;
    isSaving: boolean;

    analytes: IAnalyte[];

    experimentanalyses: IExperimentAnalysis[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected experimentPeaksService: ExperimentPeaksService,
        protected analyteService: AnalyteService,
        protected experimentAnalysisService: ExperimentAnalysisService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ experimentPeaks }) => {
            this.experimentPeaks = experimentPeaks;
        });
        this.analyteService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IAnalyte[]>) => mayBeOk.ok),
                map((response: HttpResponse<IAnalyte[]>) => response.body)
            )
            .subscribe((res: IAnalyte[]) => (this.analytes = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.experimentAnalysisService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IExperimentAnalysis[]>) => mayBeOk.ok),
                map((response: HttpResponse<IExperimentAnalysis[]>) => response.body)
            )
            .subscribe(
                (res: IExperimentAnalysis[]) => (this.experimentanalyses = res),
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.experimentPeaks.id !== undefined) {
            this.subscribeToSaveResponse(this.experimentPeaksService.update(this.experimentPeaks));
        } else {
            this.subscribeToSaveResponse(this.experimentPeaksService.create(this.experimentPeaks));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IExperimentPeaks>>) {
        result.subscribe((res: HttpResponse<IExperimentPeaks>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackAnalyteById(index: number, item: IAnalyte) {
        return item.id;
    }

    trackExperimentAnalysisById(index: number, item: IExperimentAnalysis) {
        return item.id;
    }
}
