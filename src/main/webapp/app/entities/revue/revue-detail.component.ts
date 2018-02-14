import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { Revue } from './revue.model';
import { RevueService } from './revue.service';

@Component({
    selector: 'jhi-revue-detail',
    templateUrl: './revue-detail.component.html'
})
export class RevueDetailComponent implements OnInit, OnDestroy {

    revue: Revue;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private revueService: RevueService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInRevues();
    }

    load(id) {
        this.revueService.find(id)
            .subscribe((revueResponse: HttpResponse<Revue>) => {
                this.revue = revueResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInRevues() {
        this.eventSubscriber = this.eventManager.subscribe(
            'revueListModification',
            (response) => this.load(this.revue.id)
        );
    }
}
