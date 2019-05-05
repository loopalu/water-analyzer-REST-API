import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IExperimentAnalysis } from 'app/shared/model/experiment-analysis.model';
import { ExperimentAnalysisService } from './experiment-analysis.service';

@Component({
    selector: 'jhi-experiment-analysis-delete-dialog',
    templateUrl: './experiment-analysis-delete-dialog.component.html'
})
export class ExperimentAnalysisDeleteDialogComponent {
    experimentAnalysis: IExperimentAnalysis;

    constructor(
        protected experimentAnalysisService: ExperimentAnalysisService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.experimentAnalysisService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'experimentAnalysisListModification',
                content: 'Deleted an experimentAnalysis'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-experiment-analysis-delete-popup',
    template: ''
})
export class ExperimentAnalysisDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ experimentAnalysis }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(ExperimentAnalysisDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.experimentAnalysis = experimentAnalysis;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/experiment-analysis', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/experiment-analysis', { outlets: { popup: null } }]);
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
