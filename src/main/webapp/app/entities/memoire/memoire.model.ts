import { BaseEntity } from './../../shared';

export const enum TypeMemoire {
    'THESE',
    'HDR'
}

export class Memoire implements BaseEntity {
    constructor(
        public id?: number,
        public idMemoire?: string,
        public type?: TypeMemoire,
        public date?: any,
        public lieu?: string,
        public hal?: string,
        public notations?: BaseEntity[],
    ) {
    }
}
