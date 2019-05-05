import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IBGEComposition } from 'app/shared/model/bge-composition.model';
import { BGECompositionService } from './bge-composition.service';
import { IMethod } from 'app/shared/model/method.model';
import { MethodService } from 'app/entities/method';

@Component({
    selector: 'jhi-bge-composition-update',
    templateUrl: './bge-composition-update.component.html'
})
export class BGECompositionUpdateComponent implements OnInit {
    bGEComposition: IBGEComposition;
    isSaving: boolean;

    methods: IMethod[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected bGECompositionService: BGECompositionService,
        protected methodService: MethodService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ bGEComposition }) => {
            this.bGEComposition = bGEComposition;
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
        if (this.bGEComposition.id !== undefined) {
            this.subscribeToSaveResponse(this.bGECompositionService.update(this.bGEComposition));
        } else {
            this.subscribeToSaveResponse(this.bGECompositionService.create(this.bGEComposition));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IBGEComposition>>) {
        result.subscribe((res: HttpResponse<IBGEComposition>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
