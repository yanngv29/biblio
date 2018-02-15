import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Actes } from './actes.model';
import { ActesService } from './actes.service';
import { Principal } from '../../shared';

@Component({
    selector: 'jhi-actes',
    templateUrl: './actes.component.html'
})
export class ActesComponent implements OnInit, OnDestroy {
actes: Actes[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private actesService: ActesService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.actesService.query().subscribe(
            (res: HttpResponse<Actes[]>) => {
                this.actes = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInActes();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: Actes) {
        return item.id;
    }
    registerChangeInActes() {
        this.eventSubscriber = this.eventManager.subscribe('actesListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
