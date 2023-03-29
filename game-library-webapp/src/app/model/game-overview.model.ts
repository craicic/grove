import {ModelInterface} from './interface/model.interface';
import {Category} from './category.model';
import {Creator} from './creator.model';

export class GameOverview implements ModelInterface {
  id?: number;
  gameCopyCount: number;
  title: string;
  description?: string;
  playTime?: string;
  minNumberOfPlayer?: number;
  maxNumberOfPlayer?: number;
  minMonth?: number;
  minAge?: number;
  maxAge?: number;
  categories?: Category[];
  creators?: Creator[];
  imageIds?: number[];

}
