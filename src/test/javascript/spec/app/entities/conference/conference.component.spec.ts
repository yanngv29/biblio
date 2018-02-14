/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { BiblioTestModule } from '../../../test.module';
import { ConferenceComponent } from '../../../../../../main/webapp/app/entities/conference/conference.component';
import { ConferenceService } from '../../../../../../main/webapp/app/entities/conference/conference.service';
import { Conference } from '../../../../../../main/webapp/app/entities/conference/conference.model';

describe('Component Tests', () => {

    describe('Conference Management Component', () => {
        let comp: ConferenceComponent;
        let fixture: ComponentFixture<ConferenceComponent>;
        let service: ConferenceService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [BiblioTestModule],
                declarations: [ConferenceComponent],
                providers: [
                    ConferenceService
                ]
            })
            .overrideTemplate(ConferenceComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ConferenceComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ConferenceService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new Conference(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.conferences[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
