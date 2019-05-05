/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CeTestModule } from '../../../test.module';
import { ExperimentPeaksDetailComponent } from 'app/entities/experiment-peaks/experiment-peaks-detail.component';
import { ExperimentPeaks } from 'app/shared/model/experiment-peaks.model';

describe('Component Tests', () => {
    describe('ExperimentPeaks Management Detail Component', () => {
        let comp: ExperimentPeaksDetailComponent;
        let fixture: ComponentFixture<ExperimentPeaksDetailComponent>;
        const route = ({ data: of({ experimentPeaks: new ExperimentPeaks(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CeTestModule],
                declarations: [ExperimentPeaksDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(ExperimentPeaksDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ExperimentPeaksDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.experimentPeaks).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
