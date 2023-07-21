import {ImpersonalInterface} from './interface/impersonal.interface';
import {Contact} from './contact.model';

export class Seller implements ImpersonalInterface {

  id?: number;
  name: string;
  contact?: Contact;
}
