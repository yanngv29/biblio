import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Contribution } from './contribution.model';
import { ContributionService } from './contribution.service';
import { Principal } from '../../shared';

@Component({
    selector: 'jhi-contribution',
    templateUrl: './contribution.component.html'
})
export class ContributionComponent implements OnInit, OnDestroy {
contributions: Contribution[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private contributionService: ContributionService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.contributionService.query().subscribe(
            (res: HttpResponse<Contribution[]>) => {
                this.contributions = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInContributions();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: Contribution) {
        return item.id;
    }
    registerChangeInContributions() {
        this.eventSubscriber = this.eventManager.subscribe('contributionListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
