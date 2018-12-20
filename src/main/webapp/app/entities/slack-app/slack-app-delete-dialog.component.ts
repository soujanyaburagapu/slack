import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISlackApp } from 'app/shared/model/slack-app.model';
import { SlackAppService } from './slack-app.service';

@Component({
    selector: 'jhi-slack-app-delete-dialog',
    templateUrl: './slack-app-delete-dialog.component.html'
})
export class SlackAppDeleteDialogComponent {
    slackApp: ISlackApp;

    constructor(private slackAppService: SlackAppService, public activeModal: NgbActiveModal, private eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.slackAppService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'slackAppListModification',
                content: 'Deleted an slackApp'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-slack-app-delete-popup',
    template: ''
})
export class SlackAppDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ slackApp }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(SlackAppDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.slackApp = slackApp;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}
