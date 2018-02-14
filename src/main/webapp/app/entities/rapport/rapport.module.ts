import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BiblioSharedModule } from '../../shared';
import {
    RapportService,
    RapportPopupService,
    RapportComponent,
    RapportDetailComponent,
    RapportDialogComponent,
    RapportPopupComponent,
    RapportDeletePopupComponent,
    RapportDeleteDialogComponent,
    rapportRoute,
    rapportPopupRoute,
} from './';

const ENTITY_STATES = [
    ...rapportRoute,
    ...rapportPopupRoute,
];

@NgModule({
    imports: [
        BiblioSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        RapportComponent,
        RapportDetailComponent,
        RapportDialogComponent,
        RapportDeleteDialogComponent,
        RapportPopupComponent,
        RapportDeletePopupComponent,
    ],
    entryComponents: [
        RapportComponent,
        RapportDialogComponent,
        RapportPopupComponent,
        RapportDeleteDialogComponent,
        RapportDeletePopupComponent,
    ],
    providers: [
        RapportService,
        RapportPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class BiblioRapportModule {}
