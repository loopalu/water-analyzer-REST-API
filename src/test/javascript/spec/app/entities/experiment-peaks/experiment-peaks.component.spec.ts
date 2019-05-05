/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { CeTestModule } from '../../../test.module';
import { ExperimentPeaksComponent } from 'app/entities/experiment-peaks/experiment-peaks.component';
import { ExperimentPeaksService } from 'app/entities/experiment-peaks/experiment-peaks.service';
import { ExperimentPeaks } from 'app/shared/model/experiment-peaks.model';

describe('Component Tests', () => {
    describe('ExperimentPeaks Management Component', () => {
        let comp: ExperimentPeaksComponent;
        let fixture: ComponentFixture<ExperimentPeaksComponent>;
        let service: ExperimentPeaksService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CeTestModule],
                declarations: [ExperimentPeaksComponent],
                providers: []
            })
                .overrideTemplate(ExperimentPeaksComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ExperimentPeaksComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ExperimentPeaksService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new ExperimentPeaks(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.experimentPeaks[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
