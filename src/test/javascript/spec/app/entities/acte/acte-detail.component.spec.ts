/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { BiblioTestModule } from '../../../test.module';
import { ActeDetailComponent } from '../../../../../../main/webapp/app/entities/acte/acte-detail.component';
import { ActeService } from '../../../../../../main/webapp/app/entities/acte/acte.service';
import { Acte } from '../../../../../../main/webapp/app/entities/acte/acte.model';

describe('Component Tests', () => {

    describe('Acte Management Detail Component', () => {
        let comp: ActeDetailComponent;
        let fixture: ComponentFixture<ActeDetailComponent>;
        let service: ActeService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [BiblioTestModule],
                declarations: [ActeDetailComponent],
                providers: [
                    ActeService
                ]
            })
            .overrideTemplate(ActeDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ActeDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ActeService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new Acte(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.acte).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
