import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BiblioSharedModule } from '../../shared';
import {
    NumeroRevueService,
    NumeroRevuePopupService,
    NumeroRevueComponent,
    NumeroRevueDetailComponent,
    NumeroRevueDialogComponent,
    NumeroRevuePopupComponent,
    NumeroRevueDeletePopupComponent,
    NumeroRevueDeleteDialogComponent,
    numeroRevueRoute,
    numeroRevuePopupRoute,
} from './';

const ENTITY_STATES = [
    ...numeroRevueRoute,
    ...numeroRevuePopupRoute,
];

@NgModule({
    imports: [
        BiblioSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        NumeroRevueComponent,
        NumeroRevueDetailComponent,
        NumeroRevueDialogComponent,
        NumeroRevueDeleteDialogComponent,
        NumeroRevuePopupComponent,
        NumeroRevueDeletePopupComponent,
    ],
    entryComponents: [
        NumeroRevueComponent,
        NumeroRevueDialogComponent,
        NumeroRevuePopupComponent,
        NumeroRevueDeleteDialogComponent,
        NumeroRevueDeletePopupComponent,
    ],
    providers: [
        NumeroRevueService,
        NumeroRevuePopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class BiblioNumeroRevueModule {}
