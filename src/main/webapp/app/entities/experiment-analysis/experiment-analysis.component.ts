import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IExperimentAnalysis } from 'app/shared/model/experiment-analysis.model';
import { AccountService } from 'app/core';
import { ExperimentAnalysisService } from './experiment-analysis.service';

@Component({
    selector: 'jhi-experiment-analysis',
    templateUrl: './experiment-analysis.component.html'
})
export class ExperimentAnalysisComponent implements OnInit, OnDestroy {
    experimentAnalyses: IExperimentAnalysis[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        protected experimentAnalysisService: ExperimentAnalysisService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

    loadAll() {
        this.experimentAnalysisService
            .query()
            .pipe(
                filter((res: HttpResponse<IExperimentAnalysis[]>) => res.ok),
                map((res: HttpResponse<IExperimentAnalysis[]>) => res.body)
            )
            .subscribe(
                (res: IExperimentAnalysis[]) => {
                    this.experimentAnalyses = res;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInExperimentAnalyses();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IExperimentAnalysis) {
        return item.id;
    }

    registerChangeInExperimentAnalyses() {
        this.eventSubscriber = this.eventManager.subscribe('experimentAnalysisListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
