import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IAnalyte } from 'app/shared/model/analyte.model';
import { AccountService } from 'app/core';
import { AnalyteService } from './analyte.service';

@Component({
    selector: 'jhi-analyte',
    templateUrl: './analyte.component.html'
})
export class AnalyteComponent implements OnInit, OnDestroy {
    analytes: IAnalyte[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        protected analyteService: AnalyteService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

    loadAll() {
        this.analyteService
            .query()
            .pipe(
                filter((res: HttpResponse<IAnalyte[]>) => res.ok),
                map((res: HttpResponse<IAnalyte[]>) => res.body)
            )
            .subscribe(
                (res: IAnalyte[]) => {
                    this.analytes = res;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInAnalytes();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IAnalyte) {
        return item.id;
    }

    registerChangeInAnalytes() {
        this.eventSubscriber = this.eventManager.subscribe('analyteListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
