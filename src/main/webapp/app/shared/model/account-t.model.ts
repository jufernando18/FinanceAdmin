import { TransactionType } from 'app/shared/model/enumerations/transaction-type.model';

export interface IAccountT {
  id?: number;
  name?: string;
  description?: string;
  code?: number;
  increaseWhen?: TransactionType;
  decreaseWhen?: TransactionType;
  balance?: number;
  auditId?: number;
}

export class AccountT implements IAccountT {
  constructor(
    public id?: number,
    public name?: string,
    public description?: string,
    public code?: number,
    public increaseWhen?: TransactionType,
    public decreaseWhen?: TransactionType,
    public balance?: number,
    public auditId?: number
  ) {}
}
