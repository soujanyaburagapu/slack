import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IDirectMessage } from 'app/shared/model/direct-message.model';

type EntityResponseType = HttpResponse<IDirectMessage>;
type EntityArrayResponseType = HttpResponse<IDirectMessage[]>;

@Injectable({ providedIn: 'root' })
export class DirectMessageService {
    public resourceUrl = SERVER_API_URL + 'api/direct-messages';

    constructor(private http: HttpClient) {}

    create(directMessage: IDirectMessage): Observable<EntityResponseType> {
        return this.http.post<IDirectMessage>(this.resourceUrl, directMessage, { observe: 'response' });
    }

    update(directMessage: IDirectMessage): Observable<EntityResponseType> {
        return this.http.put<IDirectMessage>(this.resourceUrl, directMessage, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IDirectMessage>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IDirectMessage[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
