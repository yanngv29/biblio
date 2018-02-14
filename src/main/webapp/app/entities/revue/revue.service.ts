import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { Revue } from './revue.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<Revue>;

@Injectable()
export class RevueService {

    private resourceUrl =  SERVER_API_URL + 'api/revues';

    constructor(private http: HttpClient) { }

    create(revue: Revue): Observable<EntityResponseType> {
        const copy = this.convert(revue);
        return this.http.post<Revue>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(revue: Revue): Observable<EntityResponseType> {
        const copy = this.convert(revue);
        return this.http.put<Revue>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Revue>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Revue[]>> {
        const options = createRequestOption(req);
        return this.http.get<Revue[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Revue[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Revue = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Revue[]>): HttpResponse<Revue[]> {
        const jsonResponse: Revue[] = res.body;
        const body: Revue[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Revue.
     */
    private convertItemFromServer(revue: Revue): Revue {
        const copy: Revue = Object.assign({}, revue);
        return copy;
    }

    /**
     * Convert a Revue to a JSON which can be sent to the server.
     */
    private convert(revue: Revue): Revue {
        const copy: Revue = Object.assign({}, revue);
        return copy;
    }
}
