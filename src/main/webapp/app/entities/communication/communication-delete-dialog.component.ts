import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Communication } from './communication.model';
import { CommunicationPopupService } from './communication-popup.service';
import { CommunicationService } from './communication.service';

@Component({
    selector: 'jhi-communication-delete-dialog',
    templateUrl: './communication-delete-dialog.component.html'
})
export class CommunicationDeleteDialogComponent {

    communication: Communication;

    constructor(
        private communicationService: CommunicationService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.communicationService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'communicationListModification',
                content: 'Deleted an communication'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-communication-delete-popup',
    template: ''
})
export class CommunicationDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private communicationPopupService: CommunicationPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.communicationPopupService
                .open(CommunicationDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
