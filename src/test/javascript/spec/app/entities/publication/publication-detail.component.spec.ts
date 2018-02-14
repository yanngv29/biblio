/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { BiblioTestModule } from '../../../test.module';
import { PublicationDetailComponent } from '../../../../../../main/webapp/app/entities/publication/publication-detail.component';
import { PublicationService } from '../../../../../../main/webapp/app/entities/publication/publication.service';
import { Publication } from '../../../../../../main/webapp/app/entities/publication/publication.model';

describe('Component Tests', () => {

    describe('Publication Management Detail Component', () => {
        let comp: PublicationDetailComponent;
        let fixture: ComponentFixture<PublicationDetailComponent>;
        let service: PublicationService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [BiblioTestModule],
                declarations: [PublicationDetailComponent],
                providers: [
                    PublicationService
                ]
            })
            .overrideTemplate(PublicationDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PublicationDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PublicationService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new Publication(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.publication).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
