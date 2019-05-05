import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IBGEComposition } from 'app/shared/model/bge-composition.model';
import { AccountService } from 'app/core';
import { BGECompositionService } from './bge-composition.service';

@Component({
    selector: 'jhi-bge-composition',
    templateUrl: './bge-composition.component.html'
})
export class BGECompositionComponent implements OnInit, OnDestroy {
    bGECompositions: IBGEComposition[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        protected bGECompositionService: BGECompositionService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

    loadAll() {
        this.bGECompositionService
            .query()
            .pipe(
                filter((res: HttpResponse<IBGEComposition[]>) => res.ok),
                map((res: HttpResponse<IBGEComposition[]>) => res.body)
            )
            .subscribe(
                (res: IBGEComposition[]) => {
                    this.bGECompositions = res;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInBGECompositions();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IBGEComposition) {
        return item.id;
    }

    registerChangeInBGECompositions() {
        this.eventSubscriber = this.eventManager.subscribe('bGECompositionListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
