/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { BiblioTestModule } from '../../../test.module';
import { OuvrageComponent } from '../../../../../../main/webapp/app/entities/ouvrage/ouvrage.component';
import { OuvrageService } from '../../../../../../main/webapp/app/entities/ouvrage/ouvrage.service';
import { Ouvrage } from '../../../../../../main/webapp/app/entities/ouvrage/ouvrage.model';

describe('Component Tests', () => {

    describe('Ouvrage Management Component', () => {
        let comp: OuvrageComponent;
        let fixture: ComponentFixture<OuvrageComponent>;
        let service: OuvrageService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [BiblioTestModule],
                declarations: [OuvrageComponent],
                providers: [
                    OuvrageService
                ]
            })
            .overrideTemplate(OuvrageComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(OuvrageComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(OuvrageService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new Ouvrage(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.ouvrages[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
