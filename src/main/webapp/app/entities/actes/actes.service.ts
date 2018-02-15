import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { Actes } from './actes.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<Actes>;

@Injectable()
export class ActesService {

    private resourceUrl =  SERVER_API_URL + 'api/actes';

    constructor(private http: HttpClient) { }

    create(actes: Actes): Observable<EntityResponseType> {
        const copy = this.convert(actes);
        return this.http.post<Actes>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(actes: Actes): Observable<EntityResponseType> {
        const copy = this.convert(actes);
        return this.http.put<Actes>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Actes>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Actes[]>> {
        const options = createRequestOption(req);
        return this.http.get<Actes[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Actes[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Actes = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Actes[]>): HttpResponse<Actes[]> {
        const jsonResponse: Actes[] = res.body;
        const body: Actes[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Actes.
     */
    private convertItemFromServer(actes: Actes): Actes {
        const copy: Actes = Object.assign({}, actes);
        return copy;
    }

    /**
     * Convert a Actes to a JSON which can be sent to the server.
     */
    private convert(actes: Actes): Actes {
        const copy: Actes = Object.assign({}, actes);
        return copy;
    }
}
