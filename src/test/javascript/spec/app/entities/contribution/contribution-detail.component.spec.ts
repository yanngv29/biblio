/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { BiblioTestModule } from '../../../test.module';
import { ContributionDetailComponent } from '../../../../../../main/webapp/app/entities/contribution/contribution-detail.component';
import { ContributionService } from '../../../../../../main/webapp/app/entities/contribution/contribution.service';
import { Contribution } from '../../../../../../main/webapp/app/entities/contribution/contribution.model';

describe('Component Tests', () => {

    describe('Contribution Management Detail Component', () => {
        let comp: ContributionDetailComponent;
        let fixture: ComponentFixture<ContributionDetailComponent>;
        let service: ContributionService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [BiblioTestModule],
                declarations: [ContributionDetailComponent],
                providers: [
                    ContributionService
                ]
            })
            .overrideTemplate(ContributionDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ContributionDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ContributionService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new Contribution(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.contribution).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
