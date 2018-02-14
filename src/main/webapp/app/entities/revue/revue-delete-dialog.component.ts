import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Revue } from './revue.model';
import { RevuePopupService } from './revue-popup.service';
import { RevueService } from './revue.service';

@Component({
    selector: 'jhi-revue-delete-dialog',
    templateUrl: './revue-delete-dialog.component.html'
})
export class RevueDeleteDialogComponent {

    revue: Revue;

    constructor(
        private revueService: RevueService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.revueService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'revueListModification',
                content: 'Deleted an revue'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-revue-delete-popup',
    template: ''
})
export class RevueDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private revuePopupService: RevuePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.revuePopupService
                .open(RevueDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
