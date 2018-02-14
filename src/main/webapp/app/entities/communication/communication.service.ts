import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { Communication } from './communication.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<Communication>;

@Injectable()
export class CommunicationService {

    private resourceUrl =  SERVER_API_URL + 'api/communications';

    constructor(private http: HttpClient) { }

    create(communication: Communication): Observable<EntityResponseType> {
        const copy = this.convert(communication);
        return this.http.post<Communication>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(communication: Communication): Observable<EntityResponseType> {
        const copy = this.convert(communication);
        return this.http.put<Communication>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Communication>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Communication[]>> {
        const options = createRequestOption(req);
        return this.http.get<Communication[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Communication[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Communication = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Communication[]>): HttpResponse<Communication[]> {
        const jsonResponse: Communication[] = res.body;
        const body: Communication[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Communication.
     */
    private convertItemFromServer(communication: Communication): Communication {
        const copy: Communication = Object.assign({}, communication);
        return copy;
    }

    /**
     * Convert a Communication to a JSON which can be sent to the server.
     */
    private convert(communication: Communication): Communication {
        const copy: Communication = Object.assign({}, communication);
        return copy;
    }
}
