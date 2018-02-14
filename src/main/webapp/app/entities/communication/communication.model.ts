import { BaseEntity } from './../../shared';

export const enum TypeCommunication {
    'AFFICHE',
    'ATELIER',
    'TABLE_RONDE'
}

export class Communication implements BaseEntity {
    constructor(
        public id?: number,
        public idCommunication?: string,
        public type?: TypeCommunication,
        public hal?: string,
        public conference?: BaseEntity,
    ) {
    }
}
