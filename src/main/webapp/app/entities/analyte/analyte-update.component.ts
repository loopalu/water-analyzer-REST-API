import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { IAnalyte } from 'app/shared/model/analyte.model';
import { AnalyteService } from './analyte.service';

@Component({
    selector: 'jhi-analyte-update',
    templateUrl: './analyte-update.component.html'
})
export class AnalyteUpdateComponent implements OnInit {
    analyte: IAnalyte;
    isSaving: boolean;

    constructor(protected analyteService: AnalyteService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ analyte }) => {
            this.analyte = analyte;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.analyte.id !== undefined) {
            this.subscribeToSaveResponse(this.analyteService.update(this.analyte));
        } else {
            this.subscribeToSaveResponse(this.analyteService.create(this.analyte));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IAnalyte>>) {
        result.subscribe((res: HttpResponse<IAnalyte>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
