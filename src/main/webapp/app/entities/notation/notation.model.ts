import { BaseEntity } from './../../shared';

export const enum Rang {
    'A',
    'B',
    'C'
}

export class Notation implements BaseEntity {
    constructor(
        public id?: number,
        public idNotation?: string,
        public cnu?: string,
        public rang?: Rang,
        public debut?: string,
        public fin?: string,
        public conference?: BaseEntity,
        public memoire?: BaseEntity,
        public ouvrage?: BaseEntity,
        public publicationGouvernementale?: BaseEntity,
        public rapport?: BaseEntity,
        public revue?: BaseEntity,
    ) {
    }
}
