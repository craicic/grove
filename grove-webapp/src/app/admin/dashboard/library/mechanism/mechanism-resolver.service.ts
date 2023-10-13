import { ActivatedRouteSnapshot, RouterStateSnapshot } from '@angular/router';
import {Observable} from 'rxjs';
import {MechanismDataService} from './mechanism-data.service';
import {MechanismService} from './mechanism.service';
import {Injectable} from '@angular/core';
import {Mechanism} from '../../../../model/mechansim.model';
import {Page} from '../../../../model/page.model';

@Injectable({providedIn: 'root'})
export class MechanismResolver  {

  constructor(private mechanismsDataService: MechanismDataService,
              private mechanismsService: MechanismService) {
  }

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot):
    Observable<Page<Mechanism>> | Promise<Page<Mechanism>> | Page<Mechanism> {
    const mechanisms: Page<Mechanism> = this.mechanismsService.pagedMechanisms;

    if (!mechanisms) {
      return this.mechanismsDataService.fetchMechanisms();
    } else {
      return mechanisms;
    }
  }
}
