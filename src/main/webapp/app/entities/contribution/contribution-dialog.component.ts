import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Contribution } from './contribution.model';
import { ContributionPopupService } from './contribution-popup.service';
import { ContributionService } from './contribution.service';
import { Publication, PublicationService } from '../publication';
import { Acte, ActeService } from '../acte';
import { Ouvrage, OuvrageService } from '../ouvrage';
import { Revue, RevueService } from '../revue';
import { Memoire, MemoireService } from '../memoire';

@Component({
    selector: 'jhi-contribution-dialog',
    templateUrl: './contribution-dialog.component.html'
})
export class ContributionDialogComponent implements OnInit {

    contribution: Contribution;
    isSaving: boolean;

    publications: Publication[];

    actes: Acte[];

    ouvrages: Ouvrage[];

    revues: Revue[];

    memoires: Memoire[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private contributionService: ContributionService,
        private publicationService: PublicationService,
        private acteService: ActeService,
        private ouvrageService: OuvrageService,
        private revueService: RevueService,
        private memoireService: MemoireService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.publicationService
            .query({filter: 'contribution-is-null'})
            .subscribe((res: HttpResponse<Publication[]>) => {
                if (!this.contribution.publication || !this.contribution.publication.id) {
                    this.publications = res.body;
                } else {
                    this.publicationService
                        .find(this.contribution.publication.id)
                        .subscribe((subRes: HttpResponse<Publication>) => {
                            this.publications = [subRes.body].concat(res.body);
                        }, (subRes: HttpErrorResponse) => this.onError(subRes.message));
                }
            }, (res: HttpErrorResponse) => this.onError(res.message));
        this.acteService
            .query({filter: 'contribution-is-null'})
            .subscribe((res: HttpResponse<Acte[]>) => {
                if (!this.contribution.acte || !this.contribution.acte.id) {
                    this.actes = res.body;
                } else {
                    this.acteService
                        .find(this.contribution.acte.id)
                        .subscribe((subRes: HttpResponse<Acte>) => {
                            this.actes = [subRes.body].concat(res.body);
                        }, (subRes: HttpErrorResponse) => this.onError(subRes.message));
                }
            }, (res: HttpErrorResponse) => this.onError(res.message));
        this.ouvrageService
            .query({filter: 'contribution-is-null'})
            .subscribe((res: HttpResponse<Ouvrage[]>) => {
                if (!this.contribution.ouvrage || !this.contribution.ouvrage.id) {
                    this.ouvrages = res.body;
                } else {
                    this.ouvrageService
                        .find(this.contribution.ouvrage.id)
                        .subscribe((subRes: HttpResponse<Ouvrage>) => {
                            this.ouvrages = [subRes.body].concat(res.body);
                        }, (subRes: HttpErrorResponse) => this.onError(subRes.message));
                }
            }, (res: HttpErrorResponse) => this.onError(res.message));
        this.revueService
            .query({filter: 'contribution-is-null'})
            .subscribe((res: HttpResponse<Revue[]>) => {
                if (!this.contribution.revue || !this.contribution.revue.id) {
                    this.revues = res.body;
                } else {
                    this.revueService
                        .find(this.contribution.revue.id)
                        .subscribe((subRes: HttpResponse<Revue>) => {
                            this.revues = [subRes.body].concat(res.body);
                        }, (subRes: HttpErrorResponse) => this.onError(subRes.message));
                }
            }, (res: HttpErrorResponse) => this.onError(res.message));
        this.memoireService
            .query({filter: 'contribution-is-null'})
            .subscribe((res: HttpResponse<Memoire[]>) => {
                if (!this.contribution.memoire || !this.contribution.memoire.id) {
                    this.memoires = res.body;
                } else {
                    this.memoireService
                        .find(this.contribution.memoire.id)
                        .subscribe((subRes: HttpResponse<Memoire>) => {
                            this.memoires = [subRes.body].concat(res.body);
                        }, (subRes: HttpErrorResponse) => this.onError(subRes.message));
                }
            }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.contribution.id !== undefined) {
            this.subscribeToSaveResponse(
                this.contributionService.update(this.contribution));
        } else {
            this.subscribeToSaveResponse(
                this.contributionService.create(this.contribution));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Contribution>>) {
        result.subscribe((res: HttpResponse<Contribution>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Contribution) {
        this.eventManager.broadcast({ name: 'contributionListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackPublicationById(index: number, item: Publication) {
        return item.id;
    }

    trackActeById(index: number, item: Acte) {
        return item.id;
    }

    trackOuvrageById(index: number, item: Ouvrage) {
        return item.id;
    }

    trackRevueById(index: number, item: Revue) {
        return item.id;
    }

    trackMemoireById(index: number, item: Memoire) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-contribution-popup',
    template: ''
})
export class ContributionPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private contributionPopupService: ContributionPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.contributionPopupService
                    .open(ContributionDialogComponent as Component, params['id']);
            } else {
                this.contributionPopupService
                    .open(ContributionDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
