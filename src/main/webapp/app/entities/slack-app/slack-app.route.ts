import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { SlackApp } from 'app/shared/model/slack-app.model';
import { SlackAppService } from './slack-app.service';
import { SlackAppComponent } from './slack-app.component';
import { SlackAppDetailComponent } from './slack-app-detail.component';
import { SlackAppUpdateComponent } from './slack-app-update.component';
import { SlackAppDeletePopupComponent } from './slack-app-delete-dialog.component';
import { ISlackApp } from 'app/shared/model/slack-app.model';

@Injectable({ providedIn: 'root' })
export class SlackAppResolve implements Resolve<ISlackApp> {
    constructor(private service: SlackAppService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<SlackApp> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<SlackApp>) => response.ok),
                map((slackApp: HttpResponse<SlackApp>) => slackApp.body)
            );
        }
        return of(new SlackApp());
    }
}

export const slackAppRoute: Routes = [
    {
        path: 'slack-app',
        component: SlackAppComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'slackJhipApp.slackApp.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'slack-app/:id/view',
        component: SlackAppDetailComponent,
        resolve: {
            slackApp: SlackAppResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'slackJhipApp.slackApp.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'slack-app/new',
        component: SlackAppUpdateComponent,
        resolve: {
            slackApp: SlackAppResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'slackJhipApp.slackApp.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'slack-app/:id/edit',
        component: SlackAppUpdateComponent,
        resolve: {
            slackApp: SlackAppResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'slackJhipApp.slackApp.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const slackAppPopupRoute: Routes = [
    {
        path: 'slack-app/:id/delete',
        component: SlackAppDeletePopupComponent,
        resolve: {
            slackApp: SlackAppResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'slackJhipApp.slackApp.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
