import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { Ouvrage } from './ouvrage.model';
import { OuvrageService } from './ouvrage.service';

@Component({
    selector: 'jhi-ouvrage-detail',
    templateUrl: './ouvrage-detail.component.html'
})
export class OuvrageDetailComponent implements OnInit, OnDestroy {

    ouvrage: Ouvrage;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private ouvrageService: OuvrageService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInOuvrages();
    }

    load(id) {
        this.ouvrageService.find(id)
            .subscribe((ouvrageResponse: HttpResponse<Ouvrage>) => {
                this.ouvrage = ouvrageResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInOuvrages() {
        this.eventSubscriber = this.eventManager.subscribe(
            'ouvrageListModification',
            (response) => this.load(this.ouvrage.id)
        );
    }
}
