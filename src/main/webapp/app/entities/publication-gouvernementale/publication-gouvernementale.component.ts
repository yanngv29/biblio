import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { PublicationGouvernementale } from './publication-gouvernementale.model';
import { PublicationGouvernementaleService } from './publication-gouvernementale.service';
import { Principal } from '../../shared';

@Component({
    selector: 'jhi-publication-gouvernementale',
    templateUrl: './publication-gouvernementale.component.html'
})
export class PublicationGouvernementaleComponent implements OnInit, OnDestroy {
publicationGouvernementales: PublicationGouvernementale[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private publicationGouvernementaleService: PublicationGouvernementaleService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.publicationGouvernementaleService.query().subscribe(
            (res: HttpResponse<PublicationGouvernementale[]>) => {
                this.publicationGouvernementales = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInPublicationGouvernementales();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: PublicationGouvernementale) {
        return item.id;
    }
    registerChangeInPublicationGouvernementales() {
        this.eventSubscriber = this.eventManager.subscribe('publicationGouvernementaleListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
