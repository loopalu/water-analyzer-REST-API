/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { CeTestModule } from '../../../test.module';
import { ExperimentAnalysisComponent } from 'app/entities/experiment-analysis/experiment-analysis.component';
import { ExperimentAnalysisService } from 'app/entities/experiment-analysis/experiment-analysis.service';
import { ExperimentAnalysis } from 'app/shared/model/experiment-analysis.model';

describe('Component Tests', () => {
    describe('ExperimentAnalysis Management Component', () => {
        let comp: ExperimentAnalysisComponent;
        let fixture: ComponentFixture<ExperimentAnalysisComponent>;
        let service: ExperimentAnalysisService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CeTestModule],
                declarations: [ExperimentAnalysisComponent],
                providers: []
            })
                .overrideTemplate(ExperimentAnalysisComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ExperimentAnalysisComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ExperimentAnalysisService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new ExperimentAnalysis(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.experimentAnalyses[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
