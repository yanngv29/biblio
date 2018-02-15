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
        public titreOuvrage?: string,
        public typeOuvrage?: TypeOuvrage,
        public participationOuvrage?: TypeParticipation,
        public anneeOuvrage?: number,
        public numeroEditionOuvrage?: number,
        public volumeOuvrage?: number,
        public traductionOuvrage?: string,
        public lieuOuvrage?: string,
        public maisonEditionOuvrage?: string,
        public collectionOuvrage?: string,
        public langueOuvrage?: string,
        public lienOuvrage?: string,
        public doiOuvrage?: string,
        public halOuvrage?: string,
        public diversOuvrage?: string,
        public notations?: BaseEntity[],
    ) {
    }
}
