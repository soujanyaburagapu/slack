import { IWorkspace } from 'app/shared/model//workspace.model';
import { IAppUser } from 'app/shared/model//app-user.model';

export interface ISlackApp {
    id?: number;
    workspaces?: IWorkspace[];
    appUsers?: IAppUser[];
}

export class SlackApp implements ISlackApp {
    constructor(public id?: number, public workspaces?: IWorkspace[], public appUsers?: IAppUser[]) {}
}
