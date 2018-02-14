/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { BiblioTestModule } from '../../../test.module';
import { ConferenceDetailComponent } from '../../../../../../main/webapp/app/entities/conference/conference-detail.component';
import { ConferenceService } from '../../../../../../main/webapp/app/entities/conference/conference.service';
import { Conference } from '../../../../../../main/webapp/app/entities/conference/conference.model';

describe('Component Tests', () => {

    describe('Conference Management Detail Component', () => {
        let comp: ConferenceDetailComponent;
        let fixture: ComponentFixture<ConferenceDetailComponent>;
        let service: ConferenceService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [BiblioTestModule],
                declarations: [ConferenceDetailComponent],
                providers: [
                    ConferenceService
                ]
            })
            .overrideTemplate(ConferenceDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ConferenceDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ConferenceService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new Conference(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.conference).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
