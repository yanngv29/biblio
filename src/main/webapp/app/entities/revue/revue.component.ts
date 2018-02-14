import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Revue } from './revue.model';
import { RevueService } from './revue.service';
import { Principal } from '../../shared';

@Component({
    selector: 'jhi-revue',
    templateUrl: './revue.component.html'
})
export class RevueComponent implements OnInit, OnDestroy {
revues: Revue[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private revueService: RevueService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.revueService.query().subscribe(
            (res: HttpResponse<Revue[]>) => {
                this.revues = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInRevues();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: Revue) {
        return item.id;
    }
    registerChangeInRevues() {
        this.eventSubscriber = this.eventManager.subscribe('revueListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
