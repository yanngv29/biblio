import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Rapport } from './rapport.model';
import { RapportPopupService } from './rapport-popup.service';
import { RapportService } from './rapport.service';

@Component({
    selector: 'jhi-rapport-delete-dialog',
    templateUrl: './rapport-delete-dialog.component.html'
})
export class RapportDeleteDialogComponent {

    rapport: Rapport;

    constructor(
        private rapportService: RapportService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.rapportService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'rapportListModification',
                content: 'Deleted an rapport'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-rapport-delete-popup',
    template: ''
})
export class RapportDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private rapportPopupService: RapportPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.rapportPopupService
                .open(RapportDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
