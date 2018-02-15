import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Chercheur } from './chercheur.model';
import { ChercheurPopupService } from './chercheur-popup.service';
import { ChercheurService } from './chercheur.service';

@Component({
    selector: 'jhi-chercheur-delete-dialog',
    templateUrl: './chercheur-delete-dialog.component.html'
})
export class ChercheurDeleteDialogComponent {

    chercheur: Chercheur;

    constructor(
        private chercheurService: ChercheurService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.chercheurService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'chercheurListModification',
                content: 'Deleted an chercheur'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-chercheur-delete-popup',
    template: ''
})
export class ChercheurDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private chercheurPopupService: ChercheurPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.chercheurPopupService
                .open(ChercheurDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
