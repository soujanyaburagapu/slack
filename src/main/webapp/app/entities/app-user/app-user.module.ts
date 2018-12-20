import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SlackJhipSharedModule } from 'app/shared';
import {
    AppUserComponent,
    AppUserDetailComponent,
    AppUserUpdateComponent,
    AppUserDeletePopupComponent,
    AppUserDeleteDialogComponent,
    appUserRoute,
    appUserPopupRoute
} from './';

const ENTITY_STATES = [...appUserRoute, ...appUserPopupRoute];

@NgModule({
    imports: [SlackJhipSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        AppUserComponent,
        AppUserDetailComponent,
        AppUserUpdateComponent,
        AppUserDeleteDialogComponent,
        AppUserDeletePopupComponent
    ],
    entryComponents: [AppUserComponent, AppUserUpdateComponent, AppUserDeleteDialogComponent, AppUserDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SlackJhipAppUserModule {}
