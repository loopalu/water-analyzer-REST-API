import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ICapillaryType } from 'app/shared/model/capillary-type.model';
import { CapillaryTypeService } from './capillary-type.service';

@Component({
    selector: 'jhi-capillary-type-update',
    templateUrl: './capillary-type-update.component.html'
})
export class CapillaryTypeUpdateComponent implements OnInit {
    capillaryType: ICapillaryType;
    isSaving: boolean;

    constructor(protected capillaryTypeService: CapillaryTypeService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ capillaryType }) => {
            this.capillaryType = capillaryType;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.capillaryType.id !== undefined) {
            this.subscribeToSaveResponse(this.capillaryTypeService.update(this.capillaryType));
        } else {
            this.subscribeToSaveResponse(this.capillaryTypeService.create(this.capillaryType));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<ICapillaryType>>) {
        result.subscribe((res: HttpResponse<ICapillaryType>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
