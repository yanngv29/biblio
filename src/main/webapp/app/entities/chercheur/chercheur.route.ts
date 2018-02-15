import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { ChercheurComponent } from './chercheur.component';
import { ChercheurDetailComponent } from './chercheur-detail.component';
import { ChercheurPopupComponent } from './chercheur-dialog.component';
import { ChercheurDeletePopupComponent } from './chercheur-delete-dialog.component';

export const chercheurRoute: Routes = [
    {
        path: 'chercheur',
        component: ChercheurComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'biblioApp.chercheur.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'chercheur/:id',
        component: ChercheurDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'biblioApp.chercheur.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const chercheurPopupRoute: Routes = [
    {
        path: 'chercheur-new',
        component: ChercheurPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'biblioApp.chercheur.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'chercheur/:id/edit',
        component: ChercheurPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'biblioApp.chercheur.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'chercheur/:id/delete',
        component: ChercheurDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'biblioApp.chercheur.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
