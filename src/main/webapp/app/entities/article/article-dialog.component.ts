import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Article } from './article.model';
import { ArticlePopupService } from './article-popup.service';
import { ArticleService } from './article.service';
import { NumeroRevue, NumeroRevueService } from '../numero-revue';
import { Actes, ActesService } from '../actes';
import { Conference, ConferenceService } from '../conference';
import { Chercheur, ChercheurService } from '../chercheur';

@Component({
    selector: 'jhi-article-dialog',
    templateUrl: './article-dialog.component.html'
})
export class ArticleDialogComponent implements OnInit {

    article: Article;
    isSaving: boolean;

    numerorevues: NumeroRevue[];

    actes: Actes[];

    conferences: Conference[];

    chercheurs: Chercheur[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private articleService: ArticleService,
        private numeroRevueService: NumeroRevueService,
        private actesService: ActesService,
        private conferenceService: ConferenceService,
        private chercheurService: ChercheurService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.numeroRevueService.query()
            .subscribe((res: HttpResponse<NumeroRevue[]>) => { this.numerorevues = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
        this.actesService.query()
            .subscribe((res: HttpResponse<Actes[]>) => { this.actes = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
        this.conferenceService.query()
            .subscribe((res: HttpResponse<Conference[]>) => { this.conferences = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
        this.chercheurService.query()
            .subscribe((res: HttpResponse<Chercheur[]>) => { this.chercheurs = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.article.id !== undefined) {
            this.subscribeToSaveResponse(
                this.articleService.update(this.article));
        } else {
            this.subscribeToSaveResponse(
                this.articleService.create(this.article));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Article>>) {
        result.subscribe((res: HttpResponse<Article>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Article) {
        this.eventManager.broadcast({ name: 'articleListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackNumeroRevueById(index: number, item: NumeroRevue) {
        return item.id;
    }

    trackActesById(index: number, item: Actes) {
        return item.id;
    }

    trackConferenceById(index: number, item: Conference) {
        return item.id;
    }

    trackChercheurById(index: number, item: Chercheur) {
        return item.id;
    }

    getSelected(selectedVals: Array<any>, option: any) {
        if (selectedVals) {
            for (let i = 0; i < selectedVals.length; i++) {
                if (option.id === selectedVals[i].id) {
                    return selectedVals[i];
                }
            }
        }
        return option;
    }
}

@Component({
    selector: 'jhi-article-popup',
    template: ''
})
export class ArticlePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private articlePopupService: ArticlePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.articlePopupService
                    .open(ArticleDialogComponent as Component, params['id']);
            } else {
                this.articlePopupService
                    .open(ArticleDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
