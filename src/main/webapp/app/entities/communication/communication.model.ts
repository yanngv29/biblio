import { BaseEntity } from './../../shared';

export const enum TypeCommunication {
    'AFFICHE',
    'ATELIER',
    'TABLE_RONDE'
}

export class Communication implements BaseEntity {
    constructor(
        public id?: number,
        public titreCommunication?: string,
        public typeCommunication?: TypeCommunication,
        public langueCommunication?: string,
        public lienCommunication?: string,
        public doiCommunication?: string,
        public halCommunication?: string,
        public diversCommunication?: string,
        public conference?: BaseEntity,
    ) {
    }
}
