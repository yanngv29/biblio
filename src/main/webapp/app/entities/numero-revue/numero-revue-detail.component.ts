import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { NumeroRevue } from './numero-revue.model';
import { NumeroRevueService } from './numero-revue.service';

@Component({
    selector: 'jhi-numero-revue-detail',
    templateUrl: './numero-revue-detail.component.html'
})
export class NumeroRevueDetailComponent implements OnInit, OnDestroy {

    numeroRevue: NumeroRevue;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private numeroRevueService: NumeroRevueService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInNumeroRevues();
    }

    load(id) {
        this.numeroRevueService.find(id)
            .subscribe((numeroRevueResponse: HttpResponse<NumeroRevue>) => {
                this.numeroRevue = numeroRevueResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInNumeroRevues() {
        this.eventSubscriber = this.eventManager.subscribe(
            'numeroRevueListModification',
            (response) => this.load(this.numeroRevue.id)
        );
    }
}
