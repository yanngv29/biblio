import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BiblioSharedModule } from '../../shared';
import {
    OuvrageService,
    OuvragePopupService,
    OuvrageComponent,
    OuvrageDetailComponent,
    OuvrageDialogComponent,
    OuvragePopupComponent,
    OuvrageDeletePopupComponent,
    OuvrageDeleteDialogComponent,
    ouvrageRoute,
    ouvragePopupRoute,
} from './';

const ENTITY_STATES = [
    ...ouvrageRoute,
    ...ouvragePopupRoute,
];

@NgModule({
    imports: [
        BiblioSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        OuvrageComponent,
        OuvrageDetailComponent,
        OuvrageDialogComponent,
        OuvrageDeleteDialogComponent,
        OuvragePopupComponent,
        OuvrageDeletePopupComponent,
    ],
    entryComponents: [
        OuvrageComponent,
        OuvrageDialogComponent,
        OuvragePopupComponent,
        OuvrageDeleteDialogComponent,
        OuvrageDeletePopupComponent,
    ],
    providers: [
        OuvrageService,
        OuvragePopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class BiblioOuvrageModule {}
