/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { CeTestModule } from '../../../test.module';
import { ExperimentComponent } from 'app/entities/experiment/experiment.component';
import { ExperimentService } from 'app/entities/experiment/experiment.service';
import { Experiment } from 'app/shared/model/experiment.model';

describe('Component Tests', () => {
    describe('Experiment Management Component', () => {
        let comp: ExperimentComponent;
        let fixture: ComponentFixture<ExperimentComponent>;
        let service: ExperimentService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CeTestModule],
                declarations: [ExperimentComponent],
                providers: []
            })
                .overrideTemplate(ExperimentComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ExperimentComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ExperimentService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Experiment(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.experiments[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
