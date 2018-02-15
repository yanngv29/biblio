import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Actes } from './actes.model';
import { ActesPopupService } from './actes-popup.service';
import { ActesService } from './actes.service';
import { Conference, ConferenceService } from '../conference';

@Component({
    selector: 'jhi-actes-dialog',
    templateUrl: './actes-dialog.component.html'
})
export class ActesDialogComponent implements OnInit {

    actes: Actes;
    isSaving: boolean;

    conferences: Conference[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private actesService: ActesService,
        private conferenceService: ConferenceService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.conferenceService
            .query({filter: 'actes-is-null'})
            .subscribe((res: HttpResponse<Conference[]>) => {
                if (!this.actes.conference || !this.actes.conference.id) {
                    this.conferences = res.body;
                } else {
                    this.conferenceService
                        .find(this.actes.conference.id)
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
        if (this.actes.id !== undefined) {
            this.subscribeToSaveResponse(
                this.actesService.update(this.actes));
        } else {
            this.subscribeToSaveResponse(
                this.actesService.create(this.actes));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Actes>>) {
        result.subscribe((res: HttpResponse<Actes>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Actes) {
        this.eventManager.broadcast({ name: 'actesListModification', content: 'OK'});
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
    selector: 'jhi-actes-popup',
    template: ''
})
export class ActesPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private actesPopupService: ActesPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.actesPopupService
                    .open(ActesDialogComponent as Component, params['id']);
            } else {
                this.actesPopupService
                    .open(ActesDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
