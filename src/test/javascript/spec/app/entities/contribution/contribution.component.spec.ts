/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { BiblioTestModule } from '../../../test.module';
import { ContributionComponent } from '../../../../../../main/webapp/app/entities/contribution/contribution.component';
import { ContributionService } from '../../../../../../main/webapp/app/entities/contribution/contribution.service';
import { Contribution } from '../../../../../../main/webapp/app/entities/contribution/contribution.model';

describe('Component Tests', () => {

    describe('Contribution Management Component', () => {
        let comp: ContributionComponent;
        let fixture: ComponentFixture<ContributionComponent>;
        let service: ContributionService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [BiblioTestModule],
                declarations: [ContributionComponent],
                providers: [
                    ContributionService
                ]
            })
            .overrideTemplate(ContributionComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ContributionComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ContributionService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new Contribution(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.contributions[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
