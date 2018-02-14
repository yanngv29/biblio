import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Publication } from './publication.model';
import { PublicationPopupService } from './publication-popup.service';
import { PublicationService } from './publication.service';

@Component({
    selector: 'jhi-publication-delete-dialog',
    templateUrl: './publication-delete-dialog.component.html'
})
export class PublicationDeleteDialogComponent {

    publication: Publication;

    constructor(
        private publicationService: PublicationService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.publicationService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'publicationListModification',
                content: 'Deleted an publication'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-publication-delete-popup',
    template: ''
})
export class PublicationDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private publicationPopupService: PublicationPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.publicationPopupService
                .open(PublicationDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
