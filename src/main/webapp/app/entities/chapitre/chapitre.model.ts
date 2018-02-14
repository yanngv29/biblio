import { BaseEntity } from './../../shared';

export class Chapitre implements BaseEntity {
    constructor(
        public id?: number,
        public idChapitre?: string,
        public pageDebut?: string,
        public pageFin?: string,
        public hal?: string,
        public ouvrage?: BaseEntity,
    ) {
    }
}
