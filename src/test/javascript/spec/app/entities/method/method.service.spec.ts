/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import { MethodService } from 'app/entities/method/method.service';
import { IMethod, Method } from 'app/shared/model/method.model';

describe('Service Tests', () => {
    describe('Method Service', () => {
        let injector: TestBed;
        let service: MethodService;
        let httpMock: HttpTestingController;
        let elemDefault: IMethod;
        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HttpClientTestingModule]
            });
            injector = getTestBed();
            service = injector.get(MethodService);
            httpMock = injector.get(HttpTestingController);

            elemDefault = new Method(0, 'AAAAAAA', 0, 'AAAAAAA', 0, 0, 'AAAAAAA', 0, 0);
        });

        describe('Service methods', async () => {
            it('should find an element', async () => {
                const returnedFromService = Object.assign({}, elemDefault);
                service
                    .find(123)
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: elemDefault }));

                const req = httpMock.expectOne({ method: 'GET' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should create a Method', async () => {
                const returnedFromService = Object.assign(
                    {
                        id: 0
                    },
                    elemDefault
                );
                const expected = Object.assign({}, returnedFromService);
                service
                    .create(new Method(null))
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'POST' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should update a Method', async () => {
                const returnedFromService = Object.assign(
                    {
                        methodName: 'BBBBBB',
                        frequency: 1,
                        injectionType: 'BBBBBB',
                        injectionTime: 1,
                        measureValue: 1,
                        unitOfMeasure: 'BBBBBB',
                        experimentTime: 1,
                        current: 1
                    },
                    elemDefault
                );

                const expected = Object.assign({}, returnedFromService);
                service
                    .update(expected)
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'PUT' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should return a list of Method', async () => {
                const returnedFromService = Object.assign(
                    {
                        methodName: 'BBBBBB',
                        frequency: 1,
                        injectionType: 'BBBBBB',
                        injectionTime: 1,
                        measureValue: 1,
                        unitOfMeasure: 'BBBBBB',
                        experimentTime: 1,
                        current: 1
                    },
                    elemDefault
                );
                const expected = Object.assign({}, returnedFromService);
                service
                    .query(expected)
                    .pipe(
                        take(1),
                        map(resp => resp.body)
                    )
                    .subscribe(body => expect(body).toContainEqual(expected));
                const req = httpMock.expectOne({ method: 'GET' });
                req.flush(JSON.stringify([returnedFromService]));
                httpMock.verify();
            });

            it('should delete a Method', async () => {
                const rxPromise = service.delete(123).subscribe(resp => expect(resp.ok));

                const req = httpMock.expectOne({ method: 'DELETE' });
                req.flush({ status: 200 });
            });
        });

        afterEach(() => {
            httpMock.verify();
        });
    });
});
