import { BaseEntity } from './../../shared';

export const enum Audience {
    'NATIONALE',
    'INTERNATIONALE'
}

export class Revue implements BaseEntity {
    constructor(
        public id?: number,
        public idRevue?: string,
        public nom?: string,
        public audience?: Audience,
        public comiteSelection?: boolean,
        public mois?: string,
        public annee?: string,
        public volume?: string,
        public numeroVolume?: string,
        public lieu?: string,
        public lien?: string,
        public divers?: string,
        public notations?: BaseEntity[],
    ) {
        this.comiteSelection = false;
    }
}
