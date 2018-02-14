import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { Notation } from './notation.model';
import { NotationService } from './notation.service';

@Component({
    selector: 'jhi-notation-detail',
    templateUrl: './notation-detail.component.html'
})
export class NotationDetailComponent implements OnInit, OnDestroy {

    notation: Notation;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private notationService: NotationService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInNotations();
    }

    load(id) {
        this.notationService.find(id)
            .subscribe((notationResponse: HttpResponse<Notation>) => {
                this.notation = notationResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInNotations() {
        this.eventSubscriber = this.eventManager.subscribe(
            'notationListModification',
            (response) => this.load(this.notation.id)
        );
    }
}
