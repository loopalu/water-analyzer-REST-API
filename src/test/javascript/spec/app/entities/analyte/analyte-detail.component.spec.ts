/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CeTestModule } from '../../../test.module';
import { AnalyteDetailComponent } from 'app/entities/analyte/analyte-detail.component';
import { Analyte } from 'app/shared/model/analyte.model';

describe('Component Tests', () => {
    describe('Analyte Management Detail Component', () => {
        let comp: AnalyteDetailComponent;
        let fixture: ComponentFixture<AnalyteDetailComponent>;
        const route = ({ data: of({ analyte: new Analyte(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CeTestModule],
                declarations: [AnalyteDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(AnalyteDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(AnalyteDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.analyte).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
