import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { BiblioActeModule } from './acte/acte.module';
import { BiblioArticleModule } from './article/article.module';
import { BiblioChapitreModule } from './chapitre/chapitre.module';
import { BiblioCommunicationModule } from './communication/communication.module';
import { BiblioConferenceModule } from './conference/conference.module';
import { BiblioContributionModule } from './contribution/contribution.module';
import { BiblioMemoireModule } from './memoire/memoire.module';
import { BiblioNotationModule } from './notation/notation.module';
import { BiblioNumeroRevueModule } from './numero-revue/numero-revue.module';
import { BiblioOuvrageModule } from './ouvrage/ouvrage.module';
import { BiblioPublicationModule } from './publication/publication.module';
import { BiblioPublicationGouvernementaleModule } from './publication-gouvernementale/publication-gouvernementale.module';
import { BiblioRapportModule } from './rapport/rapport.module';
import { BiblioRevueModule } from './revue/revue.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        BiblioActeModule,
        BiblioArticleModule,
        BiblioChapitreModule,
        BiblioCommunicationModule,
        BiblioConferenceModule,
        BiblioContributionModule,
        BiblioMemoireModule,
        BiblioNotationModule,
        BiblioNumeroRevueModule,
        BiblioOuvrageModule,
        BiblioPublicationModule,
        BiblioPublicationGouvernementaleModule,
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
