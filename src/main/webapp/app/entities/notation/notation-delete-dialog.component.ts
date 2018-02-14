import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Notation } from './notation.model';
import { NotationPopupService } from './notation-popup.service';
import { NotationService } from './notation.service';

@Component({
    selector: 'jhi-notation-delete-dialog',
    templateUrl: './notation-delete-dialog.component.html'
})
export class NotationDeleteDialogComponent {

    notation: Notation;

    constructor(
        private notationService: NotationService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.notationService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'notationListModification',
                content: 'Deleted an notation'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-notation-delete-popup',
    template: ''
})
export class NotationDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private notationPopupService: NotationPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.notationPopupService
                .open(NotationDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
