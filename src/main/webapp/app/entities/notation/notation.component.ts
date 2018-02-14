import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Notation } from './notation.model';
import { NotationService } from './notation.service';
import { Principal } from '../../shared';

@Component({
    selector: 'jhi-notation',
    templateUrl: './notation.component.html'
})
export class NotationComponent implements OnInit, OnDestroy {
notations: Notation[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private notationService: NotationService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.notationService.query().subscribe(
            (res: HttpResponse<Notation[]>) => {
                this.notations = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInNotations();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: Notation) {
        return item.id;
    }
    registerChangeInNotations() {
        this.eventSubscriber = this.eventManager.subscribe('notationListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
