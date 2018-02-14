import { BaseEntity } from './../../shared';

export const enum TypeConference {
    'CONFERENCE',
    'COLLOQUE',
    'JOURNEE_ETUDE',
    'JOURNEE_ATELIERS',
    'SEMINAIRE',
    'SYMPOSIUM'
}

export const enum Audience {
    'NATIONALE',
    'INTERNATIONALE'
}

export class Conference implements BaseEntity {
    constructor(
        public id?: number,
        public idConference?: string,
        public type?: TypeConference,
        public nom?: string,
        public audience?: Audience,
        public comiteSelection?: boolean,
        public editeur?: string,
        public dateDebut?: any,
        public dateFin?: any,
        public ville?: string,
        public pays?: string,
        public lienSite?: string,
        public lienActes?: string,
        public divers?: string,
        public notations?: BaseEntity[],
    ) {
        this.comiteSelection = false;
    }
}
