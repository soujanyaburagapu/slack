import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IAppUser } from 'app/shared/model/app-user.model';
import { Principal } from 'app/core';
import { AppUserService } from './app-user.service';

@Component({
    selector: 'jhi-app-user',
    templateUrl: './app-user.component.html'
})
export class AppUserComponent implements OnInit, OnDestroy {
    appUsers: IAppUser[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private appUserService: AppUserService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.appUserService.query().subscribe(
            (res: HttpResponse<IAppUser[]>) => {
                this.appUsers = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInAppUsers();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IAppUser) {
        return item.id;
    }

    registerChangeInAppUsers() {
        this.eventSubscriber = this.eventManager.subscribe('appUserListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
