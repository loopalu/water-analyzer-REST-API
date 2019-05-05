/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { CeTestModule } from '../../../test.module';
import { BGECompositionUpdateComponent } from 'app/entities/bge-composition/bge-composition-update.component';
import { BGECompositionService } from 'app/entities/bge-composition/bge-composition.service';
import { BGEComposition } from 'app/shared/model/bge-composition.model';

describe('Component Tests', () => {
    describe('BGEComposition Management Update Component', () => {
        let comp: BGECompositionUpdateComponent;
        let fixture: ComponentFixture<BGECompositionUpdateComponent>;
        let service: BGECompositionService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CeTestModule],
                declarations: [BGECompositionUpdateComponent]
            })
                .overrideTemplate(BGECompositionUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(BGECompositionUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BGECompositionService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new BGEComposition(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.bGEComposition = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new BGEComposition();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.bGEComposition = entity;
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
