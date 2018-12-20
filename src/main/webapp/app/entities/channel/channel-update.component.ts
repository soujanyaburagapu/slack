import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IChannel } from 'app/shared/model/channel.model';
import { ChannelService } from './channel.service';
import { IAppUser } from 'app/shared/model/app-user.model';
import { AppUserService } from 'app/entities/app-user';
import { IWorkspace } from 'app/shared/model/workspace.model';
import { WorkspaceService } from 'app/entities/workspace';

@Component({
    selector: 'jhi-channel-update',
    templateUrl: './channel-update.component.html'
})
export class ChannelUpdateComponent implements OnInit {
    channel: IChannel;
    isSaving: boolean;

    appusers: IAppUser[];

    workspaces: IWorkspace[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private channelService: ChannelService,
        private appUserService: AppUserService,
        private workspaceService: WorkspaceService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ channel }) => {
            this.channel = channel;
        });
        this.appUserService.query().subscribe(
            (res: HttpResponse<IAppUser[]>) => {
                this.appusers = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.workspaceService.query().subscribe(
            (res: HttpResponse<IWorkspace[]>) => {
                this.workspaces = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.channel.id !== undefined) {
            this.subscribeToSaveResponse(this.channelService.update(this.channel));
        } else {
            this.subscribeToSaveResponse(this.channelService.create(this.channel));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IChannel>>) {
        result.subscribe((res: HttpResponse<IChannel>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackWorkspaceById(index: number, item: IWorkspace) {
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
