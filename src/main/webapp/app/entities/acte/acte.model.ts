import { BaseEntity } from './../../shared';

export class Acte implements BaseEntity {
    constructor(
        public id?: number,
        public idActe?: string,
        public titre?: string,
        public type?: string,
        public annee?: number,
        public numeroEdition?: number,
        public volume?: number,
        public traduction?: string,
        public lieu?: string,
        public maisonEdition?: string,
        public collection?: string,
        public hal?: string,
        public conference?: BaseEntity,
    ) {
    }
}
