import { IMessage } from 'app/shared/model//message.model';
import { IAppUser } from 'app/shared/model//app-user.model';

export interface IDirectMessage {
    id?: number;
    dmId?: number;
    message?: string;
    messages?: IMessage[];
    appUsers?: IAppUser[];
}

export class DirectMessage implements IDirectMessage {
    constructor(
        public id?: number,
        public dmId?: number,
        public message?: string,
        public messages?: IMessage[],
        public appUsers?: IAppUser[]
    ) {}
}
