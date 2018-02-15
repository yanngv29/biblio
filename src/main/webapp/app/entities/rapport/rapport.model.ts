import { BaseEntity } from './../../shared';

export const enum TypeRapport {
    'RECHERCHE',
    'PROJET'
}

export class Rapport implements BaseEntity {
    constructor(
        public id?: number,
        public titreRapport?: string,
        public sousTitreRapport?: string,
        public typeRapport?: TypeRapport,
        public dateRapport?: any,
        public lieuRapport?: string,
        public maisonEditionRapport?: string,
        public langueRapport?: string,
        public lienRapport?: string,
        public doiRapport?: string,
        public halRapport?: string,
        public diversOuvrageRapport?: string,
        public notations?: BaseEntity[],
    ) {
    }
}
