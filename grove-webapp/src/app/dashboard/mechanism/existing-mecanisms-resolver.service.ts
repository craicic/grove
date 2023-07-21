import {Injectable} from '@angular/core';
import {MechanismDataService} from './mechanism-data.service';
import {ActivatedRouteSnapshot, Resolve, RouterStateSnapshot} from '@angular/router';
import {Observable} from 'rxjs';
import {Mechanism} from '../../model/mechansim.model';

@Injectable({providedIn: 'root'})
export class ExistingMechanismsResolver implements Resolve<Mechanism[]> {

  constructor(private mechanismsDataService: MechanismDataService) {
  }

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot):
    Observable<Mechanism[]> | Promise<Mechanism[]> | Mechanism[] {
    return this.mechanismsDataService.fetchNames();
  }
}
