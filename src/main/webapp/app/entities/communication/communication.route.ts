import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { CommunicationComponent } from './communication.component';
import { CommunicationDetailComponent } from './communication-detail.component';
import { CommunicationPopupComponent } from './communication-dialog.component';
import { CommunicationDeletePopupComponent } from './communication-delete-dialog.component';

export const communicationRoute: Routes = [
    {
        path: 'communication',
        component: CommunicationComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'biblioApp.communication.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'communication/:id',
        component: CommunicationDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'biblioApp.communication.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const communicationPopupRoute: Routes = [
    {
        path: 'communication-new',
        component: CommunicationPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'biblioApp.communication.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'communication/:id/edit',
        component: CommunicationPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'biblioApp.communication.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'communication/:id/delete',
        component: CommunicationDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'biblioApp.communication.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
