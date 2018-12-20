import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IWorkspace } from 'app/shared/model/workspace.model';
import { Principal } from 'app/core';
import { WorkspaceService } from './workspace.service';

@Component({
    selector: 'jhi-workspace',
    templateUrl: './workspace.component.html'
})
export class WorkspaceComponent implements OnInit, OnDestroy {
    workspaces: IWorkspace[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private workspaceService: WorkspaceService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.workspaceService.query().subscribe(
            (res: HttpResponse<IWorkspace[]>) => {
                this.workspaces = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInWorkspaces();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IWorkspace) {
        return item.id;
    }

    registerChangeInWorkspaces() {
        this.eventSubscriber = this.eventManager.subscribe('workspaceListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
