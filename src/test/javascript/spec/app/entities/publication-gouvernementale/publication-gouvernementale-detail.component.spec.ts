/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { BiblioTestModule } from '../../../test.module';
import { PublicationGouvernementaleDetailComponent } from '../../../../../../main/webapp/app/entities/publication-gouvernementale/publication-gouvernementale-detail.component';
import { PublicationGouvernementaleService } from '../../../../../../main/webapp/app/entities/publication-gouvernementale/publication-gouvernementale.service';
import { PublicationGouvernementale } from '../../../../../../main/webapp/app/entities/publication-gouvernementale/publication-gouvernementale.model';

describe('Component Tests', () => {

    describe('PublicationGouvernementale Management Detail Component', () => {
        let comp: PublicationGouvernementaleDetailComponent;
        let fixture: ComponentFixture<PublicationGouvernementaleDetailComponent>;
        let service: PublicationGouvernementaleService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [BiblioTestModule],
                declarations: [PublicationGouvernementaleDetailComponent],
                providers: [
                    PublicationGouvernementaleService
                ]
            })
            .overrideTemplate(PublicationGouvernementaleDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PublicationGouvernementaleDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PublicationGouvernementaleService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new PublicationGouvernementale(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.publicationGouvernementale).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
