import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Publication } from './publication.model';
import { PublicationPopupService } from './publication-popup.service';
import { PublicationService } from './publication.service';
import { Article, ArticleService } from '../article';
import { Communication, CommunicationService } from '../communication';
import { Memoire, MemoireService } from '../memoire';
import { Ouvrage, OuvrageService } from '../ouvrage';
import { PublicationGouvernementale, PublicationGouvernementaleService } from '../publication-gouvernementale';
import { Rapport, RapportService } from '../rapport';

@Component({
    selector: 'jhi-publication-dialog',
    templateUrl: './publication-dialog.component.html'
})
export class PublicationDialogComponent implements OnInit {

    publication: Publication;
    isSaving: boolean;

    articles: Article[];

    communications: Communication[];

    memoires: Memoire[];

    ouvrages: Ouvrage[];

    publicationgouvernementales: PublicationGouvernementale[];

    rapports: Rapport[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private publicationService: PublicationService,
        private articleService: ArticleService,
        private communicationService: CommunicationService,
        private memoireService: MemoireService,
        private ouvrageService: OuvrageService,
        private publicationGouvernementaleService: PublicationGouvernementaleService,
        private rapportService: RapportService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.articleService
            .query({filter: 'publication-is-null'})
            .subscribe((res: HttpResponse<Article[]>) => {
                if (!this.publication.article || !this.publication.article.id) {
                    this.articles = res.body;
                } else {
                    this.articleService
                        .find(this.publication.article.id)
                        .subscribe((subRes: HttpResponse<Article>) => {
                            this.articles = [subRes.body].concat(res.body);
                        }, (subRes: HttpErrorResponse) => this.onError(subRes.message));
                }
            }, (res: HttpErrorResponse) => this.onError(res.message));
        this.communicationService
            .query({filter: 'publication-is-null'})
            .subscribe((res: HttpResponse<Communication[]>) => {
                if (!this.publication.communication || !this.publication.communication.id) {
                    this.communications = res.body;
                } else {
                    this.communicationService
                        .find(this.publication.communication.id)
                        .subscribe((subRes: HttpResponse<Communication>) => {
                            this.communications = [subRes.body].concat(res.body);
                        }, (subRes: HttpErrorResponse) => this.onError(subRes.message));
                }
            }, (res: HttpErrorResponse) => this.onError(res.message));
        this.memoireService
            .query({filter: 'publication-is-null'})
            .subscribe((res: HttpResponse<Memoire[]>) => {
                if (!this.publication.memoire || !this.publication.memoire.id) {
                    this.memoires = res.body;
                } else {
                    this.memoireService
                        .find(this.publication.memoire.id)
                        .subscribe((subRes: HttpResponse<Memoire>) => {
                            this.memoires = [subRes.body].concat(res.body);
                        }, (subRes: HttpErrorResponse) => this.onError(subRes.message));
                }
            }, (res: HttpErrorResponse) => this.onError(res.message));
        this.ouvrageService
            .query({filter: 'publication-is-null'})
            .subscribe((res: HttpResponse<Ouvrage[]>) => {
                if (!this.publication.ouvrage || !this.publication.ouvrage.id) {
                    this.ouvrages = res.body;
                } else {
                    this.ouvrageService
                        .find(this.publication.ouvrage.id)
                        .subscribe((subRes: HttpResponse<Ouvrage>) => {
                            this.ouvrages = [subRes.body].concat(res.body);
                        }, (subRes: HttpErrorResponse) => this.onError(subRes.message));
                }
            }, (res: HttpErrorResponse) => this.onError(res.message));
        this.publicationGouvernementaleService
            .query({filter: 'publication-is-null'})
            .subscribe((res: HttpResponse<PublicationGouvernementale[]>) => {
                if (!this.publication.publicationGouvernementale || !this.publication.publicationGouvernementale.id) {
                    this.publicationgouvernementales = res.body;
                } else {
                    this.publicationGouvernementaleService
                        .find(this.publication.publicationGouvernementale.id)
                        .subscribe((subRes: HttpResponse<PublicationGouvernementale>) => {
                            this.publicationgouvernementales = [subRes.body].concat(res.body);
                        }, (subRes: HttpErrorResponse) => this.onError(subRes.message));
                }
            }, (res: HttpErrorResponse) => this.onError(res.message));
        this.rapportService
            .query({filter: 'publication-is-null'})
            .subscribe((res: HttpResponse<Rapport[]>) => {
                if (!this.publication.rapport || !this.publication.rapport.id) {
                    this.rapports = res.body;
                } else {
                    this.rapportService
                        .find(this.publication.rapport.id)
                        .subscribe((subRes: HttpResponse<Rapport>) => {
                            this.rapports = [subRes.body].concat(res.body);
                        }, (subRes: HttpErrorResponse) => this.onError(subRes.message));
                }
            }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.publication.id !== undefined) {
            this.subscribeToSaveResponse(
                this.publicationService.update(this.publication));
        } else {
            this.subscribeToSaveResponse(
                this.publicationService.create(this.publication));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Publication>>) {
        result.subscribe((res: HttpResponse<Publication>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Publication) {
        this.eventManager.broadcast({ name: 'publicationListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackArticleById(index: number, item: Article) {
        return item.id;
    }

    trackCommunicationById(index: number, item: Communication) {
        return item.id;
    }

    trackMemoireById(index: number, item: Memoire) {
        return item.id;
    }

    trackOuvrageById(index: number, item: Ouvrage) {
        return item.id;
    }

    trackPublicationGouvernementaleById(index: number, item: PublicationGouvernementale) {
        return item.id;
    }

    trackRapportById(index: number, item: Rapport) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-publication-popup',
    template: ''
})
export class PublicationPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private publicationPopupService: PublicationPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.publicationPopupService
                    .open(PublicationDialogComponent as Component, params['id']);
            } else {
                this.publicationPopupService
                    .open(PublicationDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
