/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { BiblioTestModule } from '../../../test.module';
import { RevueComponent } from '../../../../../../main/webapp/app/entities/revue/revue.component';
import { RevueService } from '../../../../../../main/webapp/app/entities/revue/revue.service';
import { Revue } from '../../../../../../main/webapp/app/entities/revue/revue.model';

describe('Component Tests', () => {

    describe('Revue Management Component', () => {
        let comp: RevueComponent;
        let fixture: ComponentFixture<RevueComponent>;
        let service: RevueService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [BiblioTestModule],
                declarations: [RevueComponent],
                providers: [
                    RevueService
                ]
            })
            .overrideTemplate(RevueComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(RevueComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RevueService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new Revue(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.revues[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
