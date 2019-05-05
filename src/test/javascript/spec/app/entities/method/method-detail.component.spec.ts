/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CeTestModule } from '../../../test.module';
import { MethodDetailComponent } from 'app/entities/method/method-detail.component';
import { Method } from 'app/shared/model/method.model';

describe('Component Tests', () => {
    describe('Method Management Detail Component', () => {
        let comp: MethodDetailComponent;
        let fixture: ComponentFixture<MethodDetailComponent>;
        const route = ({ data: of({ method: new Method(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CeTestModule],
                declarations: [MethodDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(MethodDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(MethodDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.method).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
