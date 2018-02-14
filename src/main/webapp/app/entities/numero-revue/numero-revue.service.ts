import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { NumeroRevue } from './numero-revue.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<NumeroRevue>;

@Injectable()
export class NumeroRevueService {

    private resourceUrl =  SERVER_API_URL + 'api/numero-revues';

    constructor(private http: HttpClient) { }

    create(numeroRevue: NumeroRevue): Observable<EntityResponseType> {
        const copy = this.convert(numeroRevue);
        return this.http.post<NumeroRevue>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(numeroRevue: NumeroRevue): Observable<EntityResponseType> {
        const copy = this.convert(numeroRevue);
        return this.http.put<NumeroRevue>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<NumeroRevue>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<NumeroRevue[]>> {
        const options = createRequestOption(req);
        return this.http.get<NumeroRevue[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<NumeroRevue[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: NumeroRevue = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<NumeroRevue[]>): HttpResponse<NumeroRevue[]> {
        const jsonResponse: NumeroRevue[] = res.body;
        const body: NumeroRevue[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to NumeroRevue.
     */
    private convertItemFromServer(numeroRevue: NumeroRevue): NumeroRevue {
        const copy: NumeroRevue = Object.assign({}, numeroRevue);
        return copy;
    }

    /**
     * Convert a NumeroRevue to a JSON which can be sent to the server.
     */
    private convert(numeroRevue: NumeroRevue): NumeroRevue {
        const copy: NumeroRevue = Object.assign({}, numeroRevue);
        return copy;
    }
}
