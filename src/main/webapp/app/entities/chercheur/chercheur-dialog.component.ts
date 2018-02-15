import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Chercheur } from './chercheur.model';
import { ChercheurPopupService } from './chercheur-popup.service';
import { ChercheurService } from './chercheur.service';
import { Actes, ActesService } from '../actes';
import { Article, ArticleService } from '../article';
import { Chapitre, ChapitreService } from '../chapitre';
import { Communication, CommunicationService } from '../communication';
import { Ouvrage, OuvrageService } from '../ouvrage';
import { PublicationGouvernementale, PublicationGouvernementaleService } from '../publication-gouvernementale';
import { NumeroRevue, NumeroRevueService } from '../numero-revue';
import { Memoire, MemoireService } from '../memoire';
import { Rapport, RapportService } from '../rapport';

@Component({
    selector: 'jhi-chercheur-dialog',
    templateUrl: './chercheur-dialog.component.html'
})
export class ChercheurDialogComponent implements OnInit {

    chercheur: Chercheur;
    isSaving: boolean;

    actes: Actes[];

    articles: Article[];

    chapitres: Chapitre[];

    communications: Communication[];

    ouvrages: Ouvrage[];

    publicationgouvernementales: PublicationGouvernementale[];

    numerorevues: NumeroRevue[];

    memoires: Memoire[];

    rapports: Rapport[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private chercheurService: ChercheurService,
        private actesService: ActesService,
        private articleService: ArticleService,
        private chapitreService: ChapitreService,
        private communicationService: CommunicationService,
        private ouvrageService: OuvrageService,
        private publicationGouvernementaleService: PublicationGouvernementaleService,
        private numeroRevueService: NumeroRevueService,
        private memoireService: MemoireService,
        private rapportService: RapportService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.actesService.query()
            .subscribe((res: HttpResponse<Actes[]>) => { this.actes = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
        this.articleService.query()
            .subscribe((res: HttpResponse<Article[]>) => { this.articles = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
        this.chapitreService.query()
            .subscribe((res: HttpResponse<Chapitre[]>) => { this.chapitres = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
        this.communicationService.query()
            .subscribe((res: HttpResponse<Communication[]>) => { this.communications = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
        this.ouvrageService.query()
            .subscribe((res: HttpResponse<Ouvrage[]>) => { this.ouvrages = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
        this.publicationGouvernementaleService.query()
            .subscribe((res: HttpResponse<PublicationGouvernementale[]>) => { this.publicationgouvernementales = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
        this.numeroRevueService.query()
            .subscribe((res: HttpResponse<NumeroRevue[]>) => { this.numerorevues = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
        this.memoireService.query()
            .subscribe((res: HttpResponse<Memoire[]>) => { this.memoires = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
        this.rapportService.query()
            .subscribe((res: HttpResponse<Rapport[]>) => { this.rapports = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.chercheur.id !== undefined) {
            this.subscribeToSaveResponse(
                this.chercheurService.update(this.chercheur));
        } else {
            this.subscribeToSaveResponse(
                this.chercheurService.create(this.chercheur));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Chercheur>>) {
        result.subscribe((res: HttpResponse<Chercheur>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Chercheur) {
        this.eventManager.broadcast({ name: 'chercheurListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackActesById(index: number, item: Actes) {
        return item.id;
    }

    trackArticleById(index: number, item: Article) {
        return item.id;
    }

    trackChapitreById(index: number, item: Chapitre) {
        return item.id;
    }

    trackCommunicationById(index: number, item: Communication) {
        return item.id;
    }

    trackOuvrageById(index: number, item: Ouvrage) {
        return item.id;
    }

    trackPublicationGouvernementaleById(index: number, item: PublicationGouvernementale) {
        return item.id;
    }

    trackNumeroRevueById(index: number, item: NumeroRevue) {
        return item.id;
    }

    trackMemoireById(index: number, item: Memoire) {
        return item.id;
    }

    trackRapportById(index: number, item: Rapport) {
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
    selector: 'jhi-chercheur-popup',
    template: ''
})
export class ChercheurPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private chercheurPopupService: ChercheurPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.chercheurPopupService
                    .open(ChercheurDialogComponent as Component, params['id']);
            } else {
                this.chercheurPopupService
                    .open(ChercheurDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
