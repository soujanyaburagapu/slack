import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IWorkspace } from 'app/shared/model/workspace.model';
import { WorkspaceService } from './workspace.service';
import { ISlackApp } from 'app/shared/model/slack-app.model';
import { SlackAppService } from 'app/entities/slack-app';
import { IAppUser } from 'app/shared/model/app-user.model';
import { AppUserService } from 'app/entities/app-user';

@Component({
    selector: 'jhi-workspace-update',
    templateUrl: './workspace-update.component.html'
})
export class WorkspaceUpdateComponent implements OnInit {
    workspace: IWorkspace;
    isSaving: boolean;

    slackapps: ISlackApp[];

    appusers: IAppUser[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private workspaceService: WorkspaceService,
        private slackAppService: SlackAppService,
        private appUserService: AppUserService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ workspace }) => {
            this.workspace = workspace;
        });
        this.slackAppService.query().subscribe(
            (res: HttpResponse<ISlackApp[]>) => {
                this.slackapps = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.appUserService.query().subscribe(
            (res: HttpResponse<IAppUser[]>) => {
                this.appusers = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.workspace.id !== undefined) {
            this.subscribeToSaveResponse(this.workspaceService.update(this.workspace));
        } else {
            this.subscribeToSaveResponse(this.workspaceService.create(this.workspace));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IWorkspace>>) {
        result.subscribe((res: HttpResponse<IWorkspace>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackSlackAppById(index: number, item: ISlackApp) {
        return item.id;
    }

    trackAppUserById(index: number, item: IAppUser) {
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
