/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { BiblioTestModule } from '../../../test.module';
import { CommunicationDetailComponent } from '../../../../../../main/webapp/app/entities/communication/communication-detail.component';
import { CommunicationService } from '../../../../../../main/webapp/app/entities/communication/communication.service';
import { Communication } from '../../../../../../main/webapp/app/entities/communication/communication.model';

describe('Component Tests', () => {

    describe('Communication Management Detail Component', () => {
        let comp: CommunicationDetailComponent;
        let fixture: ComponentFixture<CommunicationDetailComponent>;
        let service: CommunicationService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [BiblioTestModule],
                declarations: [CommunicationDetailComponent],
                providers: [
                    CommunicationService
                ]
            })
            .overrideTemplate(CommunicationDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CommunicationDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CommunicationService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new Communication(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.communication).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
