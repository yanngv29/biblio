import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { PublicationGouvernementale } from './publication-gouvernementale.model';
import { PublicationGouvernementalePopupService } from './publication-gouvernementale-popup.service';
import { PublicationGouvernementaleService } from './publication-gouvernementale.service';

@Component({
    selector: 'jhi-publication-gouvernementale-dialog',
    templateUrl: './publication-gouvernementale-dialog.component.html'
})
export class PublicationGouvernementaleDialogComponent implements OnInit {

    publicationGouvernementale: PublicationGouvernementale;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private publicationGouvernementaleService: PublicationGouvernementaleService,
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
        if (this.publicationGouvernementale.id !== undefined) {
            this.subscribeToSaveResponse(
                this.publicationGouvernementaleService.update(this.publicationGouvernementale));
        } else {
            this.subscribeToSaveResponse(
                this.publicationGouvernementaleService.create(this.publicationGouvernementale));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<PublicationGouvernementale>>) {
        result.subscribe((res: HttpResponse<PublicationGouvernementale>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: PublicationGouvernementale) {
        this.eventManager.broadcast({ name: 'publicationGouvernementaleListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-publication-gouvernementale-popup',
    template: ''
})
export class PublicationGouvernementalePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private publicationGouvernementalePopupService: PublicationGouvernementalePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.publicationGouvernementalePopupService
                    .open(PublicationGouvernementaleDialogComponent as Component, params['id']);
            } else {
                this.publicationGouvernementalePopupService
                    .open(PublicationGouvernementaleDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
