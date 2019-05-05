import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { IMatrixList } from 'app/shared/model/matrix-list.model';
import { MatrixListService } from './matrix-list.service';

@Component({
    selector: 'jhi-matrix-list-update',
    templateUrl: './matrix-list-update.component.html'
})
export class MatrixListUpdateComponent implements OnInit {
    matrixList: IMatrixList;
    isSaving: boolean;

    constructor(protected matrixListService: MatrixListService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ matrixList }) => {
            this.matrixList = matrixList;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.matrixList.id !== undefined) {
            this.subscribeToSaveResponse(this.matrixListService.update(this.matrixList));
        } else {
            this.subscribeToSaveResponse(this.matrixListService.create(this.matrixList));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IMatrixList>>) {
        result.subscribe((res: HttpResponse<IMatrixList>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
