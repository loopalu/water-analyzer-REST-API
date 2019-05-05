/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CeTestModule } from '../../../test.module';
import { ExperimentResultsDetailComponent } from 'app/entities/experiment-results/experiment-results-detail.component';
import { ExperimentResults } from 'app/shared/model/experiment-results.model';

describe('Component Tests', () => {
    describe('ExperimentResults Management Detail Component', () => {
        let comp: ExperimentResultsDetailComponent;
        let fixture: ComponentFixture<ExperimentResultsDetailComponent>;
        const route = ({ data: of({ experimentResults: new ExperimentResults(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CeTestModule],
                declarations: [ExperimentResultsDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(ExperimentResultsDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ExperimentResultsDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.experimentResults).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
