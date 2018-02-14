import { BaseEntity } from './../../shared';

export class PublicationGouvernementale implements BaseEntity {
    constructor(
        public id?: number,
        public idPG?: string,
        public date?: any,
        public numeroEdition?: string,
        public lieu?: string,
        public maisonEdition?: string,
        public notations?: BaseEntity[],
    ) {
    }
}
