/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { BiblioTestModule } from '../../../test.module';
import { ContributionDialogComponent } from '../../../../../../main/webapp/app/entities/contribution/contribution-dialog.component';
import { ContributionService } from '../../../../../../main/webapp/app/entities/contribution/contribution.service';
import { Contribution } from '../../../../../../main/webapp/app/entities/contribution/contribution.model';
import { PublicationService } from '../../../../../../main/webapp/app/entities/publication';
import { ActeService } from '../../../../../../main/webapp/app/entities/acte';
import { OuvrageService } from '../../../../../../main/webapp/app/entities/ouvrage';
import { RevueService } from '../../../../../../main/webapp/app/entities/revue';
import { MemoireService } from '../../../../../../main/webapp/app/entities/memoire';

describe('Component Tests', () => {

    describe('Contribution Management Dialog Component', () => {
        let comp: ContributionDialogComponent;
        let fixture: ComponentFixture<ContributionDialogComponent>;
        let service: ContributionService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [BiblioTestModule],
                declarations: [ContributionDialogComponent],
                providers: [
                    PublicationService,
                    ActeService,
                    OuvrageService,
                    RevueService,
                    MemoireService,
                    ContributionService
                ]
            })
            .overrideTemplate(ContributionDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ContributionDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ContributionService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new Contribution(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.contribution = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'contributionListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new Contribution();
                        spyOn(service, 'create').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.contribution = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'contributionListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
