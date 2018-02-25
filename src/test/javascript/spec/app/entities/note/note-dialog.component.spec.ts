/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { BiblioTestModule } from '../../../test.module';
import { NoteDialogComponent } from '../../../../../../main/webapp/app/entities/note/note-dialog.component';
import { NoteService } from '../../../../../../main/webapp/app/entities/note/note.service';
import { Note } from '../../../../../../main/webapp/app/entities/note/note.model';
import { ConferenceService } from '../../../../../../main/webapp/app/entities/conference';
import { MemoireService } from '../../../../../../main/webapp/app/entities/memoire';
import { OuvrageService } from '../../../../../../main/webapp/app/entities/ouvrage';
import { RapportService } from '../../../../../../main/webapp/app/entities/rapport';
import { RevueService } from '../../../../../../main/webapp/app/entities/revue';

describe('Component Tests', () => {

    describe('Note Management Dialog Component', () => {
        let comp: NoteDialogComponent;
        let fixture: ComponentFixture<NoteDialogComponent>;
        let service: NoteService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [BiblioTestModule],
                declarations: [NoteDialogComponent],
                providers: [
                    ConferenceService,
                    MemoireService,
                    OuvrageService,
                    RapportService,
                    RevueService,
                    NoteService
                ]
            })
            .overrideTemplate(NoteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(NoteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(NoteService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new Note(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.note = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'noteListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new Note();
                        spyOn(service, 'create').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.note = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'noteListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
