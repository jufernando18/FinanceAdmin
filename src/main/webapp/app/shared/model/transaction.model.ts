import { Moment } from 'moment';
import { TransactionType } from 'app/shared/model/enumerations/transaction-type.model';

export interface ITransaction {
  id?: number;
  concept?: string;
  accountT?: number;
  type?: TransactionType;
  value?: number;
  date?: Moment;
  balance?: number;
  auditId?: number;
  accountTId?: number;
}

export class Transaction implements ITransaction {
  constructor(
    public id?: number,
    public concept?: string,
    public accountT?: number,
    public type?: TransactionType,
    public value?: number,
    public date?: Moment,
    public balance?: number,
    public auditId?: number,
    public accountTId?: number
  ) {}
}
