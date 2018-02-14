import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { Acte } from './acte.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<Acte>;

@Injectable()
export class ActeService {

    private resourceUrl =  SERVER_API_URL + 'api/actes';

    constructor(private http: HttpClient) { }

    create(acte: Acte): Observable<EntityResponseType> {
        const copy = this.convert(acte);
        return this.http.post<Acte>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(acte: Acte): Observable<EntityResponseType> {
        const copy = this.convert(acte);
        return this.http.put<Acte>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Acte>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Acte[]>> {
        const options = createRequestOption(req);
        return this.http.get<Acte[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Acte[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Acte = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Acte[]>): HttpResponse<Acte[]> {
        const jsonResponse: Acte[] = res.body;
        const body: Acte[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Acte.
     */
    private convertItemFromServer(acte: Acte): Acte {
        const copy: Acte = Object.assign({}, acte);
        return copy;
    }

    /**
     * Convert a Acte to a JSON which can be sent to the server.
     */
    private convert(acte: Acte): Acte {
        const copy: Acte = Object.assign({}, acte);
        return copy;
    }
}
