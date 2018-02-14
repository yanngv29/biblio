import { BaseEntity } from './../../shared';

export const enum TypeRapport {
    'RECHERCHE',
    'PROJET'
}

export class Rapport implements BaseEntity {
    constructor(
        public id?: number,
        public idMemoire?: string,
        public type?: TypeRapport,
        public date?: any,
        public lieu?: string,
        public maisonEdition?: string,
        public notations?: BaseEntity[],
    ) {
    }
}
