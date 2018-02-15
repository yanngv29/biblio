import { BaseEntity } from './../../shared';

export class Actes implements BaseEntity {
    constructor(
        public id?: number,
        public titreActe?: string,
        public typeActe?: string,
        public anneeActe?: number,
        public numeroEditionActe?: number,
        public volumeActe?: number,
        public traductionActe?: string,
        public lieuActe?: string,
        public maisonEditionActe?: string,
        public collectionActe?: string,
        public langueActe?: string,
        public lienActe?: string,
        public halActe?: string,
        public diversActe?: string,
        public conference?: BaseEntity,
    ) {
    }
}
