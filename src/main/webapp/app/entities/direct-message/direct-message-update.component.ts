import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IDirectMessage } from 'app/shared/model/direct-message.model';
import { DirectMessageService } from './direct-message.service';
import { IAppUser } from 'app/shared/model/app-user.model';
import { AppUserService } from 'app/entities/app-user';

@Component({
    selector: 'jhi-direct-message-update',
    templateUrl: './direct-message-update.component.html'
})
export class DirectMessageUpdateComponent implements OnInit {
    directMessage: IDirectMessage;
    isSaving: boolean;

    appusers: IAppUser[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private directMessageService: DirectMessageService,
        private appUserService: AppUserService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ directMessage }) => {
            this.directMessage = directMessage;
        });
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
        if (this.directMessage.id !== undefined) {
            this.subscribeToSaveResponse(this.directMessageService.update(this.directMessage));
        } else {
            this.subscribeToSaveResponse(this.directMessageService.create(this.directMessage));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IDirectMessage>>) {
        result.subscribe((res: HttpResponse<IDirectMessage>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
