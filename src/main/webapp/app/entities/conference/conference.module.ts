import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BiblioSharedModule } from '../../shared';
import {
    ConferenceService,
    ConferencePopupService,
    ConferenceComponent,
    ConferenceDetailComponent,
    ConferenceDialogComponent,
    ConferencePopupComponent,
    ConferenceDeletePopupComponent,
    ConferenceDeleteDialogComponent,
    conferenceRoute,
    conferencePopupRoute,
} from './';

const ENTITY_STATES = [
    ...conferenceRoute,
    ...conferencePopupRoute,
];

@NgModule({
    imports: [
        BiblioSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        ConferenceComponent,
        ConferenceDetailComponent,
        ConferenceDialogComponent,
        ConferenceDeleteDialogComponent,
        ConferencePopupComponent,
        ConferenceDeletePopupComponent,
    ],
    entryComponents: [
        ConferenceComponent,
        ConferenceDialogComponent,
        ConferencePopupComponent,
        ConferenceDeleteDialogComponent,
        ConferenceDeletePopupComponent,
    ],
    providers: [
        ConferenceService,
        ConferencePopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class BiblioConferenceModule {}
