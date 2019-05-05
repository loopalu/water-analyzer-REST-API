/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CeTestModule } from '../../../test.module';
import { MatrixListDetailComponent } from 'app/entities/matrix-list/matrix-list-detail.component';
import { MatrixList } from 'app/shared/model/matrix-list.model';

describe('Component Tests', () => {
    describe('MatrixList Management Detail Component', () => {
        let comp: MatrixListDetailComponent;
        let fixture: ComponentFixture<MatrixListDetailComponent>;
        const route = ({ data: of({ matrixList: new MatrixList(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CeTestModule],
                declarations: [MatrixListDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(MatrixListDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(MatrixListDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.matrixList).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
