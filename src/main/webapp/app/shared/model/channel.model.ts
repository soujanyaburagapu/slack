import { IMessage } from 'app/shared/model//message.model';
import { IAppUser } from 'app/shared/model//app-user.model';
import { IWorkspace } from 'app/shared/model//workspace.model';

export interface IChannel {
    id?: number;
    channelName?: string;
    channelID?: number;
    channelDescription?: string;
    isPrivate?: boolean;
    messages?: IMessage[];
    appUsers?: IAppUser[];
    workspace?: IWorkspace;
}

export class Channel implements IChannel {
    constructor(
        public id?: number,
        public channelName?: string,
        public channelID?: number,
        public channelDescription?: string,
        public isPrivate?: boolean,
        public messages?: IMessage[],
        public appUsers?: IAppUser[],
        public workspace?: IWorkspace
    ) {
        this.isPrivate = this.isPrivate || false;
    }
}
