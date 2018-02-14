/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { BiblioTestModule } from '../../../test.module';
import { NumeroRevueDetailComponent } from '../../../../../../main/webapp/app/entities/numero-revue/numero-revue-detail.component';
import { NumeroRevueService } from '../../../../../../main/webapp/app/entities/numero-revue/numero-revue.service';
import { NumeroRevue } from '../../../../../../main/webapp/app/entities/numero-revue/numero-revue.model';

describe('Component Tests', () => {

    describe('NumeroRevue Management Detail Component', () => {
        let comp: NumeroRevueDetailComponent;
        let fixture: ComponentFixture<NumeroRevueDetailComponent>;
        let service: NumeroRevueService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [BiblioTestModule],
                declarations: [NumeroRevueDetailComponent],
                providers: [
                    NumeroRevueService
                ]
            })
            .overrideTemplate(NumeroRevueDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(NumeroRevueDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(NumeroRevueService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new NumeroRevue(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.numeroRevue).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
