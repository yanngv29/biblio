import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BiblioSharedModule } from '../../shared';
import {
    PublicationGouvernementaleService,
    PublicationGouvernementalePopupService,
    PublicationGouvernementaleComponent,
    PublicationGouvernementaleDetailComponent,
    PublicationGouvernementaleDialogComponent,
    PublicationGouvernementalePopupComponent,
    PublicationGouvernementaleDeletePopupComponent,
    PublicationGouvernementaleDeleteDialogComponent,
    publicationGouvernementaleRoute,
    publicationGouvernementalePopupRoute,
} from './';

const ENTITY_STATES = [
    ...publicationGouvernementaleRoute,
    ...publicationGouvernementalePopupRoute,
];

@NgModule({
    imports: [
        BiblioSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        PublicationGouvernementaleComponent,
        PublicationGouvernementaleDetailComponent,
        PublicationGouvernementaleDialogComponent,
        PublicationGouvernementaleDeleteDialogComponent,
        PublicationGouvernementalePopupComponent,
        PublicationGouvernementaleDeletePopupComponent,
    ],
    entryComponents: [
        PublicationGouvernementaleComponent,
        PublicationGouvernementaleDialogComponent,
        PublicationGouvernementalePopupComponent,
        PublicationGouvernementaleDeleteDialogComponent,
        PublicationGouvernementaleDeletePopupComponent,
    ],
    providers: [
        PublicationGouvernementaleService,
        PublicationGouvernementalePopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class BiblioPublicationGouvernementaleModule {}
