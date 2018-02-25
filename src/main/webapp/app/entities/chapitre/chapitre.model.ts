import { BaseEntity } from './../../shared';

export class Chapitre implements BaseEntity {
    constructor(
        public id?: number,
        public titreChapitre?: string,
        public sousTitreChapitre?: string,
        public pageDebutChapitre?: string,
        public pageFinChapitre?: string,
        public langueChapitre?: string,
        public lienChapitre?: string,
        public halChapitre?: string,
        public diversChapitre?: string,
    ) {
    }
}
