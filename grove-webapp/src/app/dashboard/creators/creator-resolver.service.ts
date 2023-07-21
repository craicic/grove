import {Injectable} from '@angular/core';
import {Creator} from '../../model/creator.model';
import {Page} from '../../model/page.model';
import {ActivatedRouteSnapshot, Resolve, RouterStateSnapshot} from '@angular/router';
import {Observable} from 'rxjs';
import {CreatorService} from './creator.service';
import {CreatorDataService} from './creator-data.service';

@Injectable({providedIn: 'root'})
export class CreatorResolver implements Resolve<Page<Creator>> {

  constructor(private creatorService: CreatorService,
              private creatorDataService: CreatorDataService) {
  }

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Page<Creator>> | Promise<Page<Creator>> | Page<Creator> {
    const pagedCreators: Page<Creator> = this.creatorService.pagedCreators;

    if (!pagedCreators) {
      return this.creatorDataService.fetchCreators();
    }
    return pagedCreators;
  }


}
