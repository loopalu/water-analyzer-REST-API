/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CeTestModule } from '../../../test.module';
import { BGECompositionDetailComponent } from 'app/entities/bge-composition/bge-composition-detail.component';
import { BGEComposition } from 'app/shared/model/bge-composition.model';

describe('Component Tests', () => {
    describe('BGEComposition Management Detail Component', () => {
        let comp: BGECompositionDetailComponent;
        let fixture: ComponentFixture<BGECompositionDetailComponent>;
        const route = ({ data: of({ bGEComposition: new BGEComposition(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CeTestModule],
                declarations: [BGECompositionDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(BGECompositionDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(BGECompositionDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.bGEComposition).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
