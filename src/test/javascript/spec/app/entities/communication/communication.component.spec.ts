/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { BiblioTestModule } from '../../../test.module';
import { CommunicationComponent } from '../../../../../../main/webapp/app/entities/communication/communication.component';
import { CommunicationService } from '../../../../../../main/webapp/app/entities/communication/communication.service';
import { Communication } from '../../../../../../main/webapp/app/entities/communication/communication.model';

describe('Component Tests', () => {

    describe('Communication Management Component', () => {
        let comp: CommunicationComponent;
        let fixture: ComponentFixture<CommunicationComponent>;
        let service: CommunicationService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [BiblioTestModule],
                declarations: [CommunicationComponent],
                providers: [
                    CommunicationService
                ]
            })
            .overrideTemplate(CommunicationComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CommunicationComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CommunicationService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new Communication(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.communications[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
