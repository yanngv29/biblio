import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Publication } from './publication.model';
import { PublicationService } from './publication.service';
import { Principal } from '../../shared';

@Component({
    selector: 'jhi-publication',
    templateUrl: './publication.component.html'
})
export class PublicationComponent implements OnInit, OnDestroy {
publications: Publication[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private publicationService: PublicationService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.publicationService.query().subscribe(
            (res: HttpResponse<Publication[]>) => {
                this.publications = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInPublications();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: Publication) {
        return item.id;
    }
    registerChangeInPublications() {
        this.eventSubscriber = this.eventManager.subscribe('publicationListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
