import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { Acte } from './acte.model';
import { ActeService } from './acte.service';

@Component({
    selector: 'jhi-acte-detail',
    templateUrl: './acte-detail.component.html'
})
export class ActeDetailComponent implements OnInit, OnDestroy {

    acte: Acte;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private acteService: ActeService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInActes();
    }

    load(id) {
        this.acteService.find(id)
            .subscribe((acteResponse: HttpResponse<Acte>) => {
                this.acte = acteResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInActes() {
        this.eventSubscriber = this.eventManager.subscribe(
            'acteListModification',
            (response) => this.load(this.acte.id)
        );
    }
}
