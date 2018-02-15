import { BaseEntity } from './../../shared';

export class NumeroRevue implements BaseEntity {
    constructor(
        public id?: number,
        public volumeNumeroRevue?: string,
        public numeroVolumeNumeroRevue?: string,
        public moisNumeroRevue?: string,
        public anneeNumeroRevue?: string,
        public langueNumeroRevue?: string,
        public lienNumeroRevue?: string,
        public doiNumeroRevue?: string,
        public diversNumeroRevue?: string,
        public revue?: BaseEntity,
        public writers?: BaseEntity[],
    ) {
    }
}
