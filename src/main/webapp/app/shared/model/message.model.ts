import { Moment } from 'moment';
import { IAppUser } from 'app/shared/model//app-user.model';
import { IChannel } from 'app/shared/model//channel.model';
import { IDirectMessage } from 'app/shared/model//direct-message.model';

export interface IMessage {
    id?: number;
    messageId?: number;
    message?: string;
    timeStamp?: Moment;
    dateStamp?: Moment;
    sender?: string;
    appUser?: IAppUser;
    channel?: IChannel;
    directMessage?: IDirectMessage;
}

export class Message implements IMessage {
    constructor(
        public id?: number,
        public messageId?: number,
        public message?: string,
        public timeStamp?: Moment,
        public dateStamp?: Moment,
        public sender?: string,
        public appUser?: IAppUser,
        public channel?: IChannel,
        public directMessage?: IDirectMessage
    ) {}
}
