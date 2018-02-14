/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { BiblioTestModule } from '../../../test.module';
import { OuvrageDetailComponent } from '../../../../../../main/webapp/app/entities/ouvrage/ouvrage-detail.component';
import { OuvrageService } from '../../../../../../main/webapp/app/entities/ouvrage/ouvrage.service';
import { Ouvrage } from '../../../../../../main/webapp/app/entities/ouvrage/ouvrage.model';

describe('Component Tests', () => {

    describe('Ouvrage Management Detail Component', () => {
        let comp: OuvrageDetailComponent;
        let fixture: ComponentFixture<OuvrageDetailComponent>;
        let service: OuvrageService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [BiblioTestModule],
                declarations: [OuvrageDetailComponent],
                providers: [
                    OuvrageService
                ]
            })
            .overrideTemplate(OuvrageDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(OuvrageDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(OuvrageService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new Ouvrage(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.ouvrage).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
