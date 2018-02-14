import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Communication } from './communication.model';
import { CommunicationPopupService } from './communication-popup.service';
import { CommunicationService } from './communication.service';
import { Conference, ConferenceService } from '../conference';

@Component({
    selector: 'jhi-communication-dialog',
    templateUrl: './communication-dialog.component.html'
})
export class CommunicationDialogComponent implements OnInit {

    communication: Communication;
    isSaving: boolean;

    conferences: Conference[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private communicationService: CommunicationService,
        private conferenceService: ConferenceService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.conferenceService.query()
            .subscribe((res: HttpResponse<Conference[]>) => { this.conferences = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.communication.id !== undefined) {
            this.subscribeToSaveResponse(
                this.communicationService.update(this.communication));
        } else {
            this.subscribeToSaveResponse(
                this.communicationService.create(this.communication));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Communication>>) {
        result.subscribe((res: HttpResponse<Communication>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Communication) {
        this.eventManager.broadcast({ name: 'communicationListModification', content: 'OK'});
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
}

@Component({
    selector: 'jhi-communication-popup',
    template: ''
})
export class CommunicationPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private communicationPopupService: CommunicationPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.communicationPopupService
                    .open(CommunicationDialogComponent as Component, params['id']);
            } else {
                this.communicationPopupService
                    .open(CommunicationDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
