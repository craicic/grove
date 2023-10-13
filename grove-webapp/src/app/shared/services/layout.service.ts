import {Injectable} from '@angular/core';
import {BehaviorSubject} from 'rxjs';
import {NavModeEnum} from '../../model/enum/nav-mode.enum';

export const CREATION = 'creation';
export const EDITION = 'edition';
export const NAV = 'navigation';


@Injectable({providedIn: 'root'})
export class LayoutService {

  public MODE_NAMES = {};
  mode: string;
  entity: string;

  navStatus$: BehaviorSubject<NavModeEnum> = new BehaviorSubject<NavModeEnum>(NavModeEnum.NAVIGATION);

  constructor() {
    console.log('LAYOUT SERVICE INIT !!');
    this.MODE_NAMES[CREATION] = 'Création';
    this.MODE_NAMES[EDITION] = 'Édition';
    this.MODE_NAMES[NAV] = 'Navigation';
  }

  getModeName(): string {
    return this.MODE_NAMES[this.mode];
  }

  switchMode(navMode: NavModeEnum): void {
    console.log(this.navStatus$.value);
    this.navStatus$.next(navMode);
  }

  getMode(): BehaviorSubject<NavModeEnum> {
    return this.navStatus$;
  }
}
