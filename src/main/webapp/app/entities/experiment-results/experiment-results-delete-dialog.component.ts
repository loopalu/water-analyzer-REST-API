import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IExperimentResults } from 'app/shared/model/experiment-results.model';
import { ExperimentResultsService } from './experiment-results.service';

@Component({
    selector: 'jhi-experiment-results-delete-dialog',
    templateUrl: './experiment-results-delete-dialog.component.html'
})
export class ExperimentResultsDeleteDialogComponent {
    experimentResults: IExperimentResults;

    constructor(
        protected experimentResultsService: ExperimentResultsService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.experimentResultsService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'experimentResultsListModification',
                content: 'Deleted an experimentResults'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-experiment-results-delete-popup',
    template: ''
})
export class ExperimentResultsDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ experimentResults }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(ExperimentResultsDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.experimentResults = experimentResults;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/experiment-results', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/experiment-results', { outlets: { popup: null } }]);
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
