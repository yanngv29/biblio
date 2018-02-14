import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { ConferenceComponent } from './conference.component';
import { ConferenceDetailComponent } from './conference-detail.component';
import { ConferencePopupComponent } from './conference-dialog.component';
import { ConferenceDeletePopupComponent } from './conference-delete-dialog.component';

export const conferenceRoute: Routes = [
    {
        path: 'conference',
        component: ConferenceComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'biblioApp.conference.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'conference/:id',
        component: ConferenceDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'biblioApp.conference.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const conferencePopupRoute: Routes = [
    {
        path: 'conference-new',
        component: ConferencePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'biblioApp.conference.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'conference/:id/edit',
        component: ConferencePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'biblioApp.conference.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'conference/:id/delete',
        component: ConferenceDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'biblioApp.conference.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
