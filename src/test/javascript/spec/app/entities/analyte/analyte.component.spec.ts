/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { CeTestModule } from '../../../test.module';
import { AnalyteComponent } from 'app/entities/analyte/analyte.component';
import { AnalyteService } from 'app/entities/analyte/analyte.service';
import { Analyte } from 'app/shared/model/analyte.model';

describe('Component Tests', () => {
    describe('Analyte Management Component', () => {
        let comp: AnalyteComponent;
        let fixture: ComponentFixture<AnalyteComponent>;
        let service: AnalyteService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CeTestModule],
                declarations: [AnalyteComponent],
                providers: []
            })
                .overrideTemplate(AnalyteComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(AnalyteComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AnalyteService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Analyte(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.analytes[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
