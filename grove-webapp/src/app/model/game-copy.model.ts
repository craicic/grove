import {GeneralStateEnum} from './enum/general-state.enum';
import {Publisher} from './publisher.model';

export class GameCopy {

  id: number;
  objectCode: string;
  price?: number;
  location?: string;
  dateOfPurchase?: Date;
  dateOfRegistration?: Date;
  wearCondition?: string;
  generalState?: GeneralStateEnum;
  availableForLoan?: boolean;
  gameName?: string;
  editionNumber?: string;
  publisher?: Publisher;
  gameId?: number;
}
