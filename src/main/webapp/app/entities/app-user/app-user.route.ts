import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { AppUser } from 'app/shared/model/app-user.model';
import { AppUserService } from './app-user.service';
import { AppUserComponent } from './app-user.component';
import { AppUserDetailComponent } from './app-user-detail.component';
import { AppUserUpdateComponent } from './app-user-update.component';
import { AppUserDeletePopupComponent } from './app-user-delete-dialog.component';
import { IAppUser } from 'app/shared/model/app-user.model';

@Injectable({ providedIn: 'root' })
export class AppUserResolve implements Resolve<IAppUser> {
    constructor(private service: AppUserService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<AppUser> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<AppUser>) => response.ok),
                map((appUser: HttpResponse<AppUser>) => appUser.body)
            );
        }
        return of(new AppUser());
    }
}

export const appUserRoute: Routes = [
    {
        path: 'app-user',
        component: AppUserComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'slackJhipApp.appUser.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'app-user/:id/view',
        component: AppUserDetailComponent,
        resolve: {
            appUser: AppUserResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'slackJhipApp.appUser.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'app-user/new',
        component: AppUserUpdateComponent,
        resolve: {
            appUser: AppUserResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'slackJhipApp.appUser.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'app-user/:id/edit',
        component: AppUserUpdateComponent,
        resolve: {
            appUser: AppUserResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'slackJhipApp.appUser.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const appUserPopupRoute: Routes = [
    {
        path: 'app-user/:id/delete',
        component: AppUserDeletePopupComponent,
        resolve: {
            appUser: AppUserResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'slackJhipApp.appUser.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
