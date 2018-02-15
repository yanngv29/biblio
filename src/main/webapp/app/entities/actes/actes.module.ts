import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BiblioSharedModule } from '../../shared';
import {
    ActesService,
    ActesPopupService,
    ActesComponent,
    ActesDetailComponent,
    ActesDialogComponent,
    ActesPopupComponent,
    ActesDeletePopupComponent,
    ActesDeleteDialogComponent,
    actesRoute,
    actesPopupRoute,
} from './';

const ENTITY_STATES = [
    ...actesRoute,
    ...actesPopupRoute,
];

@NgModule({
    imports: [
        BiblioSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        ActesComponent,
        ActesDetailComponent,
        ActesDialogComponent,
        ActesDeleteDialogComponent,
        ActesPopupComponent,
        ActesDeletePopupComponent,
    ],
    entryComponents: [
        ActesComponent,
        ActesDialogComponent,
        ActesPopupComponent,
        ActesDeleteDialogComponent,
        ActesDeletePopupComponent,
    ],
    providers: [
        ActesService,
        ActesPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class BiblioActesModule {}
