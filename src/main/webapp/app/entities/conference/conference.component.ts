import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Conference } from './conference.model';
import { ConferenceService } from './conference.service';
import { Principal } from '../../shared';

@Component({
    selector: 'jhi-conference',
    templateUrl: './conference.component.html'
})
export class ConferenceComponent implements OnInit, OnDestroy {
conferences: Conference[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private conferenceService: ConferenceService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.conferenceService.query().subscribe(
            (res: HttpResponse<Conference[]>) => {
                this.conferences = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInConferences();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: Conference) {
        return item.id;
    }
    registerChangeInConferences() {
        this.eventSubscriber = this.eventManager.subscribe('conferenceListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
