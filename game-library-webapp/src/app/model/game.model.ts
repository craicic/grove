import {ModelInterface} from './interface/model.interface';
import {Publisher} from './publisher.model';
import {Category} from './category.model';
import {Mechanism} from './mechansim.model';
import {Creator} from './creator.model';
import {GameNatureEnum} from './enum/game-nature.enum';
import {GameCopy} from './game-copy.model';

export class Game implements ModelInterface {
  id?: number;
  title: string;
  description?: string;
  playTime?: string;
  minNumberOfPlayer: number;
  maxNumberOfPlayer: number;
  minMonth?: number;
  minAge: number;
  maxAge: number;
  material?: string;
  preparation?: string;
  goal?: string;
  rules?: string;
  variant?: string;
  nature?: GameNatureEnum;
  size?: string;
  editionNumber?: string;
  publisher?: Publisher;
  categories?: Category[];
  mechanisms?: Mechanism[];
  creators?: Creator[];
  copies?: GameCopy[];
  imageIds?: number[];


}
