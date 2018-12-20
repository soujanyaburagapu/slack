import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IChannel } from 'app/shared/model/channel.model';
import { Principal } from 'app/core';
import { ChannelService } from './channel.service';

@Component({
    selector: 'jhi-channel',
    templateUrl: './channel.component.html'
})
export class ChannelComponent implements OnInit, OnDestroy {
    channels: IChannel[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private channelService: ChannelService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.channelService.query().subscribe(
            (res: HttpResponse<IChannel[]>) => {
                this.channels = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInChannels();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IChannel) {
        return item.id;
    }

    registerChangeInChannels() {
        this.eventSubscriber = this.eventManager.subscribe('channelListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
