import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, Resolve, RouterStateSnapshot} from '@angular/router';
import {Observable} from 'rxjs';
import {EDITION, WrapperService} from '../services/wrapper.service';

@Injectable({providedIn: 'root'})
export class WrapperEditResolver implements Resolve<void> {

  constructor(private wrapperService: WrapperService) {
  }

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<void> | Promise<void> | void {
    this.wrapperService.entity = 'Jeux';
    this.wrapperService.mode = EDITION;
  }


}
