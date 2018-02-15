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
        public nomConference?: string,
        public typeConference?: TypeConference,
        public audienceConference?: Audience,
        public comiteSelectionConference?: boolean,
        public dateDebutConference?: any,
        public dateFinConference?: any,
        public villeConference?: string,
        public paysConference?: string,
        public langueConference?: string,
        public lienSiteConference?: string,
        public diversConference?: string,
        public notations?: BaseEntity[],
    ) {
        this.comiteSelectionConference = false;
    }
}
