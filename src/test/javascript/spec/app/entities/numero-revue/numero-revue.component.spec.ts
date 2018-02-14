/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { BiblioTestModule } from '../../../test.module';
import { NumeroRevueComponent } from '../../../../../../main/webapp/app/entities/numero-revue/numero-revue.component';
import { NumeroRevueService } from '../../../../../../main/webapp/app/entities/numero-revue/numero-revue.service';
import { NumeroRevue } from '../../../../../../main/webapp/app/entities/numero-revue/numero-revue.model';

describe('Component Tests', () => {

    describe('NumeroRevue Management Component', () => {
        let comp: NumeroRevueComponent;
        let fixture: ComponentFixture<NumeroRevueComponent>;
        let service: NumeroRevueService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [BiblioTestModule],
                declarations: [NumeroRevueComponent],
                providers: [
                    NumeroRevueService
                ]
            })
            .overrideTemplate(NumeroRevueComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(NumeroRevueComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(NumeroRevueService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new NumeroRevue(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.numeroRevues[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
