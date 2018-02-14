import { BaseEntity } from './../../shared';

export const enum TypeArticle {
    'PUBLIE',
    'EN_PREPUBLICATION',
    'SOUS_PRESSE',
    'COURT'
}

export class Article implements BaseEntity {
    constructor(
        public id?: number,
        public idArticle?: string,
        public type?: TypeArticle,
        public pageDebut?: string,
        public pageFin?: string,
        public hal?: string,
        public numeroRevue?: BaseEntity,
        public acte?: BaseEntity,
        public conference?: BaseEntity,
    ) {
    }
}
