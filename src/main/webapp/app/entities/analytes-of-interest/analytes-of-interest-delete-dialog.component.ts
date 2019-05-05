import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAnalytesOfInterest } from 'app/shared/model/analytes-of-interest.model';
import { AnalytesOfInterestService } from './analytes-of-interest.service';

@Component({
    selector: 'jhi-analytes-of-interest-delete-dialog',
    templateUrl: './analytes-of-interest-delete-dialog.component.html'
})
export class AnalytesOfInterestDeleteDialogComponent {
    analytesOfInterest: IAnalytesOfInterest;

    constructor(
        protected analytesOfInterestService: AnalytesOfInterestService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.analytesOfInterestService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'analytesOfInterestListModification',
                content: 'Deleted an analytesOfInterest'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-analytes-of-interest-delete-popup',
    template: ''
})
export class AnalytesOfInterestDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ analytesOfInterest }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(AnalytesOfInterestDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.analytesOfInterest = analytesOfInterest;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/analytes-of-interest', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/analytes-of-interest', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}
