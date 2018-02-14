import { BaseEntity } from './../../shared';

export class NumeroRevue implements BaseEntity {
    constructor(
        public id?: number,
        public idNumeroRevue?: string,
        public mois?: string,
        public annee?: string,
        public volume?: string,
        public numeroVolume?: string,
        public lien?: string,
        public doi?: string,
        public revue?: BaseEntity,
    ) {
    }
}
