import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IMethod } from 'app/shared/model/method.model';
import { MethodService } from './method.service';
import { ICapillaryType } from 'app/shared/model/capillary-type.model';
import { CapillaryTypeService } from 'app/entities/capillary-type';
import { IMatrixList } from 'app/shared/model/matrix-list.model';
import { MatrixListService } from 'app/entities/matrix-list';

@Component({
    selector: 'jhi-method-update',
    templateUrl: './method-update.component.html'
})
export class MethodUpdateComponent implements OnInit {
    method: IMethod;
    isSaving: boolean;

    capillarytypes: ICapillaryType[];

    matrixlists: IMatrixList[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected methodService: MethodService,
        protected capillaryTypeService: CapillaryTypeService,
        protected matrixListService: MatrixListService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ method }) => {
            this.method = method;
        });
        this.capillaryTypeService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<ICapillaryType[]>) => mayBeOk.ok),
                map((response: HttpResponse<ICapillaryType[]>) => response.body)
            )
            .subscribe((res: ICapillaryType[]) => (this.capillarytypes = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.matrixListService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IMatrixList[]>) => mayBeOk.ok),
                map((response: HttpResponse<IMatrixList[]>) => response.body)
            )
            .subscribe((res: IMatrixList[]) => (this.matrixlists = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.method.id !== undefined) {
            this.subscribeToSaveResponse(this.methodService.update(this.method));
        } else {
            this.subscribeToSaveResponse(this.methodService.create(this.method));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IMethod>>) {
        result.subscribe((res: HttpResponse<IMethod>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackCapillaryTypeById(index: number, item: ICapillaryType) {
        return item.id;
    }

    trackMatrixListById(index: number, item: IMatrixList) {
        return item.id;
    }
}
