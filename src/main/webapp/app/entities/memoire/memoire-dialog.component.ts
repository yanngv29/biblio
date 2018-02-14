import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Memoire } from './memoire.model';
import { MemoirePopupService } from './memoire-popup.service';
import { MemoireService } from './memoire.service';

@Component({
    selector: 'jhi-memoire-dialog',
    templateUrl: './memoire-dialog.component.html'
})
export class MemoireDialogComponent implements OnInit {

    memoire: Memoire;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private memoireService: MemoireService,
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
        if (this.memoire.id !== undefined) {
            this.subscribeToSaveResponse(
                this.memoireService.update(this.memoire));
        } else {
            this.subscribeToSaveResponse(
                this.memoireService.create(this.memoire));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Memoire>>) {
        result.subscribe((res: HttpResponse<Memoire>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Memoire) {
        this.eventManager.broadcast({ name: 'memoireListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-memoire-popup',
    template: ''
})
export class MemoirePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private memoirePopupService: MemoirePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.memoirePopupService
                    .open(MemoireDialogComponent as Component, params['id']);
            } else {
                this.memoirePopupService
                    .open(MemoireDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
