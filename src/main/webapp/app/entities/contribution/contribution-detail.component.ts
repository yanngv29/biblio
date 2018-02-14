import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { Contribution } from './contribution.model';
import { ContributionService } from './contribution.service';

@Component({
    selector: 'jhi-contribution-detail',
    templateUrl: './contribution-detail.component.html'
})
export class ContributionDetailComponent implements OnInit, OnDestroy {

    contribution: Contribution;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private contributionService: ContributionService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInContributions();
    }

    load(id) {
        this.contributionService.find(id)
            .subscribe((contributionResponse: HttpResponse<Contribution>) => {
                this.contribution = contributionResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInContributions() {
        this.eventSubscriber = this.eventManager.subscribe(
            'contributionListModification',
            (response) => this.load(this.contribution.id)
        );
    }
}
