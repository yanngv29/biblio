/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { BiblioTestModule } from '../../../test.module';
import { ChercheurDetailComponent } from '../../../../../../main/webapp/app/entities/chercheur/chercheur-detail.component';
import { ChercheurService } from '../../../../../../main/webapp/app/entities/chercheur/chercheur.service';
import { Chercheur } from '../../../../../../main/webapp/app/entities/chercheur/chercheur.model';

describe('Component Tests', () => {

    describe('Chercheur Management Detail Component', () => {
        let comp: ChercheurDetailComponent;
        let fixture: ComponentFixture<ChercheurDetailComponent>;
        let service: ChercheurService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [BiblioTestModule],
                declarations: [ChercheurDetailComponent],
                providers: [
                    ChercheurService
                ]
            })
            .overrideTemplate(ChercheurDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ChercheurDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ChercheurService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new Chercheur(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.chercheur).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
