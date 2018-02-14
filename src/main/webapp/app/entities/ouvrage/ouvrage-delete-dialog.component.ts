import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Ouvrage } from './ouvrage.model';
import { OuvragePopupService } from './ouvrage-popup.service';
import { OuvrageService } from './ouvrage.service';

@Component({
    selector: 'jhi-ouvrage-delete-dialog',
    templateUrl: './ouvrage-delete-dialog.component.html'
})
export class OuvrageDeleteDialogComponent {

    ouvrage: Ouvrage;

    constructor(
        private ouvrageService: OuvrageService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.ouvrageService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'ouvrageListModification',
                content: 'Deleted an ouvrage'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-ouvrage-delete-popup',
    template: ''
})
export class OuvrageDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private ouvragePopupService: OuvragePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.ouvragePopupService
                .open(OuvrageDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
