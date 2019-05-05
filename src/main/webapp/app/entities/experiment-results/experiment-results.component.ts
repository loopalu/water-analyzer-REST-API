import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IExperimentResults } from 'app/shared/model/experiment-results.model';
import { AccountService } from 'app/core';
import { ExperimentResultsService } from './experiment-results.service';

@Component({
    selector: 'jhi-experiment-results',
    templateUrl: './experiment-results.component.html'
})
export class ExperimentResultsComponent implements OnInit, OnDestroy {
    experimentResults: IExperimentResults[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        protected experimentResultsService: ExperimentResultsService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

    loadAll() {
        this.experimentResultsService
            .query()
            .pipe(
                filter((res: HttpResponse<IExperimentResults[]>) => res.ok),
                map((res: HttpResponse<IExperimentResults[]>) => res.body)
            )
            .subscribe(
                (res: IExperimentResults[]) => {
                    this.experimentResults = res;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInExperimentResults();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IExperimentResults) {
        return item.id;
    }

    registerChangeInExperimentResults() {
        this.eventSubscriber = this.eventManager.subscribe('experimentResultsListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
