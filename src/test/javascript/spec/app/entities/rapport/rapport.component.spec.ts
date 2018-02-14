/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { BiblioTestModule } from '../../../test.module';
import { RapportComponent } from '../../../../../../main/webapp/app/entities/rapport/rapport.component';
import { RapportService } from '../../../../../../main/webapp/app/entities/rapport/rapport.service';
import { Rapport } from '../../../../../../main/webapp/app/entities/rapport/rapport.model';

describe('Component Tests', () => {

    describe('Rapport Management Component', () => {
        let comp: RapportComponent;
        let fixture: ComponentFixture<RapportComponent>;
        let service: RapportService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [BiblioTestModule],
                declarations: [RapportComponent],
                providers: [
                    RapportService
                ]
            })
            .overrideTemplate(RapportComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(RapportComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RapportService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new Rapport(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.rapports[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
