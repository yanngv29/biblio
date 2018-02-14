import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BiblioSharedModule } from '../../shared';
import {
    ContributionService,
    ContributionPopupService,
    ContributionComponent,
    ContributionDetailComponent,
    ContributionDialogComponent,
    ContributionPopupComponent,
    ContributionDeletePopupComponent,
    ContributionDeleteDialogComponent,
    contributionRoute,
    contributionPopupRoute,
} from './';

const ENTITY_STATES = [
    ...contributionRoute,
    ...contributionPopupRoute,
];

@NgModule({
    imports: [
        BiblioSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        ContributionComponent,
        ContributionDetailComponent,
        ContributionDialogComponent,
        ContributionDeleteDialogComponent,
        ContributionPopupComponent,
        ContributionDeletePopupComponent,
    ],
    entryComponents: [
        ContributionComponent,
        ContributionDialogComponent,
        ContributionPopupComponent,
        ContributionDeleteDialogComponent,
        ContributionDeletePopupComponent,
    ],
    providers: [
        ContributionService,
        ContributionPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class BiblioContributionModule {}
