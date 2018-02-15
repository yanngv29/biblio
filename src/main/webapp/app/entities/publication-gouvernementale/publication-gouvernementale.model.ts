import { BaseEntity } from './../../shared';

export class PublicationGouvernementale implements BaseEntity {
    constructor(
        public id?: number,
        public titrePG?: string,
        public datePG?: any,
        public numeroEditionPG?: string,
        public lieuPG?: string,
        public maisonEditionPG?: string,
        public languePG?: string,
        public lienPG?: string,
        public doiPG?: string,
        public halPG?: string,
        public diversOuvragePG?: string,
        public notations?: BaseEntity[],
    ) {
    }
}
