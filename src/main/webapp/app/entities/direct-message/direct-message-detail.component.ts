import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDirectMessage } from 'app/shared/model/direct-message.model';

@Component({
    selector: 'jhi-direct-message-detail',
    templateUrl: './direct-message-detail.component.html'
})
export class DirectMessageDetailComponent implements OnInit {
    directMessage: IDirectMessage;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ directMessage }) => {
            this.directMessage = directMessage;
        });
    }

    previousState() {
        window.history.back();
    }
}
