import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDirectMessage } from 'app/shared/model/direct-message.model';
import { DirectMessageService } from './direct-message.service';

@Component({
    selector: 'jhi-direct-message-delete-dialog',
    templateUrl: './direct-message-delete-dialog.component.html'
})
export class DirectMessageDeleteDialogComponent {
    directMessage: IDirectMessage;

    constructor(
        private directMessageService: DirectMessageService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.directMessageService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'directMessageListModification',
                content: 'Deleted an directMessage'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-direct-message-delete-popup',
    template: ''
})
export class DirectMessageDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ directMessage }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(DirectMessageDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.directMessage = directMessage;
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
