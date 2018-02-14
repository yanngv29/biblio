import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Conference } from './conference.model';
import { ConferencePopupService } from './conference-popup.service';
import { ConferenceService } from './conference.service';

@Component({
    selector: 'jhi-conference-delete-dialog',
    templateUrl: './conference-delete-dialog.component.html'
})
export class ConferenceDeleteDialogComponent {

    conference: Conference;

    constructor(
        private conferenceService: ConferenceService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.conferenceService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'conferenceListModification',
                content: 'Deleted an conference'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-conference-delete-popup',
    template: ''
})
export class ConferenceDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private conferencePopupService: ConferencePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.conferencePopupService
                .open(ConferenceDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
