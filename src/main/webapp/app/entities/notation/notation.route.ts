import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { NotationComponent } from './notation.component';
import { NotationDetailComponent } from './notation-detail.component';
import { NotationPopupComponent } from './notation-dialog.component';
import { NotationDeletePopupComponent } from './notation-delete-dialog.component';

export const notationRoute: Routes = [
    {
        path: 'notation',
        component: NotationComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'biblioApp.notation.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'notation/:id',
        component: NotationDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'biblioApp.notation.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const notationPopupRoute: Routes = [
    {
        path: 'notation-new',
        component: NotationPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'biblioApp.notation.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'notation/:id/edit',
        component: NotationPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'biblioApp.notation.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'notation/:id/delete',
        component: NotationDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'biblioApp.notation.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
