import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IExperiment } from 'app/shared/model/experiment.model';
import { ExperimentService } from './experiment.service';

@Component({
    selector: 'jhi-experiment-delete-dialog',
    templateUrl: './experiment-delete-dialog.component.html'
})
export class ExperimentDeleteDialogComponent {
    experiment: IExperiment;

    constructor(
        protected experimentService: ExperimentService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.experimentService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'experimentListModification',
                content: 'Deleted an experiment'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-experiment-delete-popup',
    template: ''
})
export class ExperimentDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ experiment }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(ExperimentDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.experiment = experiment;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/experiment', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/experiment', { outlets: { popup: null } }]);
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
