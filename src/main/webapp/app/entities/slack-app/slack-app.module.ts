import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SlackJhipSharedModule } from 'app/shared';
import {
    SlackAppComponent,
    SlackAppDetailComponent,
    SlackAppUpdateComponent,
    SlackAppDeletePopupComponent,
    SlackAppDeleteDialogComponent,
    slackAppRoute,
    slackAppPopupRoute
} from './';

const ENTITY_STATES = [...slackAppRoute, ...slackAppPopupRoute];

@NgModule({
    imports: [SlackJhipSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        SlackAppComponent,
        SlackAppDetailComponent,
        SlackAppUpdateComponent,
        SlackAppDeleteDialogComponent,
        SlackAppDeletePopupComponent
    ],
    entryComponents: [SlackAppComponent, SlackAppUpdateComponent, SlackAppDeleteDialogComponent, SlackAppDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SlackJhipSlackAppModule {}
