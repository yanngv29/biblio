import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Note } from './note.model';
import { NotePopupService } from './note-popup.service';
import { NoteService } from './note.service';
import { Conference, ConferenceService } from '../conference';
import { Memoire, MemoireService } from '../memoire';
import { Ouvrage, OuvrageService } from '../ouvrage';
import { Rapport, RapportService } from '../rapport';
import { Revue, RevueService } from '../revue';

@Component({
    selector: 'jhi-note-dialog',
    templateUrl: './note-dialog.component.html'
})
export class NoteDialogComponent implements OnInit {

    note: Note;
    isSaving: boolean;

    conferences: Conference[];

    memoires: Memoire[];

    ouvrages: Ouvrage[];

    rapports: Rapport[];

    revues: Revue[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private noteService: NoteService,
        private conferenceService: ConferenceService,
        private memoireService: MemoireService,
        private ouvrageService: OuvrageService,
        private rapportService: RapportService,
        private revueService: RevueService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.conferenceService.query()
            .subscribe((res: HttpResponse<Conference[]>) => { this.conferences = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
        this.memoireService.query()
            .subscribe((res: HttpResponse<Memoire[]>) => { this.memoires = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
        this.ouvrageService.query()
            .subscribe((res: HttpResponse<Ouvrage[]>) => { this.ouvrages = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
        this.rapportService.query()
            .subscribe((res: HttpResponse<Rapport[]>) => { this.rapports = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
        this.revueService.query()
            .subscribe((res: HttpResponse<Revue[]>) => { this.revues = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.note.id !== undefined) {
            this.subscribeToSaveResponse(
                this.noteService.update(this.note));
        } else {
            this.subscribeToSaveResponse(
                this.noteService.create(this.note));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Note>>) {
        result.subscribe((res: HttpResponse<Note>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Note) {
        this.eventManager.broadcast({ name: 'noteListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackConferenceById(index: number, item: Conference) {
        return item.id;
    }

    trackMemoireById(index: number, item: Memoire) {
        return item.id;
    }

    trackOuvrageById(index: number, item: Ouvrage) {
        return item.id;
    }

    trackRapportById(index: number, item: Rapport) {
        return item.id;
    }

    trackRevueById(index: number, item: Revue) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-note-popup',
    template: ''
})
export class NotePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private notePopupService: NotePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.notePopupService
                    .open(NoteDialogComponent as Component, params['id']);
            } else {
                this.notePopupService
                    .open(NoteDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
