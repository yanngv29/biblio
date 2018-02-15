import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { ActesComponent } from './actes.component';
import { ActesDetailComponent } from './actes-detail.component';
import { ActesPopupComponent } from './actes-dialog.component';
import { ActesDeletePopupComponent } from './actes-delete-dialog.component';

export const actesRoute: Routes = [
    {
        path: 'actes',
        component: ActesComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'biblioApp.actes.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'actes/:id',
        component: ActesDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'biblioApp.actes.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const actesPopupRoute: Routes = [
    {
        path: 'actes-new',
        component: ActesPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'biblioApp.actes.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'actes/:id/edit',
        component: ActesPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'biblioApp.actes.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'actes/:id/delete',
        component: ActesDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'biblioApp.actes.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
