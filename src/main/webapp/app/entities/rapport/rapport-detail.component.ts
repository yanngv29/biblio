import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { Rapport } from './rapport.model';
import { RapportService } from './rapport.service';

@Component({
    selector: 'jhi-rapport-detail',
    templateUrl: './rapport-detail.component.html'
})
export class RapportDetailComponent implements OnInit, OnDestroy {

    rapport: Rapport;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private rapportService: RapportService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInRapports();
    }

    load(id) {
        this.rapportService.find(id)
            .subscribe((rapportResponse: HttpResponse<Rapport>) => {
                this.rapport = rapportResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInRapports() {
        this.eventSubscriber = this.eventManager.subscribe(
            'rapportListModification',
            (response) => this.load(this.rapport.id)
        );
    }
}
