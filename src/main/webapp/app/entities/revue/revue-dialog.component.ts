import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Revue } from './revue.model';
import { RevuePopupService } from './revue-popup.service';
import { RevueService } from './revue.service';

@Component({
    selector: 'jhi-revue-dialog',
    templateUrl: './revue-dialog.component.html'
})
export class RevueDialogComponent implements OnInit {

    revue: Revue;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private revueService: RevueService,
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
        if (this.revue.id !== undefined) {
            this.subscribeToSaveResponse(
                this.revueService.update(this.revue));
        } else {
            this.subscribeToSaveResponse(
                this.revueService.create(this.revue));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Revue>>) {
        result.subscribe((res: HttpResponse<Revue>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Revue) {
        this.eventManager.broadcast({ name: 'revueListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-revue-popup',
    template: ''
})
export class RevuePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private revuePopupService: RevuePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.revuePopupService
                    .open(RevueDialogComponent as Component, params['id']);
            } else {
                this.revuePopupService
                    .open(RevueDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
