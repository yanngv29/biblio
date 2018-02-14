/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { BiblioTestModule } from '../../../test.module';
import { RapportDetailComponent } from '../../../../../../main/webapp/app/entities/rapport/rapport-detail.component';
import { RapportService } from '../../../../../../main/webapp/app/entities/rapport/rapport.service';
import { Rapport } from '../../../../../../main/webapp/app/entities/rapport/rapport.model';

describe('Component Tests', () => {

    describe('Rapport Management Detail Component', () => {
        let comp: RapportDetailComponent;
        let fixture: ComponentFixture<RapportDetailComponent>;
        let service: RapportService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [BiblioTestModule],
                declarations: [RapportDetailComponent],
                providers: [
                    RapportService
                ]
            })
            .overrideTemplate(RapportDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(RapportDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RapportService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new Rapport(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.rapport).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
