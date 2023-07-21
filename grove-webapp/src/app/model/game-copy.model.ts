import {GeneralStateEnum} from './enum/general-state.enum';
import {Seller} from './seller.model';

export class GameCopy {

  id: number;
  objectCode: string;
  price?: number;
  location?: string;
  dateOfPurchase?: Date;
  registerDate?: Date;
  wearCondition?: string;
  generalState?: GeneralStateEnum;
  isLoanable?: boolean;
  seller?: Seller;
  gameName?: string;
  gameId?: number;
}
