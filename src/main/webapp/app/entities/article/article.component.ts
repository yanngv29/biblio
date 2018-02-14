import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Article } from './article.model';
import { ArticleService } from './article.service';
import { Principal } from '../../shared';

@Component({
    selector: 'jhi-article',
    templateUrl: './article.component.html'
})
export class ArticleComponent implements OnInit, OnDestroy {
articles: Article[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private articleService: ArticleService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.articleService.query().subscribe(
            (res: HttpResponse<Article[]>) => {
                this.articles = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInArticles();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: Article) {
        return item.id;
    }
    registerChangeInArticles() {
        this.eventSubscriber = this.eventManager.subscribe('articleListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
