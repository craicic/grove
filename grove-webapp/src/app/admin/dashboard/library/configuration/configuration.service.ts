import {Injectable} from '@angular/core';
import {configuration} from '../../../../../properties/configuration';


@Injectable({providedIn: 'root'})
export class ConfigurationService {
  numberOfElements: number;

  setNumberOfElements(numberToStore: number): void {
    this.storeNumberOfElements(numberToStore);
    this.numberOfElements = numberToStore;
  }

  getNumberOfElements(): number {
    this.numberOfElements = this.retrieveNumberOfElements();
    return this.numberOfElements;
  }

  private storeNumberOfElements(numberToStore: number): void {
    localStorage.setItem('numberOfElements', JSON.stringify(numberToStore));
  }

  private retrieveNumberOfElements(): number {
    if (!localStorage.getItem('numberOfElements')) {
      return configuration.pagination.numberOfElementsPerPage;
    } else {
      return +JSON.parse(localStorage.getItem('numberOfElements'));
    }
  }
}
