import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Ouvrage } from './ouvrage.model';
import { OuvragePopupService } from './ouvrage-popup.service';
import { OuvrageService } from './ouvrage.service';

@Component({
    selector: 'jhi-ouvrage-dialog',
    templateUrl: './ouvrage-dialog.component.html'
})
export class OuvrageDialogComponent implements OnInit {

    ouvrage: Ouvrage;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private ouvrageService: OuvrageService,
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
        if (this.ouvrage.id !== undefined) {
            this.subscribeToSaveResponse(
                this.ouvrageService.update(this.ouvrage));
        } else {
            this.subscribeToSaveResponse(
                this.ouvrageService.create(this.ouvrage));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Ouvrage>>) {
        result.subscribe((res: HttpResponse<Ouvrage>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Ouvrage) {
        this.eventManager.broadcast({ name: 'ouvrageListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-ouvrage-popup',
    template: ''
})
export class OuvragePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private ouvragePopupService: OuvragePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.ouvragePopupService
                    .open(OuvrageDialogComponent as Component, params['id']);
            } else {
                this.ouvragePopupService
                    .open(OuvrageDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
