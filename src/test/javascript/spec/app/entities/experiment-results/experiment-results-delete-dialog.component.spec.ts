/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { CeTestModule } from '../../../test.module';
import { ExperimentResultsDeleteDialogComponent } from 'app/entities/experiment-results/experiment-results-delete-dialog.component';
import { ExperimentResultsService } from 'app/entities/experiment-results/experiment-results.service';

describe('Component Tests', () => {
    describe('ExperimentResults Management Delete Component', () => {
        let comp: ExperimentResultsDeleteDialogComponent;
        let fixture: ComponentFixture<ExperimentResultsDeleteDialogComponent>;
        let service: ExperimentResultsService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CeTestModule],
                declarations: [ExperimentResultsDeleteDialogComponent]
            })
                .overrideTemplate(ExperimentResultsDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ExperimentResultsDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ExperimentResultsService);
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
