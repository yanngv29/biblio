import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { Notation } from './notation.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<Notation>;

@Injectable()
export class NotationService {

    private resourceUrl =  SERVER_API_URL + 'api/notations';

    constructor(private http: HttpClient) { }

    create(notation: Notation): Observable<EntityResponseType> {
        const copy = this.convert(notation);
        return this.http.post<Notation>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(notation: Notation): Observable<EntityResponseType> {
        const copy = this.convert(notation);
        return this.http.put<Notation>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Notation>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Notation[]>> {
        const options = createRequestOption(req);
        return this.http.get<Notation[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Notation[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Notation = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Notation[]>): HttpResponse<Notation[]> {
        const jsonResponse: Notation[] = res.body;
        const body: Notation[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Notation.
     */
    private convertItemFromServer(notation: Notation): Notation {
        const copy: Notation = Object.assign({}, notation);
        return copy;
    }

    /**
     * Convert a Notation to a JSON which can be sent to the server.
     */
    private convert(notation: Notation): Notation {
        const copy: Notation = Object.assign({}, notation);
        return copy;
    }
}
