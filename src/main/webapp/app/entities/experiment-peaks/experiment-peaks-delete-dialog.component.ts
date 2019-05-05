import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IExperimentPeaks } from 'app/shared/model/experiment-peaks.model';
import { ExperimentPeaksService } from './experiment-peaks.service';

@Component({
    selector: 'jhi-experiment-peaks-delete-dialog',
    templateUrl: './experiment-peaks-delete-dialog.component.html'
})
export class ExperimentPeaksDeleteDialogComponent {
    experimentPeaks: IExperimentPeaks;

    constructor(
        protected experimentPeaksService: ExperimentPeaksService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.experimentPeaksService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'experimentPeaksListModification',
                content: 'Deleted an experimentPeaks'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-experiment-peaks-delete-popup',
    template: ''
})
export class ExperimentPeaksDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ experimentPeaks }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(ExperimentPeaksDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.experimentPeaks = experimentPeaks;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/experiment-peaks', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/experiment-peaks', { outlets: { popup: null } }]);
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
