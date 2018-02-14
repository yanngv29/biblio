import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { Ouvrage } from './ouvrage.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<Ouvrage>;

@Injectable()
export class OuvrageService {

    private resourceUrl =  SERVER_API_URL + 'api/ouvrages';

    constructor(private http: HttpClient) { }

    create(ouvrage: Ouvrage): Observable<EntityResponseType> {
        const copy = this.convert(ouvrage);
        return this.http.post<Ouvrage>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(ouvrage: Ouvrage): Observable<EntityResponseType> {
        const copy = this.convert(ouvrage);
        return this.http.put<Ouvrage>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Ouvrage>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Ouvrage[]>> {
        const options = createRequestOption(req);
        return this.http.get<Ouvrage[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Ouvrage[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Ouvrage = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Ouvrage[]>): HttpResponse<Ouvrage[]> {
        const jsonResponse: Ouvrage[] = res.body;
        const body: Ouvrage[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Ouvrage.
     */
    private convertItemFromServer(ouvrage: Ouvrage): Ouvrage {
        const copy: Ouvrage = Object.assign({}, ouvrage);
        return copy;
    }

    /**
     * Convert a Ouvrage to a JSON which can be sent to the server.
     */
    private convert(ouvrage: Ouvrage): Ouvrage {
        const copy: Ouvrage = Object.assign({}, ouvrage);
        return copy;
    }
}
