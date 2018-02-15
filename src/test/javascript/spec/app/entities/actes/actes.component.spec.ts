/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { BiblioTestModule } from '../../../test.module';
import { ActesComponent } from '../../../../../../main/webapp/app/entities/actes/actes.component';
import { ActesService } from '../../../../../../main/webapp/app/entities/actes/actes.service';
import { Actes } from '../../../../../../main/webapp/app/entities/actes/actes.model';

describe('Component Tests', () => {

    describe('Actes Management Component', () => {
        let comp: ActesComponent;
        let fixture: ComponentFixture<ActesComponent>;
        let service: ActesService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [BiblioTestModule],
                declarations: [ActesComponent],
                providers: [
                    ActesService
                ]
            })
            .overrideTemplate(ActesComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ActesComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ActesService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new Actes(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.actes[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
