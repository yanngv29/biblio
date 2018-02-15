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
        public titreArticle?: string,
        public typeArticle?: TypeArticle,
        public pageDebutArticle?: string,
        public pageFinArticle?: string,
        public langueArticle?: string,
        public lienArticle?: string,
        public halArticle?: string,
        public diversArticle?: string,
        public numeroRevue?: BaseEntity,
        public actes?: BaseEntity,
        public conference?: BaseEntity,
        public auteurs?: BaseEntity[],
    ) {
    }
}
