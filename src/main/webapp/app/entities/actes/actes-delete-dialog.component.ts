import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Actes } from './actes.model';
import { ActesPopupService } from './actes-popup.service';
import { ActesService } from './actes.service';

@Component({
    selector: 'jhi-actes-delete-dialog',
    templateUrl: './actes-delete-dialog.component.html'
})
export class ActesDeleteDialogComponent {

    actes: Actes;

    constructor(
        private actesService: ActesService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.actesService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'actesListModification',
                content: 'Deleted an actes'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-actes-delete-popup',
    template: ''
})
export class ActesDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private actesPopupService: ActesPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.actesPopupService
                .open(ActesDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
