import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Notation } from './notation.model';
import { NotationPopupService } from './notation-popup.service';
import { NotationService } from './notation.service';
import { Conference, ConferenceService } from '../conference';
import { Memoire, MemoireService } from '../memoire';
import { Ouvrage, OuvrageService } from '../ouvrage';
import { PublicationGouvernementale, PublicationGouvernementaleService } from '../publication-gouvernementale';
import { Rapport, RapportService } from '../rapport';
import { Revue, RevueService } from '../revue';

@Component({
    selector: 'jhi-notation-dialog',
    templateUrl: './notation-dialog.component.html'
})
export class NotationDialogComponent implements OnInit {

    notation: Notation;
    isSaving: boolean;

    conferences: Conference[];

    memoires: Memoire[];

    ouvrages: Ouvrage[];

    publicationgouvernementales: PublicationGouvernementale[];

    rapports: Rapport[];

    revues: Revue[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private notationService: NotationService,
        private conferenceService: ConferenceService,
        private memoireService: MemoireService,
        private ouvrageService: OuvrageService,
        private publicationGouvernementaleService: PublicationGouvernementaleService,
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
        this.publicationGouvernementaleService.query()
            .subscribe((res: HttpResponse<PublicationGouvernementale[]>) => { this.publicationgouvernementales = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
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
        if (this.notation.id !== undefined) {
            this.subscribeToSaveResponse(
                this.notationService.update(this.notation));
        } else {
            this.subscribeToSaveResponse(
                this.notationService.create(this.notation));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Notation>>) {
        result.subscribe((res: HttpResponse<Notation>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Notation) {
        this.eventManager.broadcast({ name: 'notationListModification', content: 'OK'});
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

    trackPublicationGouvernementaleById(index: number, item: PublicationGouvernementale) {
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
    selector: 'jhi-notation-popup',
    template: ''
})
export class NotationPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private notationPopupService: NotationPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.notationPopupService
                    .open(NotationDialogComponent as Component, params['id']);
            } else {
                this.notationPopupService
                    .open(NotationDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
