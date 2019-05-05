import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICapillaryType } from 'app/shared/model/capillary-type.model';
import { CapillaryTypeService } from './capillary-type.service';

@Component({
    selector: 'jhi-capillary-type-delete-dialog',
    templateUrl: './capillary-type-delete-dialog.component.html'
})
export class CapillaryTypeDeleteDialogComponent {
    capillaryType: ICapillaryType;

    constructor(
        protected capillaryTypeService: CapillaryTypeService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.capillaryTypeService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'capillaryTypeListModification',
                content: 'Deleted an capillaryType'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-capillary-type-delete-popup',
    template: ''
})
export class CapillaryTypeDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ capillaryType }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(CapillaryTypeDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.capillaryType = capillaryType;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/capillary-type', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/capillary-type', { outlets: { popup: null } }]);
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
