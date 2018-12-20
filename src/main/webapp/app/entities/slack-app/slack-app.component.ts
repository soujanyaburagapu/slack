import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ISlackApp } from 'app/shared/model/slack-app.model';
import { Principal } from 'app/core';
import { SlackAppService } from './slack-app.service';

@Component({
    selector: 'jhi-slack-app',
    templateUrl: './slack-app.component.html'
})
export class SlackAppComponent implements OnInit, OnDestroy {
    slackApps: ISlackApp[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private slackAppService: SlackAppService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.slackAppService.query().subscribe(
            (res: HttpResponse<ISlackApp[]>) => {
                this.slackApps = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInSlackApps();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ISlackApp) {
        return item.id;
    }

    registerChangeInSlackApps() {
        this.eventSubscriber = this.eventManager.subscribe('slackAppListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
