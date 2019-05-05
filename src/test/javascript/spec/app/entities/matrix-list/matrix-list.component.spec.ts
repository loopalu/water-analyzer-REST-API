/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { CeTestModule } from '../../../test.module';
import { MatrixListComponent } from 'app/entities/matrix-list/matrix-list.component';
import { MatrixListService } from 'app/entities/matrix-list/matrix-list.service';
import { MatrixList } from 'app/shared/model/matrix-list.model';

describe('Component Tests', () => {
    describe('MatrixList Management Component', () => {
        let comp: MatrixListComponent;
        let fixture: ComponentFixture<MatrixListComponent>;
        let service: MatrixListService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CeTestModule],
                declarations: [MatrixListComponent],
                providers: []
            })
                .overrideTemplate(MatrixListComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(MatrixListComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MatrixListService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new MatrixList(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.matrixLists[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
