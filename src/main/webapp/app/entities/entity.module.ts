import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { BiblioActesModule } from './actes/actes.module';
import { BiblioArticleModule } from './article/article.module';
import { BiblioChapitreModule } from './chapitre/chapitre.module';
import { BiblioCommunicationModule } from './communication/communication.module';
import { BiblioConferenceModule } from './conference/conference.module';
import { BiblioChercheurModule } from './chercheur/chercheur.module';
import { BiblioMemoireModule } from './memoire/memoire.module';
import { BiblioNoteModule } from './note/note.module';
import { BiblioNumeroRevueModule } from './numero-revue/numero-revue.module';
import { BiblioOuvrageModule } from './ouvrage/ouvrage.module';
import { BiblioRapportModule } from './rapport/rapport.module';
import { BiblioRevueModule } from './revue/revue.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        BiblioActesModule,
        BiblioArticleModule,
        BiblioChapitreModule,
        BiblioCommunicationModule,
        BiblioConferenceModule,
        BiblioChercheurModule,
        BiblioMemoireModule,
        BiblioNoteModule,
        BiblioNumeroRevueModule,
        BiblioOuvrageModule,
        BiblioRapportModule,
        BiblioRevueModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class BiblioEntityModule {}
