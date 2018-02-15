/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { BiblioTestModule } from '../../../test.module';
import { ActesDetailComponent } from '../../../../../../main/webapp/app/entities/actes/actes-detail.component';
import { ActesService } from '../../../../../../main/webapp/app/entities/actes/actes.service';
import { Actes } from '../../../../../../main/webapp/app/entities/actes/actes.model';

describe('Component Tests', () => {

    describe('Actes Management Detail Component', () => {
        let comp: ActesDetailComponent;
        let fixture: ComponentFixture<ActesDetailComponent>;
        let service: ActesService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [BiblioTestModule],
                declarations: [ActesDetailComponent],
                providers: [
                    ActesService
                ]
            })
            .overrideTemplate(ActesDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ActesDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ActesService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new Actes(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.actes).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
