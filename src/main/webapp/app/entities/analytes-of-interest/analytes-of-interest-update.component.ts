import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IAnalytesOfInterest } from 'app/shared/model/analytes-of-interest.model';
import { AnalytesOfInterestService } from './analytes-of-interest.service';
import { IMethod } from 'app/shared/model/method.model';
import { MethodService } from 'app/entities/method';
import { IAnalyte } from 'app/shared/model/analyte.model';
import { AnalyteService } from 'app/entities/analyte';

@Component({
    selector: 'jhi-analytes-of-interest-update',
    templateUrl: './analytes-of-interest-update.component.html'
})
export class AnalytesOfInterestUpdateComponent implements OnInit {
    analytesOfInterest: IAnalytesOfInterest;
    isSaving: boolean;

    methods: IMethod[];

    analytes: IAnalyte[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected analytesOfInterestService: AnalytesOfInterestService,
        protected methodService: MethodService,
        protected analyteService: AnalyteService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ analytesOfInterest }) => {
            this.analytesOfInterest = analytesOfInterest;
        });
        this.methodService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IMethod[]>) => mayBeOk.ok),
                map((response: HttpResponse<IMethod[]>) => response.body)
            )
            .subscribe((res: IMethod[]) => (this.methods = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.analyteService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IAnalyte[]>) => mayBeOk.ok),
                map((response: HttpResponse<IAnalyte[]>) => response.body)
            )
            .subscribe((res: IAnalyte[]) => (this.analytes = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.analytesOfInterest.id !== undefined) {
            this.subscribeToSaveResponse(this.analytesOfInterestService.update(this.analytesOfInterest));
        } else {
            this.subscribeToSaveResponse(this.analytesOfInterestService.create(this.analytesOfInterest));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IAnalytesOfInterest>>) {
        result.subscribe((res: HttpResponse<IAnalytesOfInterest>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackAnalyteById(index: number, item: IAnalyte) {
        return item.id;
    }
}
