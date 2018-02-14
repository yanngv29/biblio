/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { BiblioTestModule } from '../../../test.module';
import { RevueDetailComponent } from '../../../../../../main/webapp/app/entities/revue/revue-detail.component';
import { RevueService } from '../../../../../../main/webapp/app/entities/revue/revue.service';
import { Revue } from '../../../../../../main/webapp/app/entities/revue/revue.model';

describe('Component Tests', () => {

    describe('Revue Management Detail Component', () => {
        let comp: RevueDetailComponent;
        let fixture: ComponentFixture<RevueDetailComponent>;
        let service: RevueService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [BiblioTestModule],
                declarations: [RevueDetailComponent],
                providers: [
                    RevueService
                ]
            })
            .overrideTemplate(RevueDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(RevueDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RevueService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new Revue(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.revue).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
