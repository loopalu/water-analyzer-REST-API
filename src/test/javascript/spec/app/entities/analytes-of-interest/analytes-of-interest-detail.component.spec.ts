/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CeTestModule } from '../../../test.module';
import { AnalytesOfInterestDetailComponent } from 'app/entities/analytes-of-interest/analytes-of-interest-detail.component';
import { AnalytesOfInterest } from 'app/shared/model/analytes-of-interest.model';

describe('Component Tests', () => {
    describe('AnalytesOfInterest Management Detail Component', () => {
        let comp: AnalytesOfInterestDetailComponent;
        let fixture: ComponentFixture<AnalytesOfInterestDetailComponent>;
        const route = ({ data: of({ analytesOfInterest: new AnalytesOfInterest(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CeTestModule],
                declarations: [AnalytesOfInterestDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(AnalytesOfInterestDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(AnalytesOfInterestDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.analytesOfInterest).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
