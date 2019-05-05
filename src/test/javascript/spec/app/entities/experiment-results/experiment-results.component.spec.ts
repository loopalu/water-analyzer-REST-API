/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { CeTestModule } from '../../../test.module';
import { ExperimentResultsComponent } from 'app/entities/experiment-results/experiment-results.component';
import { ExperimentResultsService } from 'app/entities/experiment-results/experiment-results.service';
import { ExperimentResults } from 'app/shared/model/experiment-results.model';

describe('Component Tests', () => {
    describe('ExperimentResults Management Component', () => {
        let comp: ExperimentResultsComponent;
        let fixture: ComponentFixture<ExperimentResultsComponent>;
        let service: ExperimentResultsService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CeTestModule],
                declarations: [ExperimentResultsComponent],
                providers: []
            })
                .overrideTemplate(ExperimentResultsComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ExperimentResultsComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ExperimentResultsService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new ExperimentResults(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.experimentResults[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
