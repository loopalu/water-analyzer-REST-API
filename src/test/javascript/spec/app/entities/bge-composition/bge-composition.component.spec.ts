/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { CeTestModule } from '../../../test.module';
import { BGECompositionComponent } from 'app/entities/bge-composition/bge-composition.component';
import { BGECompositionService } from 'app/entities/bge-composition/bge-composition.service';
import { BGEComposition } from 'app/shared/model/bge-composition.model';

describe('Component Tests', () => {
    describe('BGEComposition Management Component', () => {
        let comp: BGECompositionComponent;
        let fixture: ComponentFixture<BGECompositionComponent>;
        let service: BGECompositionService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CeTestModule],
                declarations: [BGECompositionComponent],
                providers: []
            })
                .overrideTemplate(BGECompositionComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(BGECompositionComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BGECompositionService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new BGEComposition(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.bGECompositions[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
