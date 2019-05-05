/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { CeTestModule } from '../../../test.module';
import { ExperimentAnalysisDeleteDialogComponent } from 'app/entities/experiment-analysis/experiment-analysis-delete-dialog.component';
import { ExperimentAnalysisService } from 'app/entities/experiment-analysis/experiment-analysis.service';

describe('Component Tests', () => {
    describe('ExperimentAnalysis Management Delete Component', () => {
        let comp: ExperimentAnalysisDeleteDialogComponent;
        let fixture: ComponentFixture<ExperimentAnalysisDeleteDialogComponent>;
        let service: ExperimentAnalysisService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CeTestModule],
                declarations: [ExperimentAnalysisDeleteDialogComponent]
            })
                .overrideTemplate(ExperimentAnalysisDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ExperimentAnalysisDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ExperimentAnalysisService);
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
