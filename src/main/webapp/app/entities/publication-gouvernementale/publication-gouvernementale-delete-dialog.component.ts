import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { PublicationGouvernementale } from './publication-gouvernementale.model';
import { PublicationGouvernementalePopupService } from './publication-gouvernementale-popup.service';
import { PublicationGouvernementaleService } from './publication-gouvernementale.service';

@Component({
    selector: 'jhi-publication-gouvernementale-delete-dialog',
    templateUrl: './publication-gouvernementale-delete-dialog.component.html'
})
export class PublicationGouvernementaleDeleteDialogComponent {

    publicationGouvernementale: PublicationGouvernementale;

    constructor(
        private publicationGouvernementaleService: PublicationGouvernementaleService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.publicationGouvernementaleService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'publicationGouvernementaleListModification',
                content: 'Deleted an publicationGouvernementale'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-publication-gouvernementale-delete-popup',
    template: ''
})
export class PublicationGouvernementaleDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private publicationGouvernementalePopupService: PublicationGouvernementalePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.publicationGouvernementalePopupService
                .open(PublicationGouvernementaleDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
