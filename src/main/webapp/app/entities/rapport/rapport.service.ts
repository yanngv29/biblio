import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { Rapport } from './rapport.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<Rapport>;

@Injectable()
export class RapportService {

    private resourceUrl =  SERVER_API_URL + 'api/rapports';

    constructor(private http: HttpClient, private dateUtils: JhiDateUtils) { }

    create(rapport: Rapport): Observable<EntityResponseType> {
        const copy = this.convert(rapport);
        return this.http.post<Rapport>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(rapport: Rapport): Observable<EntityResponseType> {
        const copy = this.convert(rapport);
        return this.http.put<Rapport>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Rapport>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Rapport[]>> {
        const options = createRequestOption(req);
        return this.http.get<Rapport[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Rapport[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Rapport = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Rapport[]>): HttpResponse<Rapport[]> {
        const jsonResponse: Rapport[] = res.body;
        const body: Rapport[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Rapport.
     */
    private convertItemFromServer(rapport: Rapport): Rapport {
        const copy: Rapport = Object.assign({}, rapport);
        copy.date = this.dateUtils
            .convertDateTimeFromServer(rapport.date);
        return copy;
    }

    /**
     * Convert a Rapport to a JSON which can be sent to the server.
     */
    private convert(rapport: Rapport): Rapport {
        const copy: Rapport = Object.assign({}, rapport);

        copy.date = this.dateUtils.toDate(rapport.date);
        return copy;
    }
}
