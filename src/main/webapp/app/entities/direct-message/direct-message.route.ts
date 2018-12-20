import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { DirectMessage } from 'app/shared/model/direct-message.model';
import { DirectMessageService } from './direct-message.service';
import { DirectMessageComponent } from './direct-message.component';
import { DirectMessageDetailComponent } from './direct-message-detail.component';
import { DirectMessageUpdateComponent } from './direct-message-update.component';
import { DirectMessageDeletePopupComponent } from './direct-message-delete-dialog.component';
import { IDirectMessage } from 'app/shared/model/direct-message.model';

@Injectable({ providedIn: 'root' })
export class DirectMessageResolve implements Resolve<IDirectMessage> {
    constructor(private service: DirectMessageService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<DirectMessage> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<DirectMessage>) => response.ok),
                map((directMessage: HttpResponse<DirectMessage>) => directMessage.body)
            );
        }
        return of(new DirectMessage());
    }
}

export const directMessageRoute: Routes = [
    {
        path: 'direct-message',
        component: DirectMessageComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'slackJhipApp.directMessage.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'direct-message/:id/view',
        component: DirectMessageDetailComponent,
        resolve: {
            directMessage: DirectMessageResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'slackJhipApp.directMessage.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'direct-message/new',
        component: DirectMessageUpdateComponent,
        resolve: {
            directMessage: DirectMessageResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'slackJhipApp.directMessage.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'direct-message/:id/edit',
        component: DirectMessageUpdateComponent,
        resolve: {
            directMessage: DirectMessageResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'slackJhipApp.directMessage.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const directMessagePopupRoute: Routes = [
    {
        path: 'direct-message/:id/delete',
        component: DirectMessageDeletePopupComponent,
        resolve: {
            directMessage: DirectMessageResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'slackJhipApp.directMessage.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
