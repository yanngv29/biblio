/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { BiblioTestModule } from '../../../test.module';
import { PublicationComponent } from '../../../../../../main/webapp/app/entities/publication/publication.component';
import { PublicationService } from '../../../../../../main/webapp/app/entities/publication/publication.service';
import { Publication } from '../../../../../../main/webapp/app/entities/publication/publication.model';

describe('Component Tests', () => {

    describe('Publication Management Component', () => {
        let comp: PublicationComponent;
        let fixture: ComponentFixture<PublicationComponent>;
        let service: PublicationService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [BiblioTestModule],
                declarations: [PublicationComponent],
                providers: [
                    PublicationService
                ]
            })
            .overrideTemplate(PublicationComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PublicationComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PublicationService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new Publication(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.publications[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
