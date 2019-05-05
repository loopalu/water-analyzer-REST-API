import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAnalyte } from 'app/shared/model/analyte.model';
import { AnalyteService } from './analyte.service';

@Component({
    selector: 'jhi-analyte-delete-dialog',
    templateUrl: './analyte-delete-dialog.component.html'
})
export class AnalyteDeleteDialogComponent {
    analyte: IAnalyte;

    constructor(protected analyteService: AnalyteService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.analyteService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'analyteListModification',
                content: 'Deleted an analyte'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-analyte-delete-popup',
    template: ''
})
export class AnalyteDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ analyte }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(AnalyteDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.analyte = analyte;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/analyte', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/analyte', { outlets: { popup: null } }]);
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
