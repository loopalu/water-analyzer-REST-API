/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CeTestModule } from '../../../test.module';
import { ExperimentAnalysisDetailComponent } from 'app/entities/experiment-analysis/experiment-analysis-detail.component';
import { ExperimentAnalysis } from 'app/shared/model/experiment-analysis.model';

describe('Component Tests', () => {
    describe('ExperimentAnalysis Management Detail Component', () => {
        let comp: ExperimentAnalysisDetailComponent;
        let fixture: ComponentFixture<ExperimentAnalysisDetailComponent>;
        const route = ({ data: of({ experimentAnalysis: new ExperimentAnalysis(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CeTestModule],
                declarations: [ExperimentAnalysisDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(ExperimentAnalysisDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ExperimentAnalysisDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.experimentAnalysis).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
