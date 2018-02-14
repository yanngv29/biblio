import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { NumeroRevue } from './numero-revue.model';
import { NumeroRevueService } from './numero-revue.service';
import { Principal } from '../../shared';

@Component({
    selector: 'jhi-numero-revue',
    templateUrl: './numero-revue.component.html'
})
export class NumeroRevueComponent implements OnInit, OnDestroy {
numeroRevues: NumeroRevue[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private numeroRevueService: NumeroRevueService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.numeroRevueService.query().subscribe(
            (res: HttpResponse<NumeroRevue[]>) => {
                this.numeroRevues = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInNumeroRevues();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: NumeroRevue) {
        return item.id;
    }
    registerChangeInNumeroRevues() {
        this.eventSubscriber = this.eventManager.subscribe('numeroRevueListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
