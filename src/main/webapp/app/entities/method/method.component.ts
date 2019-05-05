import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IMethod } from 'app/shared/model/method.model';
import { AccountService } from 'app/core';
import { MethodService } from './method.service';

@Component({
    selector: 'jhi-method',
    templateUrl: './method.component.html'
})
export class MethodComponent implements OnInit, OnDestroy {
    methods: IMethod[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        protected methodService: MethodService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

    loadAll() {
        this.methodService
            .query()
            .pipe(
                filter((res: HttpResponse<IMethod[]>) => res.ok),
                map((res: HttpResponse<IMethod[]>) => res.body)
            )
            .subscribe(
                (res: IMethod[]) => {
                    this.methods = res;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInMethods();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IMethod) {
        return item.id;
    }

    registerChangeInMethods() {
        this.eventSubscriber = this.eventManager.subscribe('methodListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
