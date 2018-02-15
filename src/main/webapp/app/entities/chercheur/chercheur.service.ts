import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { Chercheur } from './chercheur.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<Chercheur>;

@Injectable()
export class ChercheurService {

    private resourceUrl =  SERVER_API_URL + 'api/chercheurs';

    constructor(private http: HttpClient) { }

    create(chercheur: Chercheur): Observable<EntityResponseType> {
        const copy = this.convert(chercheur);
        return this.http.post<Chercheur>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(chercheur: Chercheur): Observable<EntityResponseType> {
        const copy = this.convert(chercheur);
        return this.http.put<Chercheur>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Chercheur>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Chercheur[]>> {
        const options = createRequestOption(req);
        return this.http.get<Chercheur[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Chercheur[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Chercheur = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Chercheur[]>): HttpResponse<Chercheur[]> {
        const jsonResponse: Chercheur[] = res.body;
        const body: Chercheur[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Chercheur.
     */
    private convertItemFromServer(chercheur: Chercheur): Chercheur {
        const copy: Chercheur = Object.assign({}, chercheur);
        return copy;
    }

    /**
     * Convert a Chercheur to a JSON which can be sent to the server.
     */
    private convert(chercheur: Chercheur): Chercheur {
        const copy: Chercheur = Object.assign({}, chercheur);
        return copy;
    }
}
