import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Acte } from './acte.model';
import { ActePopupService } from './acte-popup.service';
import { ActeService } from './acte.service';
import { Conference, ConferenceService } from '../conference';

@Component({
    selector: 'jhi-acte-dialog',
    templateUrl: './acte-dialog.component.html'
})
export class ActeDialogComponent implements OnInit {

    acte: Acte;
    isSaving: boolean;

    conferences: Conference[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private acteService: ActeService,
        private conferenceService: ConferenceService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.conferenceService
            .query({filter: 'acte-is-null'})
            .subscribe((res: HttpResponse<Conference[]>) => {
                if (!this.acte.conference || !this.acte.conference.id) {
                    this.conferences = res.body;
                } else {
                    this.conferenceService
                        .find(this.acte.conference.id)
                        .subscribe((subRes: HttpResponse<Conference>) => {
                            this.conferences = [subRes.body].concat(res.body);
                        }, (subRes: HttpErrorResponse) => this.onError(subRes.message));
                }
            }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.acte.id !== undefined) {
            this.subscribeToSaveResponse(
                this.acteService.update(this.acte));
        } else {
            this.subscribeToSaveResponse(
                this.acteService.create(this.acte));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Acte>>) {
        result.subscribe((res: HttpResponse<Acte>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Acte) {
        this.eventManager.broadcast({ name: 'acteListModification', content: 'OK'});
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
    selector: 'jhi-acte-popup',
    template: ''
})
export class ActePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private actePopupService: ActePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.actePopupService
                    .open(ActeDialogComponent as Component, params['id']);
            } else {
                this.actePopupService
                    .open(ActeDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
