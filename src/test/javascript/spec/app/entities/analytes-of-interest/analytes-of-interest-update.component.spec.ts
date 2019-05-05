/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { CeTestModule } from '../../../test.module';
import { AnalytesOfInterestUpdateComponent } from 'app/entities/analytes-of-interest/analytes-of-interest-update.component';
import { AnalytesOfInterestService } from 'app/entities/analytes-of-interest/analytes-of-interest.service';
import { AnalytesOfInterest } from 'app/shared/model/analytes-of-interest.model';

describe('Component Tests', () => {
    describe('AnalytesOfInterest Management Update Component', () => {
        let comp: AnalytesOfInterestUpdateComponent;
        let fixture: ComponentFixture<AnalytesOfInterestUpdateComponent>;
        let service: AnalytesOfInterestService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CeTestModule],
                declarations: [AnalytesOfInterestUpdateComponent]
            })
                .overrideTemplate(AnalytesOfInterestUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(AnalytesOfInterestUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AnalytesOfInterestService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new AnalytesOfInterest(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.analytesOfInterest = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new AnalytesOfInterest();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.analytesOfInterest = entity;
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
