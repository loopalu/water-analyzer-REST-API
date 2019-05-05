/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { CeTestModule } from '../../../test.module';
import { MethodUpdateComponent } from 'app/entities/method/method-update.component';
import { MethodService } from 'app/entities/method/method.service';
import { Method } from 'app/shared/model/method.model';

describe('Component Tests', () => {
    describe('Method Management Update Component', () => {
        let comp: MethodUpdateComponent;
        let fixture: ComponentFixture<MethodUpdateComponent>;
        let service: MethodService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CeTestModule],
                declarations: [MethodUpdateComponent]
            })
                .overrideTemplate(MethodUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(MethodUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MethodService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new Method(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.method = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new Method();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.method = entity;
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
