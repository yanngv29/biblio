import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BiblioSharedModule } from '../../shared';
import {
    PublicationService,
    PublicationPopupService,
    PublicationComponent,
    PublicationDetailComponent,
    PublicationDialogComponent,
    PublicationPopupComponent,
    PublicationDeletePopupComponent,
    PublicationDeleteDialogComponent,
    publicationRoute,
    publicationPopupRoute,
} from './';

const ENTITY_STATES = [
    ...publicationRoute,
    ...publicationPopupRoute,
];

@NgModule({
    imports: [
        BiblioSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        PublicationComponent,
        PublicationDetailComponent,
        PublicationDialogComponent,
        PublicationDeleteDialogComponent,
        PublicationPopupComponent,
        PublicationDeletePopupComponent,
    ],
    entryComponents: [
        PublicationComponent,
        PublicationDialogComponent,
        PublicationPopupComponent,
        PublicationDeleteDialogComponent,
        PublicationDeletePopupComponent,
    ],
    providers: [
        PublicationService,
        PublicationPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class BiblioPublicationModule {}
