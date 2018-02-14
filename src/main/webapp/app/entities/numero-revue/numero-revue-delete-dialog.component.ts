import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { NumeroRevue } from './numero-revue.model';
import { NumeroRevuePopupService } from './numero-revue-popup.service';
import { NumeroRevueService } from './numero-revue.service';

@Component({
    selector: 'jhi-numero-revue-delete-dialog',
    templateUrl: './numero-revue-delete-dialog.component.html'
})
export class NumeroRevueDeleteDialogComponent {

    numeroRevue: NumeroRevue;

    constructor(
        private numeroRevueService: NumeroRevueService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.numeroRevueService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'numeroRevueListModification',
                content: 'Deleted an numeroRevue'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-numero-revue-delete-popup',
    template: ''
})
export class NumeroRevueDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private numeroRevuePopupService: NumeroRevuePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.numeroRevuePopupService
                .open(NumeroRevueDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
