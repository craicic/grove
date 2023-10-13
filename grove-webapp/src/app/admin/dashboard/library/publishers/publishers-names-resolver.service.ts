import {Injectable} from '@angular/core';
import { ActivatedRouteSnapshot, RouterStateSnapshot } from '@angular/router';
import {ImpersonalInterface} from '../../../../model/interface/impersonal.interface';
import {Observable} from 'rxjs';
import {PublisherDataService} from './publisher-data.service';

@Injectable({providedIn: 'root'})
export class PublishersNamesResolver  {

  constructor(private dataService: PublisherDataService) {
  }

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot):
    Observable<ImpersonalInterface[]> | Promise<ImpersonalInterface[]> | ImpersonalInterface[] {
    return this.dataService.fetchNames();
  }
}
