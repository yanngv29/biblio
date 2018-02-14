import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { RapportComponent } from './rapport.component';
import { RapportDetailComponent } from './rapport-detail.component';
import { RapportPopupComponent } from './rapport-dialog.component';
import { RapportDeletePopupComponent } from './rapport-delete-dialog.component';

export const rapportRoute: Routes = [
    {
        path: 'rapport',
        component: RapportComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'biblioApp.rapport.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'rapport/:id',
        component: RapportDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'biblioApp.rapport.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const rapportPopupRoute: Routes = [
    {
        path: 'rapport-new',
        component: RapportPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'biblioApp.rapport.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'rapport/:id/edit',
        component: RapportPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'biblioApp.rapport.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'rapport/:id/delete',
        component: RapportDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'biblioApp.rapport.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
