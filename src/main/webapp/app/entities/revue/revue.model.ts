import { BaseEntity } from './../../shared';

export const enum Audience {
    'NATIONALE',
    'INTERNATIONALE'
}

export class Revue implements BaseEntity {
    constructor(
        public id?: number,
        public nomRevue?: string,
        public audience?: Audience,
        public comiteSelection?: boolean,
        public langueRevue?: string,
        public lieuRevue?: string,
        public lienRevue?: string,
        public diversRevue?: string,
        public notations?: BaseEntity[],
    ) {
        this.comiteSelection = false;
    }
}
