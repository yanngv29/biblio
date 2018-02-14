import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { Contribution } from './contribution.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<Contribution>;

@Injectable()
export class ContributionService {

    private resourceUrl =  SERVER_API_URL + 'api/contributions';

    constructor(private http: HttpClient) { }

    create(contribution: Contribution): Observable<EntityResponseType> {
        const copy = this.convert(contribution);
        return this.http.post<Contribution>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(contribution: Contribution): Observable<EntityResponseType> {
        const copy = this.convert(contribution);
        return this.http.put<Contribution>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Contribution>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Contribution[]>> {
        const options = createRequestOption(req);
        return this.http.get<Contribution[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Contribution[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Contribution = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Contribution[]>): HttpResponse<Contribution[]> {
        const jsonResponse: Contribution[] = res.body;
        const body: Contribution[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Contribution.
     */
    private convertItemFromServer(contribution: Contribution): Contribution {
        const copy: Contribution = Object.assign({}, contribution);
        return copy;
    }

    /**
     * Convert a Contribution to a JSON which can be sent to the server.
     */
    private convert(contribution: Contribution): Contribution {
        const copy: Contribution = Object.assign({}, contribution);
        return copy;
    }
}
