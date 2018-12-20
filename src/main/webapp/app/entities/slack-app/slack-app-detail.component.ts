import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISlackApp } from 'app/shared/model/slack-app.model';

@Component({
    selector: 'jhi-slack-app-detail',
    templateUrl: './slack-app-detail.component.html'
})
export class SlackAppDetailComponent implements OnInit {
    slackApp: ISlackApp;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ slackApp }) => {
            this.slackApp = slackApp;
        });
    }

    previousState() {
        window.history.back();
    }
}
