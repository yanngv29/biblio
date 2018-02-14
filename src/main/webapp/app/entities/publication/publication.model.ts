import { BaseEntity } from './../../shared';

export class Publication implements BaseEntity {
    constructor(
        public id?: number,
        public idPublication?: string,
        public titre?: string,
        public divers?: string,
        public lien?: string,
        public doi?: string,
        public article?: BaseEntity,
        public communication?: BaseEntity,
        public memoire?: BaseEntity,
        public ouvrage?: BaseEntity,
        public publicationGouvernementale?: BaseEntity,
        public rapport?: BaseEntity,
    ) {
    }
}
