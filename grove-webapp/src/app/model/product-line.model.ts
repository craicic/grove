import {ModelInterface} from './interface/model.interface';
import {ImpersonalInterface} from './interface/impersonal.interface';

export class ProductLine implements ModelInterface, ImpersonalInterface {
  id?: number;
  name: string;

  constructor(name: string, id?: number) {
    this.name = name;
    this.id = id;
  }
}
