import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { Publication } from './publication.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<Publication>;

@Injectable()
export class PublicationService {

    private resourceUrl =  SERVER_API_URL + 'api/publications';

    constructor(private http: HttpClient) { }

    create(publication: Publication): Observable<EntityResponseType> {
        const copy = this.convert(publication);
        return this.http.post<Publication>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(publication: Publication): Observable<EntityResponseType> {
        const copy = this.convert(publication);
        return this.http.put<Publication>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Publication>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Publication[]>> {
        const options = createRequestOption(req);
        return this.http.get<Publication[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Publication[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Publication = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Publication[]>): HttpResponse<Publication[]> {
        const jsonResponse: Publication[] = res.body;
        const body: Publication[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Publication.
     */
    private convertItemFromServer(publication: Publication): Publication {
        const copy: Publication = Object.assign({}, publication);
        return copy;
    }

    /**
     * Convert a Publication to a JSON which can be sent to the server.
     */
    private convert(publication: Publication): Publication {
        const copy: Publication = Object.assign({}, publication);
        return copy;
    }
}
