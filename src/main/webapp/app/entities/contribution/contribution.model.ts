import { BaseEntity } from './../../shared';

export const enum Participation {
    'PUBLICATION',
    'ENCADREMENT',
    'DIRECTION',
    'EDITION',
    'COORDINATION'
}

export class Contribution implements BaseEntity {
    constructor(
        public id?: number,
        public idChercheur?: string,
        public type?: Participation,
        public rang?: string,
        public publication?: BaseEntity,
        public acte?: BaseEntity,
        public ouvrage?: BaseEntity,
        public revue?: BaseEntity,
        public memoire?: BaseEntity,
    ) {
    }
}
