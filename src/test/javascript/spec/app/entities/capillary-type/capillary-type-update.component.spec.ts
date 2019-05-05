/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { CeTestModule } from '../../../test.module';
import { CapillaryTypeUpdateComponent } from 'app/entities/capillary-type/capillary-type-update.component';
import { CapillaryTypeService } from 'app/entities/capillary-type/capillary-type.service';
import { CapillaryType } from 'app/shared/model/capillary-type.model';

describe('Component Tests', () => {
    describe('CapillaryType Management Update Component', () => {
        let comp: CapillaryTypeUpdateComponent;
        let fixture: ComponentFixture<CapillaryTypeUpdateComponent>;
        let service: CapillaryTypeService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CeTestModule],
                declarations: [CapillaryTypeUpdateComponent]
            })
                .overrideTemplate(CapillaryTypeUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(CapillaryTypeUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CapillaryTypeService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new CapillaryType(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.capillaryType = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new CapillaryType();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.capillaryType = entity;
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
