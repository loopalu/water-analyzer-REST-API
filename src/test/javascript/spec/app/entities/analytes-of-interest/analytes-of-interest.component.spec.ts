/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { CeTestModule } from '../../../test.module';
import { AnalytesOfInterestComponent } from 'app/entities/analytes-of-interest/analytes-of-interest.component';
import { AnalytesOfInterestService } from 'app/entities/analytes-of-interest/analytes-of-interest.service';
import { AnalytesOfInterest } from 'app/shared/model/analytes-of-interest.model';

describe('Component Tests', () => {
    describe('AnalytesOfInterest Management Component', () => {
        let comp: AnalytesOfInterestComponent;
        let fixture: ComponentFixture<AnalytesOfInterestComponent>;
        let service: AnalytesOfInterestService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CeTestModule],
                declarations: [AnalytesOfInterestComponent],
                providers: []
            })
                .overrideTemplate(AnalytesOfInterestComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(AnalytesOfInterestComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AnalytesOfInterestService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new AnalytesOfInterest(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.analytesOfInterests[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
