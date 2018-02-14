/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { BiblioTestModule } from '../../../test.module';
import { NotationDialogComponent } from '../../../../../../main/webapp/app/entities/notation/notation-dialog.component';
import { NotationService } from '../../../../../../main/webapp/app/entities/notation/notation.service';
import { Notation } from '../../../../../../main/webapp/app/entities/notation/notation.model';
import { ConferenceService } from '../../../../../../main/webapp/app/entities/conference';
import { MemoireService } from '../../../../../../main/webapp/app/entities/memoire';
import { OuvrageService } from '../../../../../../main/webapp/app/entities/ouvrage';
import { PublicationGouvernementaleService } from '../../../../../../main/webapp/app/entities/publication-gouvernementale';
import { RapportService } from '../../../../../../main/webapp/app/entities/rapport';
import { RevueService } from '../../../../../../main/webapp/app/entities/revue';

describe('Component Tests', () => {

    describe('Notation Management Dialog Component', () => {
        let comp: NotationDialogComponent;
        let fixture: ComponentFixture<NotationDialogComponent>;
        let service: NotationService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [BiblioTestModule],
                declarations: [NotationDialogComponent],
                providers: [
                    ConferenceService,
                    MemoireService,
                    OuvrageService,
                    PublicationGouvernementaleService,
                    RapportService,
                    RevueService,
                    NotationService
                ]
            })
            .overrideTemplate(NotationDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(NotationDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(NotationService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new Notation(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.notation = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'notationListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new Notation();
                        spyOn(service, 'create').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.notation = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'notationListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
