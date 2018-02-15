import { BaseEntity } from './../../shared';

export const enum TypeMemoire {
    'THESE',
    'HDR'
}

export class Memoire implements BaseEntity {
    constructor(
        public id?: number,
        public titreMemoire?: string,
        public typeMemoire?: TypeMemoire,
        public dateMemoire?: any,
        public lieuMemoire?: string,
        public langueMemoire?: string,
        public lienMemoire?: string,
        public doiMemoire?: string,
        public halMemoire?: string,
        public diversMemoire?: string,
        public notations?: BaseEntity[],
    ) {
    }
}
