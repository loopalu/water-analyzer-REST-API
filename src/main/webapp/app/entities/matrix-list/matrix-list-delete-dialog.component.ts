import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IMatrixList } from 'app/shared/model/matrix-list.model';
import { MatrixListService } from './matrix-list.service';

@Component({
    selector: 'jhi-matrix-list-delete-dialog',
    templateUrl: './matrix-list-delete-dialog.component.html'
})
export class MatrixListDeleteDialogComponent {
    matrixList: IMatrixList;

    constructor(
        protected matrixListService: MatrixListService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.matrixListService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'matrixListListModification',
                content: 'Deleted an matrixList'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-matrix-list-delete-popup',
    template: ''
})
export class MatrixListDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ matrixList }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(MatrixListDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.matrixList = matrixList;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/matrix-list', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/matrix-list', { outlets: { popup: null } }]);
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
