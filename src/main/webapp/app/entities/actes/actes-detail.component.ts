import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { Actes } from './actes.model';
import { ActesService } from './actes.service';

@Component({
    selector: 'jhi-actes-detail',
    templateUrl: './actes-detail.component.html'
})
export class ActesDetailComponent implements OnInit, OnDestroy {

    actes: Actes;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private actesService: ActesService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInActes();
    }

    load(id) {
        this.actesService.find(id)
            .subscribe((actesResponse: HttpResponse<Actes>) => {
                this.actes = actesResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInActes() {
        this.eventSubscriber = this.eventManager.subscribe(
            'actesListModification',
            (response) => this.load(this.actes.id)
        );
    }
}
