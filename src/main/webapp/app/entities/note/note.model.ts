import { BaseEntity } from './../../shared';

export const enum RangNote {
    'A',
    'B',
    'C'
}

export class Note implements BaseEntity {
    constructor(
        public id?: number,
        public cnuNote?: string,
        public rangNote?: RangNote,
        public debutNote?: string,
        public finNote?: string,
        public conference?: BaseEntity,
        public memoire?: BaseEntity,
        public ouvrage?: BaseEntity,
        public rapport?: BaseEntity,
        public revue?: BaseEntity,
    ) {
    }
}
