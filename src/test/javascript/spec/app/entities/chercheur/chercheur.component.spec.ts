/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { BiblioTestModule } from '../../../test.module';
import { ChercheurComponent } from '../../../../../../main/webapp/app/entities/chercheur/chercheur.component';
import { ChercheurService } from '../../../../../../main/webapp/app/entities/chercheur/chercheur.service';
import { Chercheur } from '../../../../../../main/webapp/app/entities/chercheur/chercheur.model';

describe('Component Tests', () => {

    describe('Chercheur Management Component', () => {
        let comp: ChercheurComponent;
        let fixture: ComponentFixture<ChercheurComponent>;
        let service: ChercheurService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [BiblioTestModule],
                declarations: [ChercheurComponent],
                providers: [
                    ChercheurService
                ]
            })
            .overrideTemplate(ChercheurComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ChercheurComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ChercheurService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new Chercheur(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.chercheurs[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
