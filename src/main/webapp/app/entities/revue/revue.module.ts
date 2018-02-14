import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BiblioSharedModule } from '../../shared';
import {
    RevueService,
    RevuePopupService,
    RevueComponent,
    RevueDetailComponent,
    RevueDialogComponent,
    RevuePopupComponent,
    RevueDeletePopupComponent,
    RevueDeleteDialogComponent,
    revueRoute,
    revuePopupRoute,
} from './';

const ENTITY_STATES = [
    ...revueRoute,
    ...revuePopupRoute,
];

@NgModule({
    imports: [
        BiblioSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        RevueComponent,
        RevueDetailComponent,
        RevueDialogComponent,
        RevueDeleteDialogComponent,
        RevuePopupComponent,
        RevueDeletePopupComponent,
    ],
    entryComponents: [
        RevueComponent,
        RevueDialogComponent,
        RevuePopupComponent,
        RevueDeleteDialogComponent,
        RevueDeletePopupComponent,
    ],
    providers: [
        RevueService,
        RevuePopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class BiblioRevueModule {}
