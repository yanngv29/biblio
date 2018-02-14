import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Memoire } from './memoire.model';
import { MemoireService } from './memoire.service';
import { Principal } from '../../shared';

@Component({
    selector: 'jhi-memoire',
    templateUrl: './memoire.component.html'
})
export class MemoireComponent implements OnInit, OnDestroy {
memoires: Memoire[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private memoireService: MemoireService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.memoireService.query().subscribe(
            (res: HttpResponse<Memoire[]>) => {
                this.memoires = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInMemoires();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: Memoire) {
        return item.id;
    }
    registerChangeInMemoires() {
        this.eventSubscriber = this.eventManager.subscribe('memoireListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
