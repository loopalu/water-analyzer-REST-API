/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { CeTestModule } from '../../../test.module';
import { ExperimentResultsUpdateComponent } from 'app/entities/experiment-results/experiment-results-update.component';
import { ExperimentResultsService } from 'app/entities/experiment-results/experiment-results.service';
import { ExperimentResults } from 'app/shared/model/experiment-results.model';

describe('Component Tests', () => {
    describe('ExperimentResults Management Update Component', () => {
        let comp: ExperimentResultsUpdateComponent;
        let fixture: ComponentFixture<ExperimentResultsUpdateComponent>;
        let service: ExperimentResultsService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CeTestModule],
                declarations: [ExperimentResultsUpdateComponent]
            })
                .overrideTemplate(ExperimentResultsUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ExperimentResultsUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ExperimentResultsService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new ExperimentResults(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.experimentResults = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new ExperimentResults();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.experimentResults = entity;
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
