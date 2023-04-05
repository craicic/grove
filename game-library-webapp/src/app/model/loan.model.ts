import {GameCopy} from './game-copy.model';
import {Account} from './account.model';

export class Loan {
  id: number;
  loanStartTime?: Date;
  loanEndTime?: Date;
  gameCopy?: GameCopy;
  account?: Account;
  closed?: boolean;
}
