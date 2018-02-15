import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { Conference } from './conference.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<Conference>;

@Injectable()
export class ConferenceService {

    private resourceUrl =  SERVER_API_URL + 'api/conferences';

    constructor(private http: HttpClient, private dateUtils: JhiDateUtils) { }

    create(conference: Conference): Observable<EntityResponseType> {
        const copy = this.convert(conference);
        return this.http.post<Conference>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(conference: Conference): Observable<EntityResponseType> {
        const copy = this.convert(conference);
        return this.http.put<Conference>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Conference>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Conference[]>> {
        const options = createRequestOption(req);
        return this.http.get<Conference[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Conference[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Conference = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Conference[]>): HttpResponse<Conference[]> {
        const jsonResponse: Conference[] = res.body;
        const body: Conference[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Conference.
     */
    private convertItemFromServer(conference: Conference): Conference {
        const copy: Conference = Object.assign({}, conference);
        copy.dateDebutConference = this.dateUtils
            .convertDateTimeFromServer(conference.dateDebutConference);
        copy.dateFinConference = this.dateUtils
            .convertDateTimeFromServer(conference.dateFinConference);
        return copy;
    }

    /**
     * Convert a Conference to a JSON which can be sent to the server.
     */
    private convert(conference: Conference): Conference {
        const copy: Conference = Object.assign({}, conference);

        copy.dateDebutConference = this.dateUtils.toDate(conference.dateDebutConference);

        copy.dateFinConference = this.dateUtils.toDate(conference.dateFinConference);
        return copy;
    }
}
