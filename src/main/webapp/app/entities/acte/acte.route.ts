import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { ActeComponent } from './acte.component';
import { ActeDetailComponent } from './acte-detail.component';
import { ActePopupComponent } from './acte-dialog.component';
import { ActeDeletePopupComponent } from './acte-delete-dialog.component';

export const acteRoute: Routes = [
    {
        path: 'acte',
        component: ActeComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'biblioApp.acte.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'acte/:id',
        component: ActeDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'biblioApp.acte.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const actePopupRoute: Routes = [
    {
        path: 'acte-new',
        component: ActePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'biblioApp.acte.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'acte/:id/edit',
        component: ActePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'biblioApp.acte.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'acte/:id/delete',
        component: ActeDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'biblioApp.acte.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
