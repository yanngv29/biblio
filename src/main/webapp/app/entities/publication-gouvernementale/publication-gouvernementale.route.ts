import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PublicationGouvernementaleComponent } from './publication-gouvernementale.component';
import { PublicationGouvernementaleDetailComponent } from './publication-gouvernementale-detail.component';
import { PublicationGouvernementalePopupComponent } from './publication-gouvernementale-dialog.component';
import { PublicationGouvernementaleDeletePopupComponent } from './publication-gouvernementale-delete-dialog.component';

export const publicationGouvernementaleRoute: Routes = [
    {
        path: 'publication-gouvernementale',
        component: PublicationGouvernementaleComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'biblioApp.publicationGouvernementale.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'publication-gouvernementale/:id',
        component: PublicationGouvernementaleDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'biblioApp.publicationGouvernementale.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const publicationGouvernementalePopupRoute: Routes = [
    {
        path: 'publication-gouvernementale-new',
        component: PublicationGouvernementalePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'biblioApp.publicationGouvernementale.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'publication-gouvernementale/:id/edit',
        component: PublicationGouvernementalePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'biblioApp.publicationGouvernementale.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'publication-gouvernementale/:id/delete',
        component: PublicationGouvernementaleDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'biblioApp.publicationGouvernementale.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
