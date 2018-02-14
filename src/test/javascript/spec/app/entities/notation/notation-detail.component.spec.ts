/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { BiblioTestModule } from '../../../test.module';
import { NotationDetailComponent } from '../../../../../../main/webapp/app/entities/notation/notation-detail.component';
import { NotationService } from '../../../../../../main/webapp/app/entities/notation/notation.service';
import { Notation } from '../../../../../../main/webapp/app/entities/notation/notation.model';

describe('Component Tests', () => {

    describe('Notation Management Detail Component', () => {
        let comp: NotationDetailComponent;
        let fixture: ComponentFixture<NotationDetailComponent>;
        let service: NotationService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [BiblioTestModule],
                declarations: [NotationDetailComponent],
                providers: [
                    NotationService
                ]
            })
            .overrideTemplate(NotationDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(NotationDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(NotationService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new Notation(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.notation).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
