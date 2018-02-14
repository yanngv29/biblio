import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BiblioSharedModule } from '../../shared';
import {
    CommunicationService,
    CommunicationPopupService,
    CommunicationComponent,
    CommunicationDetailComponent,
    CommunicationDialogComponent,
    CommunicationPopupComponent,
    CommunicationDeletePopupComponent,
    CommunicationDeleteDialogComponent,
    communicationRoute,
    communicationPopupRoute,
} from './';

const ENTITY_STATES = [
    ...communicationRoute,
    ...communicationPopupRoute,
];

@NgModule({
    imports: [
        BiblioSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        CommunicationComponent,
        CommunicationDetailComponent,
        CommunicationDialogComponent,
        CommunicationDeleteDialogComponent,
        CommunicationPopupComponent,
        CommunicationDeletePopupComponent,
    ],
    entryComponents: [
        CommunicationComponent,
        CommunicationDialogComponent,
        CommunicationPopupComponent,
        CommunicationDeleteDialogComponent,
        CommunicationDeletePopupComponent,
    ],
    providers: [
        CommunicationService,
        CommunicationPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class BiblioCommunicationModule {}
