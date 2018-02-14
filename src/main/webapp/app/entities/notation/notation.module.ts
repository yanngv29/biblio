import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BiblioSharedModule } from '../../shared';
import {
    NotationService,
    NotationPopupService,
    NotationComponent,
    NotationDetailComponent,
    NotationDialogComponent,
    NotationPopupComponent,
    NotationDeletePopupComponent,
    NotationDeleteDialogComponent,
    notationRoute,
    notationPopupRoute,
} from './';

const ENTITY_STATES = [
    ...notationRoute,
    ...notationPopupRoute,
];

@NgModule({
    imports: [
        BiblioSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        NotationComponent,
        NotationDetailComponent,
        NotationDialogComponent,
        NotationDeleteDialogComponent,
        NotationPopupComponent,
        NotationDeletePopupComponent,
    ],
    entryComponents: [
        NotationComponent,
        NotationDialogComponent,
        NotationPopupComponent,
        NotationDeleteDialogComponent,
        NotationDeletePopupComponent,
    ],
    providers: [
        NotationService,
        NotationPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class BiblioNotationModule {}
