import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IAppUser } from 'app/shared/model/app-user.model';
import { AppUserService } from './app-user.service';
import { IMessage } from 'app/shared/model/message.model';
import { MessageService } from 'app/entities/message';
import { IWorkspace } from 'app/shared/model/workspace.model';
import { WorkspaceService } from 'app/entities/workspace';
import { IDirectMessage } from 'app/shared/model/direct-message.model';
import { DirectMessageService } from 'app/entities/direct-message';
import { ISlackApp } from 'app/shared/model/slack-app.model';
import { SlackAppService } from 'app/entities/slack-app';
import { IChannel } from 'app/shared/model/channel.model';
import { ChannelService } from 'app/entities/channel';

@Component({
    selector: 'jhi-app-user-update',
    templateUrl: './app-user-update.component.html'
})
export class AppUserUpdateComponent implements OnInit {
    appUser: IAppUser;
    isSaving: boolean;

    messages: IMessage[];

    workspaces: IWorkspace[];

    directmessages: IDirectMessage[];

    slackapps: ISlackApp[];

    channels: IChannel[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private appUserService: AppUserService,
        private messageService: MessageService,
        private workspaceService: WorkspaceService,
        private directMessageService: DirectMessageService,
        private slackAppService: SlackAppService,
        private channelService: ChannelService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ appUser }) => {
            this.appUser = appUser;
        });
        this.messageService.query({ filter: 'appuser-is-null' }).subscribe(
            (res: HttpResponse<IMessage[]>) => {
                if (!this.appUser.message || !this.appUser.message.id) {
                    this.messages = res.body;
                } else {
                    this.messageService.find(this.appUser.message.id).subscribe(
                        (subRes: HttpResponse<IMessage>) => {
                            this.messages = [subRes.body].concat(res.body);
                        },
                        (subRes: HttpErrorResponse) => this.onError(subRes.message)
                    );
                }
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.workspaceService.query().subscribe(
            (res: HttpResponse<IWorkspace[]>) => {
                this.workspaces = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.directMessageService.query().subscribe(
            (res: HttpResponse<IDirectMessage[]>) => {
                this.directmessages = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.slackAppService.query().subscribe(
            (res: HttpResponse<ISlackApp[]>) => {
                this.slackapps = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.channelService.query().subscribe(
            (res: HttpResponse<IChannel[]>) => {
                this.channels = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.appUser.id !== undefined) {
            this.subscribeToSaveResponse(this.appUserService.update(this.appUser));
        } else {
            this.subscribeToSaveResponse(this.appUserService.create(this.appUser));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IAppUser>>) {
        result.subscribe((res: HttpResponse<IAppUser>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackMessageById(index: number, item: IMessage) {
        return item.id;
    }

    trackWorkspaceById(index: number, item: IWorkspace) {
        return item.id;
    }

    trackDirectMessageById(index: number, item: IDirectMessage) {
        return item.id;
    }

    trackSlackAppById(index: number, item: ISlackApp) {
        return item.id;
    }

    trackChannelById(index: number, item: IChannel) {
        return item.id;
    }

    getSelected(selectedVals: Array<any>, option: any) {
        if (selectedVals) {
            for (let i = 0; i < selectedVals.length; i++) {
                if (option.id === selectedVals[i].id) {
                    return selectedVals[i];
                }
            }
        }
        return option;
    }
}
