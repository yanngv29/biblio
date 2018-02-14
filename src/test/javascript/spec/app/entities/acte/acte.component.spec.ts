/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { BiblioTestModule } from '../../../test.module';
import { ActeComponent } from '../../../../../../main/webapp/app/entities/acte/acte.component';
import { ActeService } from '../../../../../../main/webapp/app/entities/acte/acte.service';
import { Acte } from '../../../../../../main/webapp/app/entities/acte/acte.model';

describe('Component Tests', () => {

    describe('Acte Management Component', () => {
        let comp: ActeComponent;
        let fixture: ComponentFixture<ActeComponent>;
        let service: ActeService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [BiblioTestModule],
                declarations: [ActeComponent],
                providers: [
                    ActeService
                ]
            })
            .overrideTemplate(ActeComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ActeComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ActeService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new Acte(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.actes[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
