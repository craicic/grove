import {CreatorRoleEnum} from './enum/creator-role.enum';
import {Contact} from './contact.model';
import {ModelInterface} from './interface/model.interface';
import {Person} from './interface/person.interface';

export class Creator implements ModelInterface, Person {
  id?: number;
  firstName: string;
  lastName: string;
  role?: CreatorRoleEnum;
  contact?: Contact;
}
