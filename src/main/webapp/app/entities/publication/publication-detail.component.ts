import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { Publication } from './publication.model';
import { PublicationService } from './publication.service';

@Component({
    selector: 'jhi-publication-detail',
    templateUrl: './publication-detail.component.html'
})
export class PublicationDetailComponent implements OnInit, OnDestroy {

    publication: Publication;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private publicationService: PublicationService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInPublications();
    }

    load(id) {
        this.publicationService.find(id)
            .subscribe((publicationResponse: HttpResponse<Publication>) => {
                this.publication = publicationResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInPublications() {
        this.eventSubscriber = this.eventManager.subscribe(
            'publicationListModification',
            (response) => this.load(this.publication.id)
        );
    }
}
