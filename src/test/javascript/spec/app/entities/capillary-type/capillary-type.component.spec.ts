/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { CeTestModule } from '../../../test.module';
import { CapillaryTypeComponent } from 'app/entities/capillary-type/capillary-type.component';
import { CapillaryTypeService } from 'app/entities/capillary-type/capillary-type.service';
import { CapillaryType } from 'app/shared/model/capillary-type.model';

describe('Component Tests', () => {
    describe('CapillaryType Management Component', () => {
        let comp: CapillaryTypeComponent;
        let fixture: ComponentFixture<CapillaryTypeComponent>;
        let service: CapillaryTypeService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CeTestModule],
                declarations: [CapillaryTypeComponent],
                providers: []
            })
                .overrideTemplate(CapillaryTypeComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(CapillaryTypeComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CapillaryTypeService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new CapillaryType(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.capillaryTypes[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
