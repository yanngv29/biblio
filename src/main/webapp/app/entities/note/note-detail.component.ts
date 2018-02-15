import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { Note } from './note.model';
import { NoteService } from './note.service';

@Component({
    selector: 'jhi-note-detail',
    templateUrl: './note-detail.component.html'
})
export class NoteDetailComponent implements OnInit, OnDestroy {

    note: Note;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private noteService: NoteService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInNotes();
    }

    load(id) {
        this.noteService.find(id)
            .subscribe((noteResponse: HttpResponse<Note>) => {
                this.note = noteResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInNotes() {
        this.eventSubscriber = this.eventManager.subscribe(
            'noteListModification',
            (response) => this.load(this.note.id)
        );
    }
}
