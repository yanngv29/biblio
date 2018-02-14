import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Rapport } from './rapport.model';
import { RapportService } from './rapport.service';
import { Principal } from '../../shared';

@Component({
    selector: 'jhi-rapport',
    templateUrl: './rapport.component.html'
})
export class RapportComponent implements OnInit, OnDestroy {
rapports: Rapport[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private rapportService: RapportService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.rapportService.query().subscribe(
            (res: HttpResponse<Rapport[]>) => {
                this.rapports = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInRapports();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: Rapport) {
        return item.id;
    }
    registerChangeInRapports() {
        this.eventSubscriber = this.eventManager.subscribe('rapportListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
