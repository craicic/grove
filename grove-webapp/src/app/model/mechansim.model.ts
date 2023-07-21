import {ModelInterface} from './interface/model.interface';

export class Mechanism implements ModelInterface {
  title: string;
  id?: number;

  constructor(title: string, id?: number) {
    this.title = title;
    this.id = id;
  }
}
