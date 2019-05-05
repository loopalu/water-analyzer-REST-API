/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { CeTestModule } from '../../../test.module';
import { AnalyteUpdateComponent } from 'app/entities/analyte/analyte-update.component';
import { AnalyteService } from 'app/entities/analyte/analyte.service';
import { Analyte } from 'app/shared/model/analyte.model';

describe('Component Tests', () => {
    describe('Analyte Management Update Component', () => {
        let comp: AnalyteUpdateComponent;
        let fixture: ComponentFixture<AnalyteUpdateComponent>;
        let service: AnalyteService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CeTestModule],
                declarations: [AnalyteUpdateComponent]
            })
                .overrideTemplate(AnalyteUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(AnalyteUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AnalyteService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new Analyte(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.analyte = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new Analyte();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.analyte = entity;
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
