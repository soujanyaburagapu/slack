import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ISlackApp } from 'app/shared/model/slack-app.model';

type EntityResponseType = HttpResponse<ISlackApp>;
type EntityArrayResponseType = HttpResponse<ISlackApp[]>;

@Injectable({ providedIn: 'root' })
export class SlackAppService {
    public resourceUrl = SERVER_API_URL + 'api/slack-apps';

    constructor(private http: HttpClient) {}

    create(slackApp: ISlackApp): Observable<EntityResponseType> {
        return this.http.post<ISlackApp>(this.resourceUrl, slackApp, { observe: 'response' });
    }

    update(slackApp: ISlackApp): Observable<EntityResponseType> {
        return this.http.put<ISlackApp>(this.resourceUrl, slackApp, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<ISlackApp>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ISlackApp[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
