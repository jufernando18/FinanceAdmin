import { Moment } from 'moment';
import { State } from 'app/shared/model/enumerations/state.model';

export interface IAuditRegistry {
  id?: number;
  state?: State;
  createdBy?: number;
  createdDate?: Moment;
  lastModifiedBy?: number;
  lastModifiedDate?: Moment;
  createdById?: number;
  lastModifiedById?: number;
}

export class AuditRegistry implements IAuditRegistry {
  constructor(
    public id?: number,
    public state?: State,
    public createdBy?: number,
    public createdDate?: Moment,
    public lastModifiedBy?: number,
    public lastModifiedDate?: Moment,
    public createdById?: number,
    public lastModifiedById?: number
  ) {}
}
