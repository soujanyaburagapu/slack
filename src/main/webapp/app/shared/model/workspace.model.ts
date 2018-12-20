import { IChannel } from 'app/shared/model//channel.model';
import { ISlackApp } from 'app/shared/model//slack-app.model';
import { IAppUser } from 'app/shared/model//app-user.model';

export interface IWorkspace {
    id?: number;
    workspaceName?: string;
    admin?: string;
    workspaceID?: number;
    description?: string;
    channels?: IChannel[];
    slackApp?: ISlackApp;
    appUsers?: IAppUser[];
}

export class Workspace implements IWorkspace {
    constructor(
        public id?: number,
        public workspaceName?: string,
        public admin?: string,
        public workspaceID?: number,
        public description?: string,
        public channels?: IChannel[],
        public slackApp?: ISlackApp,
        public appUsers?: IAppUser[]
    ) {}
}
