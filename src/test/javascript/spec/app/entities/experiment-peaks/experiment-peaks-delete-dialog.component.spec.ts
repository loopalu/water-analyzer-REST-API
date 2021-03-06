/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { CeTestModule } from '../../../test.module';
import { ExperimentPeaksDeleteDialogComponent } from 'app/entities/experiment-peaks/experiment-peaks-delete-dialog.component';
import { ExperimentPeaksService } from 'app/entities/experiment-peaks/experiment-peaks.service';

describe('Component Tests', () => {
    describe('ExperimentPeaks Management Delete Component', () => {
        let comp: ExperimentPeaksDeleteDialogComponent;
        let fixture: ComponentFixture<ExperimentPeaksDeleteDialogComponent>;
        let service: ExperimentPeaksService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CeTestModule],
                declarations: [ExperimentPeaksDeleteDialogComponent]
            })
                .overrideTemplate(ExperimentPeaksDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ExperimentPeaksDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ExperimentPeaksService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete', inject(
                [],
                fakeAsync(() => {
                    // GIVEN
                    spyOn(service, 'delete').and.returnValue(of({}));

                    // WHEN
                    comp.confirmDelete(123);
                    tick();

                    // THEN
                    expect(service.delete).toHaveBeenCalledWith(123);
                    expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                })
            ));
        });
    });
});
