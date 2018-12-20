import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { SlackJhipSlackAppModule } from './slack-app/slack-app.module';
import { SlackJhipChannelModule } from './channel/channel.module';
import { SlackJhipWorkspaceModule } from './workspace/workspace.module';
import { SlackJhipAppUserModule } from './app-user/app-user.module';
import { SlackJhipMessageModule } from './message/message.module';
import { SlackJhipDirectMessageModule } from './direct-message/direct-message.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    // prettier-ignore
    imports: [
        SlackJhipSlackAppModule,
        SlackJhipChannelModule,
        SlackJhipWorkspaceModule,
        SlackJhipAppUserModule,
        SlackJhipMessageModule,
        SlackJhipDirectMessageModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SlackJhipEntityModule {}
