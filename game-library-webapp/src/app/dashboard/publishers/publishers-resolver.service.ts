import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, Resolve, RouterStateSnapshot} from '@angular/router';
import {Observable} from 'rxjs';
import {PublisherDataService} from './publisher-data.service';
import {PublisherService} from './publisher.service';
import {Page} from '../../model/page.model';
import {Publisher} from '../../model/publisher.model';

@Injectable({providedIn: 'root'})
export class PublishersResolver implements Resolve<Page<Publisher>> {

  constructor(private dataService: PublisherDataService,
              private service: PublisherService) {
  }

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot):
    Observable<Page<Publisher>> | Promise<Page<Publisher>> | Page<Publisher> {
    const pagedPublisher: Page<Publisher> = this.service.pagedPublishers;

    if (!pagedPublisher) {
      return this.dataService.fetchPublishers();
    }
    return pagedPublisher;
  }


}
