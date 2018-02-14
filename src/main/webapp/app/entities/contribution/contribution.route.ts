import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { ContributionComponent } from './contribution.component';
import { ContributionDetailComponent } from './contribution-detail.component';
import { ContributionPopupComponent } from './contribution-dialog.component';
import { ContributionDeletePopupComponent } from './contribution-delete-dialog.component';

export const contributionRoute: Routes = [
    {
        path: 'contribution',
        component: ContributionComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'biblioApp.contribution.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'contribution/:id',
        component: ContributionDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'biblioApp.contribution.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const contributionPopupRoute: Routes = [
    {
        path: 'contribution-new',
        component: ContributionPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'biblioApp.contribution.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'contribution/:id/edit',
        component: ContributionPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'biblioApp.contribution.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'contribution/:id/delete',
        component: ContributionDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'biblioApp.contribution.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
