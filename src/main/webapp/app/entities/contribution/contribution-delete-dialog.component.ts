import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Contribution } from './contribution.model';
import { ContributionPopupService } from './contribution-popup.service';
import { ContributionService } from './contribution.service';

@Component({
    selector: 'jhi-contribution-delete-dialog',
    templateUrl: './contribution-delete-dialog.component.html'
})
export class ContributionDeleteDialogComponent {

    contribution: Contribution;

    constructor(
        private contributionService: ContributionService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.contributionService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'contributionListModification',
                content: 'Deleted an contribution'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-contribution-delete-popup',
    template: ''
})
export class ContributionDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private contributionPopupService: ContributionPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.contributionPopupService
                .open(ContributionDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
