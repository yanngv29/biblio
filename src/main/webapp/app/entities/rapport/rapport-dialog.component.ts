import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Rapport } from './rapport.model';
import { RapportPopupService } from './rapport-popup.service';
import { RapportService } from './rapport.service';

@Component({
    selector: 'jhi-rapport-dialog',
    templateUrl: './rapport-dialog.component.html'
})
export class RapportDialogComponent implements OnInit {

    rapport: Rapport;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private rapportService: RapportService,
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
        if (this.rapport.id !== undefined) {
            this.subscribeToSaveResponse(
                this.rapportService.update(this.rapport));
        } else {
            this.subscribeToSaveResponse(
                this.rapportService.create(this.rapport));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Rapport>>) {
        result.subscribe((res: HttpResponse<Rapport>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Rapport) {
        this.eventManager.broadcast({ name: 'rapportListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-rapport-popup',
    template: ''
})
export class RapportPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private rapportPopupService: RapportPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.rapportPopupService
                    .open(RapportDialogComponent as Component, params['id']);
            } else {
                this.rapportPopupService
                    .open(RapportDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
