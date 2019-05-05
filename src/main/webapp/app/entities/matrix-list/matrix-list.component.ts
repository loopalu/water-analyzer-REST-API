import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IMatrixList } from 'app/shared/model/matrix-list.model';
import { AccountService } from 'app/core';
import { MatrixListService } from './matrix-list.service';

@Component({
    selector: 'jhi-matrix-list',
    templateUrl: './matrix-list.component.html'
})
export class MatrixListComponent implements OnInit, OnDestroy {
    matrixLists: IMatrixList[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        protected matrixListService: MatrixListService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

    loadAll() {
        this.matrixListService
            .query()
            .pipe(
                filter((res: HttpResponse<IMatrixList[]>) => res.ok),
                map((res: HttpResponse<IMatrixList[]>) => res.body)
            )
            .subscribe(
                (res: IMatrixList[]) => {
                    this.matrixLists = res;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInMatrixLists();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IMatrixList) {
        return item.id;
    }

    registerChangeInMatrixLists() {
        this.eventSubscriber = this.eventManager.subscribe('matrixListListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
