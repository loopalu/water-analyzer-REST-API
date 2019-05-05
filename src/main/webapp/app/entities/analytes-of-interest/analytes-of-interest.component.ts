import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IAnalytesOfInterest } from 'app/shared/model/analytes-of-interest.model';
import { AccountService } from 'app/core';
import { AnalytesOfInterestService } from './analytes-of-interest.service';

@Component({
    selector: 'jhi-analytes-of-interest',
    templateUrl: './analytes-of-interest.component.html'
})
export class AnalytesOfInterestComponent implements OnInit, OnDestroy {
    analytesOfInterests: IAnalytesOfInterest[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        protected analytesOfInterestService: AnalytesOfInterestService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

    loadAll() {
        this.analytesOfInterestService
            .query()
            .pipe(
                filter((res: HttpResponse<IAnalytesOfInterest[]>) => res.ok),
                map((res: HttpResponse<IAnalytesOfInterest[]>) => res.body)
            )
            .subscribe(
                (res: IAnalytesOfInterest[]) => {
                    this.analytesOfInterests = res;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInAnalytesOfInterests();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IAnalytesOfInterest) {
        return item.id;
    }

    registerChangeInAnalytesOfInterests() {
        this.eventSubscriber = this.eventManager.subscribe('analytesOfInterestListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
