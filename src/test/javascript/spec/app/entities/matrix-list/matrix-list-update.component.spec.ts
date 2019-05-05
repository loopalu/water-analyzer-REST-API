/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { CeTestModule } from '../../../test.module';
import { MatrixListUpdateComponent } from 'app/entities/matrix-list/matrix-list-update.component';
import { MatrixListService } from 'app/entities/matrix-list/matrix-list.service';
import { MatrixList } from 'app/shared/model/matrix-list.model';

describe('Component Tests', () => {
    describe('MatrixList Management Update Component', () => {
        let comp: MatrixListUpdateComponent;
        let fixture: ComponentFixture<MatrixListUpdateComponent>;
        let service: MatrixListService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CeTestModule],
                declarations: [MatrixListUpdateComponent]
            })
                .overrideTemplate(MatrixListUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(MatrixListUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MatrixListService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new MatrixList(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.matrixList = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new MatrixList();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.matrixList = entity;
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
