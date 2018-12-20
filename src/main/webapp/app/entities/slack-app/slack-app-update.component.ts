import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { ISlackApp } from 'app/shared/model/slack-app.model';
import { SlackAppService } from './slack-app.service';

@Component({
    selector: 'jhi-slack-app-update',
    templateUrl: './slack-app-update.component.html'
})
export class SlackAppUpdateComponent implements OnInit {
    slackApp: ISlackApp;
    isSaving: boolean;

    constructor(private slackAppService: SlackAppService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ slackApp }) => {
            this.slackApp = slackApp;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.slackApp.id !== undefined) {
            this.subscribeToSaveResponse(this.slackAppService.update(this.slackApp));
        } else {
            this.subscribeToSaveResponse(this.slackAppService.create(this.slackApp));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ISlackApp>>) {
        result.subscribe((res: HttpResponse<ISlackApp>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
}
