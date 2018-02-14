import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { PublicationGouvernementale } from './publication-gouvernementale.model';
import { PublicationGouvernementaleService } from './publication-gouvernementale.service';

@Component({
    selector: 'jhi-publication-gouvernementale-detail',
    templateUrl: './publication-gouvernementale-detail.component.html'
})
export class PublicationGouvernementaleDetailComponent implements OnInit, OnDestroy {

    publicationGouvernementale: PublicationGouvernementale;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private publicationGouvernementaleService: PublicationGouvernementaleService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInPublicationGouvernementales();
    }

    load(id) {
        this.publicationGouvernementaleService.find(id)
            .subscribe((publicationGouvernementaleResponse: HttpResponse<PublicationGouvernementale>) => {
                this.publicationGouvernementale = publicationGouvernementaleResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInPublicationGouvernementales() {
        this.eventSubscriber = this.eventManager.subscribe(
            'publicationGouvernementaleListModification',
            (response) => this.load(this.publicationGouvernementale.id)
        );
    }
}
