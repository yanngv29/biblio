import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { NumeroRevueComponent } from './numero-revue.component';
import { NumeroRevueDetailComponent } from './numero-revue-detail.component';
import { NumeroRevuePopupComponent } from './numero-revue-dialog.component';
import { NumeroRevueDeletePopupComponent } from './numero-revue-delete-dialog.component';

export const numeroRevueRoute: Routes = [
    {
        path: 'numero-revue',
        component: NumeroRevueComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'biblioApp.numeroRevue.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'numero-revue/:id',
        component: NumeroRevueDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'biblioApp.numeroRevue.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const numeroRevuePopupRoute: Routes = [
    {
        path: 'numero-revue-new',
        component: NumeroRevuePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'biblioApp.numeroRevue.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'numero-revue/:id/edit',
        component: NumeroRevuePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'biblioApp.numeroRevue.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'numero-revue/:id/delete',
        component: NumeroRevueDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'biblioApp.numeroRevue.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
