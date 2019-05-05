import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IExperiment } from 'app/shared/model/experiment.model';
import { AccountService } from 'app/core';
import { ExperimentService } from './experiment.service';

@Component({
    selector: 'jhi-experiment',
    templateUrl: './experiment.component.html'
})
export class ExperimentComponent implements OnInit, OnDestroy {
    experiments: IExperiment[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        protected experimentService: ExperimentService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

    loadAll() {
        this.experimentService
            .query()
            .pipe(
                filter((res: HttpResponse<IExperiment[]>) => res.ok),
                map((res: HttpResponse<IExperiment[]>) => res.body)
            )
            .subscribe(
                (res: IExperiment[]) => {
                    this.experiments = res;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInExperiments();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IExperiment) {
        return item.id;
    }

    registerChangeInExperiments() {
        this.eventSubscriber = this.eventManager.subscribe('experimentListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
