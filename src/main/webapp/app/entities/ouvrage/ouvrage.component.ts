import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Ouvrage } from './ouvrage.model';
import { OuvrageService } from './ouvrage.service';
import { Principal } from '../../shared';

@Component({
    selector: 'jhi-ouvrage',
    templateUrl: './ouvrage.component.html'
})
export class OuvrageComponent implements OnInit, OnDestroy {
ouvrages: Ouvrage[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private ouvrageService: OuvrageService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.ouvrageService.query().subscribe(
            (res: HttpResponse<Ouvrage[]>) => {
                this.ouvrages = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInOuvrages();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: Ouvrage) {
        return item.id;
    }
    registerChangeInOuvrages() {
        this.eventSubscriber = this.eventManager.subscribe('ouvrageListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
