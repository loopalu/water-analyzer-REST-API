/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CeTestModule } from '../../../test.module';
import { ExperimentDetailComponent } from 'app/entities/experiment/experiment-detail.component';
import { Experiment } from 'app/shared/model/experiment.model';

describe('Component Tests', () => {
    describe('Experiment Management Detail Component', () => {
        let comp: ExperimentDetailComponent;
        let fixture: ComponentFixture<ExperimentDetailComponent>;
        const route = ({ data: of({ experiment: new Experiment(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CeTestModule],
                declarations: [ExperimentDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(ExperimentDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ExperimentDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.experiment).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
