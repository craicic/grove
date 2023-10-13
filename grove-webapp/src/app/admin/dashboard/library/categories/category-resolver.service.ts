import { ActivatedRouteSnapshot, RouterStateSnapshot } from '@angular/router';
import {Observable} from 'rxjs';
import {CategoryService} from './category.service';
import {Injectable} from '@angular/core';
import {Category} from '../../../../model/category.model';

@Injectable({providedIn: 'root'})
export class CategoryResolver  {
  constructor(private service: CategoryService) {
  }

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot):
    Observable<Category[]> | Promise<Category[]> | Category[] {

    return this.service.fetchAllAndStore();
  }
}
