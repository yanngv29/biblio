import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BiblioSharedModule } from '../../shared';
import {
    ActeService,
    ActePopupService,
    ActeComponent,
    ActeDetailComponent,
    ActeDialogComponent,
    ActePopupComponent,
    ActeDeletePopupComponent,
    ActeDeleteDialogComponent,
    acteRoute,
    actePopupRoute,
} from './';

const ENTITY_STATES = [
    ...acteRoute,
    ...actePopupRoute,
];

@NgModule({
    imports: [
        BiblioSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        ActeComponent,
        ActeDetailComponent,
        ActeDialogComponent,
        ActeDeleteDialogComponent,
        ActePopupComponent,
        ActeDeletePopupComponent,
    ],
    entryComponents: [
        ActeComponent,
        ActeDialogComponent,
        ActePopupComponent,
        ActeDeleteDialogComponent,
        ActeDeletePopupComponent,
    ],
    providers: [
        ActeService,
        ActePopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class BiblioActeModule {}
