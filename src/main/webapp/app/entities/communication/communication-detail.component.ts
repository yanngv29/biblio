import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { Communication } from './communication.model';
import { CommunicationService } from './communication.service';

@Component({
    selector: 'jhi-communication-detail',
    templateUrl: './communication-detail.component.html'
})
export class CommunicationDetailComponent implements OnInit, OnDestroy {

    communication: Communication;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private communicationService: CommunicationService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInCommunications();
    }

    load(id) {
        this.communicationService.find(id)
            .subscribe((communicationResponse: HttpResponse<Communication>) => {
                this.communication = communicationResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInCommunications() {
        this.eventSubscriber = this.eventManager.subscribe(
            'communicationListModification',
            (response) => this.load(this.communication.id)
        );
    }
}
