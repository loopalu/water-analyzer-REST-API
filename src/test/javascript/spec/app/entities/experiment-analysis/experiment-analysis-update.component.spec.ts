/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { CeTestModule } from '../../../test.module';
import { ExperimentAnalysisUpdateComponent } from 'app/entities/experiment-analysis/experiment-analysis-update.component';
import { ExperimentAnalysisService } from 'app/entities/experiment-analysis/experiment-analysis.service';
import { ExperimentAnalysis } from 'app/shared/model/experiment-analysis.model';

describe('Component Tests', () => {
    describe('ExperimentAnalysis Management Update Component', () => {
        let comp: ExperimentAnalysisUpdateComponent;
        let fixture: ComponentFixture<ExperimentAnalysisUpdateComponent>;
        let service: ExperimentAnalysisService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CeTestModule],
                declarations: [ExperimentAnalysisUpdateComponent]
            })
                .overrideTemplate(ExperimentAnalysisUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ExperimentAnalysisUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ExperimentAnalysisService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new ExperimentAnalysis(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.experimentAnalysis = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new ExperimentAnalysis();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.experimentAnalysis = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.create).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));
        });
    });
});
