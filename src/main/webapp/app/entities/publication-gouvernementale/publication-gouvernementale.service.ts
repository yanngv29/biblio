import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { PublicationGouvernementale } from './publication-gouvernementale.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<PublicationGouvernementale>;

@Injectable()
export class PublicationGouvernementaleService {

    private resourceUrl =  SERVER_API_URL + 'api/publication-gouvernementales';

    constructor(private http: HttpClient, private dateUtils: JhiDateUtils) { }

    create(publicationGouvernementale: PublicationGouvernementale): Observable<EntityResponseType> {
        const copy = this.convert(publicationGouvernementale);
        return this.http.post<PublicationGouvernementale>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(publicationGouvernementale: PublicationGouvernementale): Observable<EntityResponseType> {
        const copy = this.convert(publicationGouvernementale);
        return this.http.put<PublicationGouvernementale>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<PublicationGouvernementale>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<PublicationGouvernementale[]>> {
        const options = createRequestOption(req);
        return this.http.get<PublicationGouvernementale[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<PublicationGouvernementale[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: PublicationGouvernementale = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<PublicationGouvernementale[]>): HttpResponse<PublicationGouvernementale[]> {
        const jsonResponse: PublicationGouvernementale[] = res.body;
        const body: PublicationGouvernementale[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to PublicationGouvernementale.
     */
    private convertItemFromServer(publicationGouvernementale: PublicationGouvernementale): PublicationGouvernementale {
        const copy: PublicationGouvernementale = Object.assign({}, publicationGouvernementale);
        copy.datePG = this.dateUtils
            .convertDateTimeFromServer(publicationGouvernementale.datePG);
        return copy;
    }

    /**
     * Convert a PublicationGouvernementale to a JSON which can be sent to the server.
     */
    private convert(publicationGouvernementale: PublicationGouvernementale): PublicationGouvernementale {
        const copy: PublicationGouvernementale = Object.assign({}, publicationGouvernementale);

        copy.datePG = this.dateUtils.toDate(publicationGouvernementale.datePG);
        return copy;
    }
}
