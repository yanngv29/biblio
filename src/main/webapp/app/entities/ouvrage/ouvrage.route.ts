import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { OuvrageComponent } from './ouvrage.component';
import { OuvrageDetailComponent } from './ouvrage-detail.component';
import { OuvragePopupComponent } from './ouvrage-dialog.component';
import { OuvrageDeletePopupComponent } from './ouvrage-delete-dialog.component';

export const ouvrageRoute: Routes = [
    {
        path: 'ouvrage',
        component: OuvrageComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'biblioApp.ouvrage.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'ouvrage/:id',
        component: OuvrageDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'biblioApp.ouvrage.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const ouvragePopupRoute: Routes = [
    {
        path: 'ouvrage-new',
        component: OuvragePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'biblioApp.ouvrage.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'ouvrage/:id/edit',
        component: OuvragePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'biblioApp.ouvrage.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'ouvrage/:id/delete',
        component: OuvrageDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'biblioApp.ouvrage.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
