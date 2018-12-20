import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IDirectMessage } from 'app/shared/model/direct-message.model';
import { Principal } from 'app/core';
import { DirectMessageService } from './direct-message.service';

@Component({
    selector: 'jhi-direct-message',
    templateUrl: './direct-message.component.html'
})
export class DirectMessageComponent implements OnInit, OnDestroy {
    directMessages: IDirectMessage[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private directMessageService: DirectMessageService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.directMessageService.query().subscribe(
            (res: HttpResponse<IDirectMessage[]>) => {
                this.directMessages = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInDirectMessages();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IDirectMessage) {
        return item.id;
    }

    registerChangeInDirectMessages() {
        this.eventSubscriber = this.eventManager.subscribe('directMessageListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
