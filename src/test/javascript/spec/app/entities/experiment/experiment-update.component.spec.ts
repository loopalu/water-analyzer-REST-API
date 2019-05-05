/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { CeTestModule } from '../../../test.module';
import { ExperimentUpdateComponent } from 'app/entities/experiment/experiment-update.component';
import { ExperimentService } from 'app/entities/experiment/experiment.service';
import { Experiment } from 'app/shared/model/experiment.model';

describe('Component Tests', () => {
    describe('Experiment Management Update Component', () => {
        let comp: ExperimentUpdateComponent;
        let fixture: ComponentFixture<ExperimentUpdateComponent>;
        let service: ExperimentService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CeTestModule],
                declarations: [ExperimentUpdateComponent]
            })
                .overrideTemplate(ExperimentUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ExperimentUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ExperimentService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new Experiment(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.experiment = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new Experiment();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.experiment = entity;
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
