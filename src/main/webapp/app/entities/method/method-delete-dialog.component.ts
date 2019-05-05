import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IMethod } from 'app/shared/model/method.model';
import { MethodService } from './method.service';

@Component({
    selector: 'jhi-method-delete-dialog',
    templateUrl: './method-delete-dialog.component.html'
})
export class MethodDeleteDialogComponent {
    method: IMethod;

    constructor(protected methodService: MethodService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.methodService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'methodListModification',
                content: 'Deleted an method'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-method-delete-popup',
    template: ''
})
export class MethodDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ method }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(MethodDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.method = method;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/method', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/method', { outlets: { popup: null } }]);
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
