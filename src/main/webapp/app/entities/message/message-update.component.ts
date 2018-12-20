import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';

import { IMessage } from 'app/shared/model/message.model';
import { MessageService } from './message.service';
import { IAppUser } from 'app/shared/model/app-user.model';
import { AppUserService } from 'app/entities/app-user';
import { IChannel } from 'app/shared/model/channel.model';
import { ChannelService } from 'app/entities/channel';
import { IDirectMessage } from 'app/shared/model/direct-message.model';
import { DirectMessageService } from 'app/entities/direct-message';

@Component({
    selector: 'jhi-message-update',
    templateUrl: './message-update.component.html'
})
export class MessageUpdateComponent implements OnInit {
    message: IMessage;
    isSaving: boolean;

    appusers: IAppUser[];

    channels: IChannel[];

    directmessages: IDirectMessage[];
    timeStamp: string;
    dateStampDp: any;

    constructor(
        private jhiAlertService: JhiAlertService,
        private messageService: MessageService,
        private appUserService: AppUserService,
        private channelService: ChannelService,
        private directMessageService: DirectMessageService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ message }) => {
            this.message = message;
            this.timeStamp = this.message.timeStamp != null ? this.message.timeStamp.format(DATE_TIME_FORMAT) : null;
        });
        this.appUserService.query().subscribe(
            (res: HttpResponse<IAppUser[]>) => {
                this.appusers = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.channelService.query().subscribe(
            (res: HttpResponse<IChannel[]>) => {
                this.channels = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.directMessageService.query().subscribe(
            (res: HttpResponse<IDirectMessage[]>) => {
                this.directmessages = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.message.timeStamp = this.timeStamp != null ? moment(this.timeStamp, DATE_TIME_FORMAT) : null;
        if (this.message.id !== undefined) {
            this.subscribeToSaveResponse(this.messageService.update(this.message));
        } else {
            this.subscribeToSaveResponse(this.messageService.create(this.message));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IMessage>>) {
        result.subscribe((res: HttpResponse<IMessage>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackAppUserById(index: number, item: IAppUser) {
        return item.id;
    }

    trackChannelById(index: number, item: IChannel) {
        return item.id;
    }

    trackDirectMessageById(index: number, item: IDirectMessage) {
        return item.id;
    }
}
