import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { Conference } from './conference.model';
import { ConferenceService } from './conference.service';

@Component({
    selector: 'jhi-conference-detail',
    templateUrl: './conference-detail.component.html'
})
export class ConferenceDetailComponent implements OnInit, OnDestroy {

    conference: Conference;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private conferenceService: ConferenceService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInConferences();
    }

    load(id) {
        this.conferenceService.find(id)
            .subscribe((conferenceResponse: HttpResponse<Conference>) => {
                this.conference = conferenceResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInConferences() {
        this.eventSubscriber = this.eventManager.subscribe(
            'conferenceListModification',
            (response) => this.load(this.conference.id)
        );
    }
}
