import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Chercheur } from './chercheur.model';
import { ChercheurService } from './chercheur.service';
import { Principal } from '../../shared';

@Component({
    selector: 'jhi-chercheur',
    templateUrl: './chercheur.component.html'
})
export class ChercheurComponent implements OnInit, OnDestroy {
chercheurs: Chercheur[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private chercheurService: ChercheurService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.chercheurService.query().subscribe(
            (res: HttpResponse<Chercheur[]>) => {
                this.chercheurs = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInChercheurs();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: Chercheur) {
        return item.id;
    }
    registerChangeInChercheurs() {
        this.eventSubscriber = this.eventManager.subscribe('chercheurListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
