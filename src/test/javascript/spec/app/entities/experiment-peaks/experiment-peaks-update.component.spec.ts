/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { CeTestModule } from '../../../test.module';
import { ExperimentPeaksUpdateComponent } from 'app/entities/experiment-peaks/experiment-peaks-update.component';
import { ExperimentPeaksService } from 'app/entities/experiment-peaks/experiment-peaks.service';
import { ExperimentPeaks } from 'app/shared/model/experiment-peaks.model';

describe('Component Tests', () => {
    describe('ExperimentPeaks Management Update Component', () => {
        let comp: ExperimentPeaksUpdateComponent;
        let fixture: ComponentFixture<ExperimentPeaksUpdateComponent>;
        let service: ExperimentPeaksService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CeTestModule],
                declarations: [ExperimentPeaksUpdateComponent]
            })
                .overrideTemplate(ExperimentPeaksUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ExperimentPeaksUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ExperimentPeaksService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new ExperimentPeaks(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.experimentPeaks = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new ExperimentPeaks();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.experimentPeaks = entity;
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
