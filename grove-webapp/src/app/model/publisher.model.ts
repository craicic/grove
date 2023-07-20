import {ModelInterface} from './interface/model.interface';
import {Contact} from './contact.model';
import {ImpersonalInterface} from './interface/impersonal.interface';

export class Publisher implements ModelInterface, ImpersonalInterface {
  id?: number;
  name: string;
  contact?: Contact;

  constructor(name?: string, id?: number) {
    this.name = name;
    this.id = id;
  }
}
