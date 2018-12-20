import { IMessage } from 'app/shared/model//message.model';
import { IWorkspace } from 'app/shared/model//workspace.model';
import { IDirectMessage } from 'app/shared/model//direct-message.model';
import { ISlackApp } from 'app/shared/model//slack-app.model';
import { IChannel } from 'app/shared/model//channel.model';

export interface IAppUser {
    id?: number;
    userId?: number;
    displayName?: string;
    email?: string;
    userName?: string;
    password?: string;
    isActive?: boolean;
    message?: IMessage;
    workspaces?: IWorkspace[];
    directMessages?: IDirectMessage[];
    slackApp?: ISlackApp;
    channels?: IChannel[];
}

export class AppUser implements IAppUser {
    constructor(
        public id?: number,
        public userId?: number,
        public displayName?: string,
        public email?: string,
        public userName?: string,
        public password?: string,
        public isActive?: boolean,
        public message?: IMessage,
        public workspaces?: IWorkspace[],
        public directMessages?: IDirectMessage[],
        public slackApp?: ISlackApp,
        public channels?: IChannel[]
    ) {
        this.isActive = this.isActive || false;
    }
}
