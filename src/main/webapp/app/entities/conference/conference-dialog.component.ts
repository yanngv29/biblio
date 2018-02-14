import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Conference } from './conference.model';
import { ConferencePopupService } from './conference-popup.service';
import { ConferenceService } from './conference.service';

@Component({
    selector: 'jhi-conference-dialog',
    templateUrl: './conference-dialog.component.html'
})
export class ConferenceDialogComponent implements OnInit {

    conference: Conference;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private conferenceService: ConferenceService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.conference.id !== undefined) {
            this.subscribeToSaveResponse(
                this.conferenceService.update(this.conference));
        } else {
            this.subscribeToSaveResponse(
                this.conferenceService.create(this.conference));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Conference>>) {
        result.subscribe((res: HttpResponse<Conference>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Conference) {
        this.eventManager.broadcast({ name: 'conferenceListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-conference-popup',
    template: ''
})
export class ConferencePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private conferencePopupService: ConferencePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.conferencePopupService
                    .open(ConferenceDialogComponent as Component, params['id']);
            } else {
                this.conferencePopupService
                    .open(ConferenceDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
