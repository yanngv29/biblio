/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { BiblioTestModule } from '../../../test.module';
import { NotationComponent } from '../../../../../../main/webapp/app/entities/notation/notation.component';
import { NotationService } from '../../../../../../main/webapp/app/entities/notation/notation.service';
import { Notation } from '../../../../../../main/webapp/app/entities/notation/notation.model';

describe('Component Tests', () => {

    describe('Notation Management Component', () => {
        let comp: NotationComponent;
        let fixture: ComponentFixture<NotationComponent>;
        let service: NotationService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [BiblioTestModule],
                declarations: [NotationComponent],
                providers: [
                    NotationService
                ]
            })
            .overrideTemplate(NotationComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(NotationComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(NotationService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new Notation(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.notations[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
