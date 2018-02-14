import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { RevueComponent } from './revue.component';
import { RevueDetailComponent } from './revue-detail.component';
import { RevuePopupComponent } from './revue-dialog.component';
import { RevueDeletePopupComponent } from './revue-delete-dialog.component';

export const revueRoute: Routes = [
    {
        path: 'revue',
        component: RevueComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'biblioApp.revue.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'revue/:id',
        component: RevueDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'biblioApp.revue.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const revuePopupRoute: Routes = [
    {
        path: 'revue-new',
        component: RevuePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'biblioApp.revue.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'revue/:id/edit',
        component: RevuePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'biblioApp.revue.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'revue/:id/delete',
        component: RevueDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'biblioApp.revue.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
