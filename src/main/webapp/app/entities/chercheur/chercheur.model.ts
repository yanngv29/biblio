import { BaseEntity } from './../../shared';

export const enum TypeChercheur {
    'PUBLIANT',
    'ENCADRANT',
    'DIRECTEUR',
    'EDITIEUR',
    'COORDINATEUR'
}

export class Chercheur implements BaseEntity {
    constructor(
        public id?: number,
        public nomChercheur?: string,
        public typeChercheur?: TypeChercheur,
        public rangChercheur?: string,
        public actes?: BaseEntity[],
        public articles?: BaseEntity[],
        public chapitres?: BaseEntity[],
        public communications?: BaseEntity[],
        public ouvrages?: BaseEntity[],
        public publicationGouvernementales?: BaseEntity[],
        public revues?: BaseEntity[],
        public memoires?: BaseEntity[],
        public rapports?: BaseEntity[],
    ) {
    }
}
