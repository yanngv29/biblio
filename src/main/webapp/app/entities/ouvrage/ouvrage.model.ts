import { BaseEntity } from './../../shared';

export const enum TypeOuvrage {
    'SCIENTIFIQUE',
    'VULGARISATION'
}

export const enum TypeParticipation {
    'COMPLETE',
    'PARTIELLE'
}

export class Ouvrage implements BaseEntity {
    constructor(
        public id?: number,
        public idOuvrage?: string,
        public titre?: string,
        public type?: TypeOuvrage,
        public participation?: TypeParticipation,
        public annee?: number,
        public numeroEdition?: number,
        public volume?: number,
        public traduction?: string,
        public lieu?: string,
        public maisonEdition?: string,
        public collection?: string,
        public hal?: string,
        public notations?: BaseEntity[],
        public chapitres?: BaseEntity[],
    ) {
    }
}
