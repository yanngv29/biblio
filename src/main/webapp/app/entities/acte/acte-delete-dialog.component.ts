import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Acte } from './acte.model';
import { ActePopupService } from './acte-popup.service';
import { ActeService } from './acte.service';

@Component({
    selector: 'jhi-acte-delete-dialog',
    templateUrl: './acte-delete-dialog.component.html'
})
export class ActeDeleteDialogComponent {

    acte: Acte;

    constructor(
        private acteService: ActeService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.acteService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'acteListModification',
                content: 'Deleted an acte'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-acte-delete-popup',
    template: ''
})
export class ActeDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private actePopupService: ActePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.actePopupService
                .open(ActeDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
