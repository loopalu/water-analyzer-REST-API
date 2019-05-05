import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IBGEComposition } from 'app/shared/model/bge-composition.model';
import { BGECompositionService } from './bge-composition.service';

@Component({
    selector: 'jhi-bge-composition-delete-dialog',
    templateUrl: './bge-composition-delete-dialog.component.html'
})
export class BGECompositionDeleteDialogComponent {
    bGEComposition: IBGEComposition;

    constructor(
        protected bGECompositionService: BGECompositionService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.bGECompositionService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'bGECompositionListModification',
                content: 'Deleted an bGEComposition'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-bge-composition-delete-popup',
    template: ''
})
export class BGECompositionDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ bGEComposition }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(BGECompositionDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.bGEComposition = bGEComposition;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/bge-composition', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/bge-composition', { outlets: { popup: null } }]);
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
