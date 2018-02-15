import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { Chercheur } from './chercheur.model';
import { ChercheurService } from './chercheur.service';

@Component({
    selector: 'jhi-chercheur-detail',
    templateUrl: './chercheur-detail.component.html'
})
export class ChercheurDetailComponent implements OnInit, OnDestroy {

    chercheur: Chercheur;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private chercheurService: ChercheurService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInChercheurs();
    }

    load(id) {
        this.chercheurService.find(id)
            .subscribe((chercheurResponse: HttpResponse<Chercheur>) => {
                this.chercheur = chercheurResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInChercheurs() {
        this.eventSubscriber = this.eventManager.subscribe(
            'chercheurListModification',
            (response) => this.load(this.chercheur.id)
        );
    }
}
