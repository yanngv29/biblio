import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { NumeroRevue } from './numero-revue.model';
import { NumeroRevuePopupService } from './numero-revue-popup.service';
import { NumeroRevueService } from './numero-revue.service';
import { Revue, RevueService } from '../revue';

@Component({
    selector: 'jhi-numero-revue-dialog',
    templateUrl: './numero-revue-dialog.component.html'
})
export class NumeroRevueDialogComponent implements OnInit {

    numeroRevue: NumeroRevue;
    isSaving: boolean;

    revues: Revue[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private numeroRevueService: NumeroRevueService,
        private revueService: RevueService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.revueService.query()
            .subscribe((res: HttpResponse<Revue[]>) => { this.revues = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.numeroRevue.id !== undefined) {
            this.subscribeToSaveResponse(
                this.numeroRevueService.update(this.numeroRevue));
        } else {
            this.subscribeToSaveResponse(
                this.numeroRevueService.create(this.numeroRevue));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<NumeroRevue>>) {
        result.subscribe((res: HttpResponse<NumeroRevue>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: NumeroRevue) {
        this.eventManager.broadcast({ name: 'numeroRevueListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackRevueById(index: number, item: Revue) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-numero-revue-popup',
    template: ''
})
export class NumeroRevuePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private numeroRevuePopupService: NumeroRevuePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.numeroRevuePopupService
                    .open(NumeroRevueDialogComponent as Component, params['id']);
            } else {
                this.numeroRevuePopupService
                    .open(NumeroRevueDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
