/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { BiblioTestModule } from '../../../test.module';
import { PublicationGouvernementaleComponent } from '../../../../../../main/webapp/app/entities/publication-gouvernementale/publication-gouvernementale.component';
import { PublicationGouvernementaleService } from '../../../../../../main/webapp/app/entities/publication-gouvernementale/publication-gouvernementale.service';
import { PublicationGouvernementale } from '../../../../../../main/webapp/app/entities/publication-gouvernementale/publication-gouvernementale.model';

describe('Component Tests', () => {

    describe('PublicationGouvernementale Management Component', () => {
        let comp: PublicationGouvernementaleComponent;
        let fixture: ComponentFixture<PublicationGouvernementaleComponent>;
        let service: PublicationGouvernementaleService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [BiblioTestModule],
                declarations: [PublicationGouvernementaleComponent],
                providers: [
                    PublicationGouvernementaleService
                ]
            })
            .overrideTemplate(PublicationGouvernementaleComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PublicationGouvernementaleComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PublicationGouvernementaleService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new PublicationGouvernementale(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.publicationGouvernementales[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
