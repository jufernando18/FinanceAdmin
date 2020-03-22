export interface IUserRegistry {
  id?: number;
  name?: string;
  username?: string;
  password?: string;
  title?: string;
  token?: string;
  session?: boolean;
}

export class UserRegistry implements IUserRegistry {
  constructor(
    public id?: number,
    public name?: string,
    public username?: string,
    public password?: string,
    public title?: string,
    public token?: string,
    public session?: boolean
  ) {
    this.session = this.session || false;
  }
}
