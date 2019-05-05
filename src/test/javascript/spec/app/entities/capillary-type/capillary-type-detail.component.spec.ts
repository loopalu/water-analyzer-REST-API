/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CeTestModule } from '../../../test.module';
import { CapillaryTypeDetailComponent } from 'app/entities/capillary-type/capillary-type-detail.component';
import { CapillaryType } from 'app/shared/model/capillary-type.model';

describe('Component Tests', () => {
    describe('CapillaryType Management Detail Component', () => {
        let comp: CapillaryTypeDetailComponent;
        let fixture: ComponentFixture<CapillaryTypeDetailComponent>;
        const route = ({ data: of({ capillaryType: new CapillaryType(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CeTestModule],
                declarations: [CapillaryTypeDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(CapillaryTypeDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(CapillaryTypeDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.capillaryType).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
