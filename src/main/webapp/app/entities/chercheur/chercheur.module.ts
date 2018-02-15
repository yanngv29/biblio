import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BiblioSharedModule } from '../../shared';
import {
    ChercheurService,
    ChercheurPopupService,
    ChercheurComponent,
    ChercheurDetailComponent,
    ChercheurDialogComponent,
    ChercheurPopupComponent,
    ChercheurDeletePopupComponent,
    ChercheurDeleteDialogComponent,
    chercheurRoute,
    chercheurPopupRoute,
} from './';

const ENTITY_STATES = [
    ...chercheurRoute,
    ...chercheurPopupRoute,
];

@NgModule({
    imports: [
        BiblioSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        ChercheurComponent,
        ChercheurDetailComponent,
        ChercheurDialogComponent,
        ChercheurDeleteDialogComponent,
        ChercheurPopupComponent,
        ChercheurDeletePopupComponent,
    ],
    entryComponents: [
        ChercheurComponent,
        ChercheurDialogComponent,
        ChercheurPopupComponent,
        ChercheurDeleteDialogComponent,
        ChercheurDeletePopupComponent,
    ],
    providers: [
        ChercheurService,
        ChercheurPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class BiblioChercheurModule {}
